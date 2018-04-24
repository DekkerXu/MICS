package org.hyperledger.fabric.sdkintegration;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;

public class Query {
	String Qpart;
	String testTxID;
	Collection<Peer> channelPeers;
	Collection<Orderer> orderers;
	TransactionEvent transactionEvent;
	Channel channel;
	HFClient client;
	SampleOrg sampleOrg;
	int delta;
	Collection<ProposalResponse> successful;
	Collection<ProposalResponse> failed;
	String TESTUSER_1_NAME;
	ChaincodeID chaincodeID;

	public Query(String qpart, String testTxID, Collection<Peer> channelPeers, Collection<Orderer> orderers,
			TransactionEvent transactionEvent, Channel channel, HFClient client, SampleOrg sampleOrg, int delta,
			Collection<ProposalResponse> successful, Collection<ProposalResponse> failed, String tESTUSER_1_NAME,
			ChaincodeID chaincodeID) {
		super();
		Qpart = qpart;
		this.testTxID = testTxID;
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

	public String getTestTxID() {
		return testTxID;
	}

	public void query() {
		try {
			System.out.println("---------" + transactionEvent);

			waitOnFabric(0);
			assertTrue(transactionEvent.isValid()); // must be valid to be here.
			Out.out("Finished transaction with transaction id %s", transactionEvent.getTransactionID());
			testTxID = transactionEvent.getTransactionID(); // used in the channel queries later

			BlockchainInfo channelInfo1 = channel.queryBlockchainInfo();
			BlockInfo returnedBlock1 = channel.queryBlockByNumber(channelInfo1.getHeight() - 1);
			returnedBlock1 = channel.queryBlockByTransactionID(testTxID);
			System.out.println("-------------" + returnedBlock1);
			////////////////////////////
			// Send Query Proposal to all peers
			//
			// String expect = "" + (400 + delta);
			Out.out("Now query chaincode for the value of " + Qpart + ".");
			QueryByChaincodeRequest queryByChaincodeRequest = client.newQueryProposalRequest();
		//	System.out.println("========" + Qpart);
			queryByChaincodeRequest.setArgs(new String[] { "query", Qpart });
			// queryByChaincodeRequest1.setArgs(new String[] { "query", Qpart },new String[]
			// { "query", Qpart });
			queryByChaincodeRequest.setFcn("invoke");
			queryByChaincodeRequest.setChaincodeID(chaincodeID);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "QueryByChaincodeRequest:JavaSDK".getBytes(UTF_8));
			tm2.put("method", "QueryByChaincodeRequest".getBytes(UTF_8));
			queryByChaincodeRequest.setTransientMap(tm2);

			Collection<ProposalResponse> queryProposals = channel.queryByChaincode(queryByChaincodeRequest,
					channel.getPeers());
			for (ProposalResponse proposalResponse : queryProposals) {
				if (!proposalResponse.isVerified() || proposalResponse.getStatus() != ProposalResponse.Status.SUCCESS) {
					fail("Failed query proposal from peer " + proposalResponse.getPeer().getName() + " status: "
							+ proposalResponse.getStatus() + ". Messages: " + proposalResponse.getMessage()
							+ ". Was verified : " + proposalResponse.isVerified());
				} else {
					String payload = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
					Out.out("Query payload of" + Qpart + " from peer %s returned %s",
							proposalResponse.getPeer().getName(), payload);
					// assertEquals(payload, expect);
				}
			}

		} catch (Exception e) {
			Out.out("Caught exception while running query");
			e.printStackTrace();
			fail("Failed during chaincode query with error : " + e.getMessage());
		}

	}
}
