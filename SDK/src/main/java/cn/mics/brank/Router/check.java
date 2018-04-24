package cn.mics.brank.Router;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.SDKUtils;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.TxReadWriteSetInfo;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;

import cn.mics.brank.fabric.entity.Contract;
import cn.mics.brank.fabric.entity.MedicalCare;
import cn.mics.brank.fabric.entity.SampleOrg;
import cn.mics.brank.fabric.util.JacksonUtil;
import cn.mics.brank.sdk.Out;
import cn.mics.brank.testutils.TestConfig;

public class check {
	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String CHAIN_CODE_NAME = "example_cc_go";
	private static final String CHAIN_CODE_PATH = "github.com/example_cc";
	private static final String CHAIN_CODE_VERSION = "1";
	Collection<Peer> channelPeers;
	Collection<Orderer> orderers;
	TransactionEvent transactionEvent;
	Channel channel;
	HFClient client;
	SampleOrg sampleOrg ;
	Collection<ProposalResponse> successful;
	public Collection<ProposalResponse> getSuccessful() {
		return successful;
	}

	public void setSuccessful(Collection<ProposalResponse> successful) {
		this.successful = successful;
	}
	Collection<ProposalResponse> failed;
	String TESTUSER_1_NAME;
	String invokeTxID = "";  // save the CC invoke TxID
	ChaincodeID chaincodeID;
	String contractID;
	public check(Collection<Peer> channelPeers, Collection<Orderer> orderers, TransactionEvent transactionEvent,
			Channel channel, HFClient client, SampleOrg sampleOrg,  Collection<ProposalResponse> successful,
			Collection<ProposalResponse> failed, String tESTUSER_1_NAME, ChaincodeID chaincodeID,String contractID) {
		super();
		this.channelPeers = channelPeers;
		this.orderers = orderers;
		this.transactionEvent = transactionEvent;
		this.channel = channel;
		this.client = client;
		this.sampleOrg = sampleOrg;
		this.successful = successful;
		this.failed = failed;
		this.TESTUSER_1_NAME = tESTUSER_1_NAME;
		this.chaincodeID = chaincodeID;
		this.contractID=contractID;
	}
	
	public TransactionEvent getTransactionEvent() {
		return transactionEvent;
	}

	private static void waitOnFabric(int additional) {
		// wait a few seconds for the peers to catch up with each other via the gossip
		// network.
		// Another way would be to wait on all the peers event hubs for the event
		// containing the transaction TxID
		// try {
		// out("Wait %d milliseconds for peers to sync with each other", gossipWaitTime
		// + additional);
		// TimeUnit.MILLISECONDS.sleep(gossipWaitTime + additional);
		// } catch (InterruptedException e) {
		// fail("should not have jumped out of sleep mode. No other threads should be
		// running");
		// }
	}
	 public void invoke() {
			   Out.out("Sending instantiateTransaction to orderer ","" );
				waitOnFabric(0);
				assertTrue(transactionEvent.isValid()); // must be valid to be here.
				Out.out("Finished instantiate transaction with transaction id %s", transactionEvent.getTransactionID());

				try {
					successful.clear();
					failed.clear();
					client.setUserContext(sampleOrg.getUser(TESTUSER_1_NAME));
					
					///////////////
					/// Send transaction proposal to all peers
                    TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
                    transactionProposalRequest.setChaincodeID(chaincodeID);
                    transactionProposalRequest.setFcn("invoke");
                    transactionProposalRequest.setProposalWaitTime(testConfig.getProposalWaitTime());
                    MedicalCare medicare=new MedicalCare("一般意外","意外身故","23","hll","a","1","34","man","dfs","sf","20150305","awer","serg");
                    Contract contract=new Contract("一般意外","意外身故","123","一般意外","意外身故","123","一般意外","意外身故","123","一般意外","意外身故","123","一般意外","意外身故","123",
                    		"一般意外","意外身故","123","一般意外","意外身故","123","一般意外","意外身故","123","20140102","100","20150102","20160102","xian","hui","gf","fd","man","19920203",
                    		"1234567","234567","hll","543","a","1","re","man","234");
                    String medicareValue= JacksonUtil.toJSon(medicare);
                    String contractValue= JacksonUtil.toJSon(contract);
                  
            	
                    transactionProposalRequest.setArgs(new String[] {"check", "medicare", medicareValue,contractID,contractValue});

                    Map<String, byte[]> tm2 = new HashMap<>();
                    tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
                    tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
                    tm2.put("result", ":)".getBytes(UTF_8));  /// This should be returned see chaincode.
                    transactionProposalRequest.setTransientMap(tm2);

                    Out.out("sending transactionProposal to all peers with arguments: save medicare,contract");

                    Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
                    for (ProposalResponse response : transactionPropResp) {
                        if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                        	Out.out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
                        	invokeTxID = response.getTransactionID();
                        	successful.add(response);
                        } else {
                            failed.add(response);
                        }
                    }

                    // Check that all the proposals are consistent with each other. We should have only one set
                    // where all the proposals above are consistent.
                    Collection<Set<ProposalResponse>> proposalConsistencySets = SDKUtils.getProposalConsistencySets(transactionPropResp);
                    if (proposalConsistencySets.size() != 1) {
                        fail(format("Expected only one set of consistent proposal responses but got %d", proposalConsistencySets.size()));
                    }

                    Out.out("Received %d transaction proposal responses. Successful+verified: %d . Failed: %d",
                            transactionPropResp.size(), successful.size(), failed.size());
                    if (failed.size() > 0) {
                        ProposalResponse firstTransactionProposalResponse = failed.iterator().next();
                        fail("Not enough endorsers for invoke(move a,b,100):" + failed.size() + " endorser error: " +
                                firstTransactionProposalResponse.getMessage() +
                                ". Was verified: " + firstTransactionProposalResponse.isVerified());
                    }
                    Out.out("Successfully received transaction proposal responses.");

                    ProposalResponse resp = transactionPropResp.iterator().next();
                    byte[] x = resp.getChaincodeActionResponsePayload(); // This is the data returned by the chaincode.
                    String resultAsString = null;
                    if (x != null) {
                        resultAsString = new String(x, "UTF-8");
                    }
                    assertEquals(":)", resultAsString);

                    assertEquals(200, resp.getChaincodeActionResponseStatus()); //Chaincode's status.

                    TxReadWriteSetInfo readWriteSetInfo = resp.getChaincodeActionResponseReadWriteSetInfo();
                    //See blockwalker below how to transverse this
                    assertNotNull(readWriteSetInfo);
                    assertTrue(readWriteSetInfo.getNsRwsetCount() > 0);

                    ChaincodeID cid = resp.getChaincodeID();
                    assertNotNull(cid);
                    assertEquals(CHAIN_CODE_PATH, cid.getPath());
                    assertEquals(CHAIN_CODE_NAME, cid.getName());
                    assertEquals(CHAIN_CODE_VERSION, cid.getVersion());

                    ////////////////////////////
                    // Send Transaction Transaction to orderer
                    Out.out("Sending chaincode transaction(save medicare,contract) to orderer.");
                } catch (Exception e) {
                	Out.out("Caught an exception while invoking chaincode");
                    e.printStackTrace();
                    fail("Failed invoking chaincode with error : " + e.getMessage());
                }
				
				
				

	    }
	 
}
