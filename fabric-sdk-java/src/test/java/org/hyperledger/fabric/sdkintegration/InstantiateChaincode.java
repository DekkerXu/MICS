package org.hyperledger.fabric.sdkintegration;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.hyperledger.fabric.sdk.ChaincodeEndorsementPolicy;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstantiateProposalRequest;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.testutils.TestConfig;

public class InstantiateChaincode {
	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String CHAIN_CODE_NAME = "example_cc_go";
    private static final String CHAIN_CODE_PATH = "github.com/example_cc";
    private static final String CHAIN_CODE_VERSION = "1";
    private static final String TEST_FIXTURES_PATH = "src/test/fixture";
    private String name;
    private String namevalue;
    private String moneyvalue;
    private String money;
    
    HFClient client;
    Channel channel;
    boolean installChaincode;
    SampleOrg sampleOrg;
    int delta;
    boolean isFooChain;
	Collection<ProposalResponse> successful;
	Collection<ProposalResponse> failed;





	public InstantiateChaincode(String name, String namevalue, String money, String moneyvalue, HFClient client, Channel channel,
			boolean installChaincode, SampleOrg sampleOrg, int delta, boolean isFooChain,
			Collection<ProposalResponse> successful, Collection<ProposalResponse> failed) {
		super();
		this.name = name;
		this.namevalue = namevalue;
		this.moneyvalue = moneyvalue;
		this.money = money;
		this.client = client;
		this.channel = channel;
		this.installChaincode = installChaincode;
		this.sampleOrg = sampleOrg;
		this.delta = delta;
		this.isFooChain = isFooChain;
		this.successful = successful;
		this.failed = failed;
	}



	public Collection<ProposalResponse> getSuccessful() {
		return successful;
	}



	public Collection<ProposalResponse> getFailed() {
		return failed;
	}


	void instantiateChaincode() throws InvalidArgumentException, ChaincodeEndorsementPolicyParseException, IOException, ProposalException{
		 final ChaincodeID chaincodeID;
		 chaincodeID = ChaincodeID.newBuilder().setName(CHAIN_CODE_NAME)
                 .setVersion(CHAIN_CODE_VERSION)
                 .setPath(CHAIN_CODE_PATH).build();
         Collection<ProposalResponse> responses;
         InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
         instantiateProposalRequest.setProposalWaitTime(testConfig.getProposalWaitTime());
         instantiateProposalRequest.setChaincodeID(chaincodeID);
         instantiateProposalRequest.setFcn("init");
         //instantiateProposalRequest.setArgs(new String[] {"a", "500", "b", "" + (200 + delta)});
         instantiateProposalRequest.setArgs(new String[] {"1",name, namevalue,money,moneyvalue});
         Map<String, byte[]> tm = new HashMap<>();
         tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
         tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
         instantiateProposalRequest.setTransientMap(tm);

         /*
           policy OR(Org1MSP.member, Org2MSP.member) meaning 1 signature from someone in either Org1 or Org2
           See README.md Chaincode endorsement policies section for more details.
         */
         ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
         chaincodeEndorsementPolicy.fromYamlFile(new File(TEST_FIXTURES_PATH + "/sdkintegration/chaincodeendorsementpolicy.yaml"));
         instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);

         Out.out("Sending instantiateProposalRequest to all peers with arguments: a and b set to 100 and %s respectively", "" + (200 + delta));
         successful.clear();
         failed.clear();

         if (isFooChain) {  //Send responses both ways with specifying peers and by using those on the channel.
             responses = channel.sendInstantiationProposal(instantiateProposalRequest, channel.getPeers());
         } else {
             responses = channel.sendInstantiationProposal(instantiateProposalRequest);

         }
         for (ProposalResponse response : responses) {
             if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                 successful.add(response);
                 Out.out("Succesful instantiate proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName());
             } else {
                 failed.add(response);
             }
         }
         Out.out("Received %d instantiate proposal responses. Successful+verified: %d . Failed: %d", responses.size(), successful.size(), failed.size());
         if (failed.size() > 0) {
             ProposalResponse first = failed.iterator().next();
             fail("Not enough endorsers for instantiate :" + successful.size() + "endorser failed with " + first.getMessage() + ". Was verified:" + first.isVerified());
         }
	}
    }

