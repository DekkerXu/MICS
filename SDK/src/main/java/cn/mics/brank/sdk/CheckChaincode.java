package cn.mics.brank.sdk;


import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;

import cn.mics.brank.fabric.entity.SampleOrg;

import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;

public class CheckChaincode {
	static TransactionEvent transactionEvent;
	static String payload;
	public static String getPayload() {
		return payload;
	}




	public static void setPayload(String payload) {
		CheckChaincode.payload = payload;
	}




	public static  void  CheckChaincode(String contractID,String medicareID,String testTxID, Collection<Peer> channelPeers, Collection<Orderer> orderers,
			 Channel channel, HFClient client, SampleOrg sampleOrg,
			Collection<ProposalResponse> successful, Collection<ProposalResponse> failed, String tESTUSER_1_NAME,
			ChaincodeID chaincodeID) {
		try {
			System.out.println("---------");

			waitOnFabric(0);
			client.setUserContext(sampleOrg.getUser(tESTUSER_1_NAME));
		
			////////////////////////////
			// Send Query Proposal to all peers
			QueryByChaincodeRequest queryByChaincodeRequest = client.newQueryProposalRequest();
			queryByChaincodeRequest.setArgs(new String[] {"check",contractID,medicareID });
			queryByChaincodeRequest.setFcn("invoke");
			queryByChaincodeRequest.setChaincodeID(chaincodeID);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "QueryByChaincodeRequest:JavaSDK".getBytes(UTF_8));
			tm2.put("method", "QueryByChaincodeRequest".getBytes(UTF_8));
			queryByChaincodeRequest.setTransientMap(tm2); 

			Collection<ProposalResponse> checkProposals = channel.queryByChaincode(queryByChaincodeRequest,channel.getPeers());
			for (ProposalResponse proposalResponse : checkProposals) {
				if (!proposalResponse.isVerified() || proposalResponse.getStatus() != ProposalResponse.Status.SUCCESS) {
					fail("Failed query proposal from peer " + proposalResponse.getPeer().getName() + " status: "
							+ proposalResponse.getStatus() + ". Messages: " + proposalResponse.getMessage()
							+ ". Was verified : " + proposalResponse.isVerified());
				} else {
					payload = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
				
				}
			}

		} catch (Exception e) {
			Out.out("Caught exception while running query");
			e.printStackTrace();
			fail("Failed during chaincode query with error : " + e.getMessage());
		}

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
}