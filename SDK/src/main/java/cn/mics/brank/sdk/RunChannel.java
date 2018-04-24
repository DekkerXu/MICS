package cn.mics.brank.sdk;

import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.LinkedList;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstallProposalRequest;
import org.hyperledger.fabric.sdk.ProposalResponse;

import cn.mics.brank.fabric.entity.SampleOrg;
import cn.mics.brank.testutils.TestConfig;

public class RunChannel {
	private static final String FOO_CHANNEL_NAME = "foo";
	private static final String BAR_CHANNEL_NAME = "bar";
	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String CHAIN_CODE_NAME = "example_cc_go";
	private static final String CHAIN_CODE_PATH = "github.com/example_cc";
	private static final String CHAIN_CODE_VERSION = "1";
	static String testTxID = null; // save the CC invoke TxID and use in queries
	static String testTxID1 = null;
	HFClient client;
	Channel channel;
	boolean installChaincode;
	SampleOrg sampleOrg;
	int delta;
	ChaincodeID chaincodeID = null;
	boolean isFooChain;
	Collection<ProposalResponse> successful = new LinkedList<>();
	Collection<ProposalResponse> failed = new LinkedList<>();

	public RunChannel(HFClient client, Channel channel,
			boolean installChaincode, SampleOrg sampleOrg) {
		super();
		this.client = client;
		this.channel = channel;
		this.installChaincode = installChaincode;
		this.sampleOrg = sampleOrg;
	}

	public ChaincodeID getChaincodeID() {
		return chaincodeID;
	}

	public Collection<ProposalResponse> getSuccessful() {
		return successful;
	}

	public Collection<ProposalResponse> getFailed() {
		return failed;
	}

	// CHECKSTYLE.OFF: Method length is 320 lines (max allowed is 150).
	void runChannel() {
		try {
			final String channelName = channel.getName();
			if (FOO_CHANNEL_NAME.equals(channelName)) {
				isFooChain = true;
			} else if (BAR_CHANNEL_NAME.equals(channelName)) {
				isFooChain = true;
			} else {
				isFooChain = false;
			}

			Out.out("Running channel %s", channelName);
			channel.setTransactionWaitTime(testConfig.getTransactionWaitTime());
			channel.setDeployWaitTime(testConfig.getDeployWaitTime());

			chaincodeID = ChaincodeID.newBuilder().setName(CHAIN_CODE_NAME).setVersion(CHAIN_CODE_VERSION)
					.setPath(CHAIN_CODE_PATH).build();

			if (installChaincode) {
				////////////////////////////
				// Install Proposal Request
				//

				client.setUserContext(sampleOrg.getPeerAdmin());

				Out.out("Creating install proposal");

				InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
				installProposalRequest.setChaincodeID(chaincodeID);
				// installChaincode
				if (isFooChain) {
					InstallChaincode installChaincode=new InstallChaincode(channel, client, sampleOrg,successful,failed);
					installChaincode.installChaincode();
					successful=installChaincode.getSuccessful();
					failed=installChaincode.getFailed();
					
				}
			}

			// client.setUserContext(sampleOrg.getUser(TEST_ADMIN_NAME));
			// final ChaincodeID chaincodeID =
			// firstInstallProposalResponse.getChaincodeID();
			// Note installing chaincode does not require transaction no need to
			// send to Orderers

			/////////////// ;
			//// Instantiate chaincode.
			
			InstantiateChaincode instantiateChaincode=new InstantiateChaincode(client, channel, installChaincode, sampleOrg,isFooChain,successful, failed);
			instantiateChaincode.instantiateChaincode();
			successful = instantiateChaincode.getSuccessful();
			failed=instantiateChaincode.getFailed();
			/// Send instantiate transaction to orderer(invoke)
			// Channel queries

			// We can only send channel queries to peers that are in the same org as the SDK
			// user context
			// Get the peers from the current org being used and pick one randomly to send
			// the queries to.

		} catch (Exception e) {
			Out.out("Caught an exception running channel %s", channel.getName());
			e.printStackTrace();
			fail("Test failed with error : " + e.getMessage());
		}

	}
	// CHECKSTYLE.ON: Method length is 320 lines (max allowed is 150).

}