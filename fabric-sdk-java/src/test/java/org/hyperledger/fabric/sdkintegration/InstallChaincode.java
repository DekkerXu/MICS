package org.hyperledger.fabric.sdkintegration;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstallProposalRequest;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.SDKUtils;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

public class InstallChaincode {
    private static final String TEST_FIXTURES_PATH = "src/test/fixture";
    private static final String CHAIN_CODE_PATH = "github.com/example_cc";
    private static final String CHAIN_CODE_VERSION = "1";
    private static final String FOO_CHANNEL_NAME = "foo";
    private static final String BAR_CHANNEL_NAME = "bar";
    private static final String CHAIN_CODE_NAME = "example_cc_go";
    Channel channel;
    HFClient client;
    SampleOrg sampleOrg;
	 Collection<ProposalResponse> responses= new LinkedList<>();
	 Collection<ProposalResponse> successful;
    Collection<ProposalResponse> failed ;
	

	public InstallChaincode(Channel channel, HFClient client, SampleOrg sampleOrg,
		   Collection<ProposalResponse> successful,
			Collection<ProposalResponse> failed) {
		super();
		this.channel = channel;
		this.client = client;
		this.sampleOrg = sampleOrg;
		this.successful = successful;
		this.failed = failed;
	}


	public Collection<ProposalResponse> getSuccessful() {
		return successful;
	}


	private void setSuccessful(Collection<ProposalResponse> successful) {
		this.successful = successful;
	}


	public Collection<ProposalResponse> getFailed() {
		return failed;
	}


	private void setFailed(Collection<ProposalResponse> failed) {
		this.failed = failed;
	}


	public void  installChaincode() throws InvalidArgumentException, IOException, ProposalException {

        ////////////////////////////
        // Install Proposal Request
        //
		  final String channelName = channel.getName();
		  final ChaincodeID chaincodeID;
		  chaincodeID = ChaincodeID.newBuilder().setName(CHAIN_CODE_NAME)
                  .setVersion(CHAIN_CODE_VERSION)
                  .setPath(CHAIN_CODE_PATH).build();
		  
		  boolean isFooChain = FOO_CHANNEL_NAME.equals(channelName);
        client.setUserContext(sampleOrg.getPeerAdmin());

        Out.out("Creating install proposal");

        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(chaincodeID);

        if (isFooChain) {
            // on foo chain install from directory.

            ////For GO language and serving just a single user, chaincodeSource is mostly likely the users GOPATH
            installProposalRequest.setChaincodeSourceLocation(new File(TEST_FIXTURES_PATH + "/sdkintegration/gocc/sample1"));
        } else {
            // On bar chain install from an input stream.

            installProposalRequest.setChaincodeInputStream(Util.generateTarGzInputStream(
                    (Paths.get(TEST_FIXTURES_PATH, "/sdkintegration/gocc/sample1", "src", CHAIN_CODE_PATH).toFile()),
                    Paths.get("src", CHAIN_CODE_PATH).toString()));

        }

        installProposalRequest.setChaincodeVersion(CHAIN_CODE_VERSION);

        Out.out("Sending install proposal");

        ////////////////////////////
        // only a client from the same org as the peer can issue an install request
        int numInstallProposal = 0;
        //    Set<String> orgs = orgPeers.keySet();
        //   for (SampleOrg org : testSampleOrgs) {

        Set<Peer> peersFromOrg = sampleOrg.getPeers();
        numInstallProposal = numInstallProposal + peersFromOrg.size();
        responses = client.sendInstallProposal(installProposalRequest, peersFromOrg);

        for (ProposalResponse response : responses) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
            	Out.out("Successful install proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
                successful.add(response);
            } else {
                failed.add(response);
            }
        }

        SDKUtils.getProposalConsistencySets(responses);
        //   }
        Out.out("Received %d install proposal responses. Successful+verified: %d . Failed: %d", numInstallProposal, successful.size(), failed.size());

        if (failed.size() > 0) {
            ProposalResponse first = failed.iterator().next();
            fail("Not enough endorsers for install :" + successful.size() + ".  " + first.getMessage());
        }
		
		
	}
}
