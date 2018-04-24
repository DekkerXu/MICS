package org.hyperledger.fabric.sdkintegration;

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
import java.util.concurrent.TimeUnit;
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
import org.hyperledger.fabric.sdk.testutils.TestConfig;

public class Invoke {
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
	int delta;
	Collection<ProposalResponse> successful;
	Collection<ProposalResponse> failed;
	String TESTUSER_1_NAME;
	ChaincodeID chaincodeID;
	public Invoke(Collection<Peer> channelPeers, Collection<Orderer> orderers, TransactionEvent transactionEvent,
			Channel channel, HFClient client, SampleOrg sampleOrg, int delta, Collection<ProposalResponse> successful,
			Collection<ProposalResponse> failed, String tESTUSER_1_NAME, ChaincodeID chaincodeID) {
		super();
		this.channelPeers = channelPeers;
		this.orderers = orderers;
		this.transactionEvent = transactionEvent;
		this.channel = channel;
		this.client = client;
		this.sampleOrg = sampleOrg;
		this.delta = delta;
		this.successful = successful;
		this.failed = failed;
		TESTUSER_1_NAME = tESTUSER_1_NAME;
		this.chaincodeID = chaincodeID;
	}
	
	public TransactionEvent getTransactionEvent() {
		return transactionEvent;
	}

	private void setTransactionEvent(TransactionEvent transactionEvent) {
		this.transactionEvent = transactionEvent;
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
			Out.out("Sending instantiateTransaction to orderer with a and b set to 100 and %s respectively","" + (200 + delta));
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
                    transactionProposalRequest.setArgs(new String[] {"move", "a", "b", "100"});

                    Map<String, byte[]> tm2 = new HashMap<>();
                    tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
                    tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
                    tm2.put("result", ":)".getBytes(UTF_8));  /// This should be returned see chaincode.
                    transactionProposalRequest.setTransientMap(tm2);

                    Out.out("sending transactionProposal to all peers with arguments: move(a,b,100)");

                    Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
                    for (ProposalResponse response : transactionPropResp) {
                        if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                        	Out.out("Successful transaction proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
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
                    Out.out("Sending chaincode transaction(move a,b,100) to orderer.");
                    transactionEvent=channel.sendTransaction(successful).get(testConfig.getTransactionWaitTime(), TimeUnit.SECONDS);

                } catch (Exception e) {
                	Out.out("Caught an exception while invoking chaincode");
                    e.printStackTrace();
                    fail("Failed invoking chaincode with error : " + e.getMessage());
                }
				
				

	    }
	 
}
