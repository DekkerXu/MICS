/*
 *  Copyright 2016, 2017 DTCC, Fujitsu Australia Software Technology, IBM - All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.mics.brank.sdk;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.TransactionEventException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import cn.mics.brank.fabric.entity.SampleOrg;
import cn.mics.brank.fabric.entity.SampleUser;
//import cn.mics.brank.fabric.entity.SampleOrg;
//import cn.mics.brank.fabric.entity.SampleUser;
import cn.mics.brank.fabric.util.Util;
import cn.mics.brank.testutils.TestConfig;
//import cn.mics.brank.testutils.TestConfigHelper;
import cn.mics.brank.testutils.TestConfigHelper;

import static java.lang.String.format;
import static org.junit.Assert.fail;

/**
 * Test end to end scenario
 */
public class First {

	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String TEST_ADMIN_NAME = "admin";
	private static final String TESTUSER_1_NAME = "user1";
	private static final String FOO_CHANNEL_NAME = "foo";
	private static final String BAR_CHANNEL_NAME = "bar";
	//Channel channel;
	HFClient client;
	SampleOrg sampleOrg;
	Collection<ProposalResponse> failed;
	ChaincodeID chaincodeID;
	Channel fooChannel;
	TransactionEvent transactionEvent;
	Collection<ProposalResponse> successful;
	String testTxID = null; // save the CC invoke TxID and use in queries
	static String testTxID1 = null;
	private final TestConfigHelper configHelper = new TestConfigHelper();

	private Collection<SampleOrg> testSampleOrgs;


	public Collection<SampleOrg> checkConfig() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, MalformedURLException {
		Out.out("\n\n\nRUNNING: End2endIT.\n");
		configHelper.clearConfig();
		configHelper.customizeConfig();

		testSampleOrgs = testConfig.getIntegrationTestsSampleOrgs();
		// Set up hfca for each sample org

		for (SampleOrg sampleOrg : testSampleOrgs) {
			sampleOrg.setCAClient(HFCAClient.createNewInstance(sampleOrg.getCALocation(), sampleOrg.getCAProperties()));
		}
		return testSampleOrgs;
	}

	public void clearConfig() {
		try {
		//	configHelper.clearConfig();
		} catch (Exception e) {
		}
	}


	public Channel setup(Collection<SampleOrg> testSampleOrgs) {

		try {

			////////////////////////////
			// Setup client

			// Create instance of client.
			client = HFClient.createNewInstance();

			client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

			// client.setMemberServices(peerOrg1FabricCA);

			////////////////////////////
			// Set up USERS

			// Persistence is not part of SDK. Sample file store is for demonstration
			// purposes only!
			// MUST be replaced with more robust application implementation (Database, LDAP)
			// File sampleStoreFile = new File(System.getProperty("java.io.tmpdir") +
			// "/HFCSampletest.properties");
			File sampleStoreFile = new File("/tmp/HFCSampletest.properties");
			if (sampleStoreFile.exists()) { // For testing start fresh
				sampleStoreFile.delete();
			}

			final SampleStore sampleStore = new SampleStore(sampleStoreFile);
			// sampleStoreFile.deleteOnExit();

			// SampleUser can be any implementation that implements
			// org.hyperledger.fabric.sdk.User Interface

			////////////////////////////
			// get users for all orgs

			for (SampleOrg sampleOrg : testSampleOrgs) {

				HFCAClient ca = sampleOrg.getCAClient();
				final String orgName = sampleOrg.getName();
				final String mspid = sampleOrg.getMSPID();
				ca.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
				SampleUser admin = sampleStore.getMember(TEST_ADMIN_NAME, orgName);
				if (!admin.isEnrolled()) { // Preregistered admin only needs to be enrolled with Fabric caClient.
					admin.setEnrollment(ca.enroll(admin.getName(), "adminpw"));
					admin.setMspId(mspid);
				}

				sampleOrg.setAdmin(admin); // The admin of this org --

				SampleUser user = sampleStore.getMember(TESTUSER_1_NAME, sampleOrg.getName());
				if (!user.isRegistered()) { // users need to be registered AND enrolled
					RegistrationRequest rr = new RegistrationRequest(user.getName(), "org1.department1");
					user.setEnrollmentSecret(ca.register(rr, admin));
				}
				if (!user.isEnrolled()) {
					user.setEnrollment(ca.enroll(user.getName(), user.getEnrollmentSecret()));
					user.setMspId(mspid);
				}
				sampleOrg.addUser(user); // Remember user belongs to this Org

				final String sampleOrgName = sampleOrg.getName();
				final String sampleOrgDomainName = sampleOrg.getDomainName();

				// src/test/fixture/sdkintegration/e2e-2Orgs/channel/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/

				SampleUser peerOrgAdmin = sampleStore.getMember(sampleOrgName + "Admin", sampleOrgName,
						sampleOrg.getMSPID(),
						Util.findFileSk(Paths.get(testConfig.getTestChannelPath(), "crypto-config/peerOrganizations/",
								sampleOrgDomainName, format("/users/Admin@%s/msp/keystore", sampleOrgDomainName))
								.toFile()),
						Paths.get(testConfig.getTestChannelPath(), "crypto-config/peerOrganizations/",
								sampleOrgDomainName, format("/users/Admin@%s/msp/signcerts/Admin@%s-cert.pem",
										sampleOrgDomainName, sampleOrgDomainName))
								.toFile());

				sampleOrg.setPeerAdmin(peerOrgAdmin); // A special user that can create channels, join peers and install
														// chaincode

			}

			////////////////////////////
			// Construct and run the channels
		   sampleOrg = testConfig.getIntegrationTestsSampleOrg("peerOrg1");
			CreatChannel constructChannel = new CreatChannel(FOO_CHANNEL_NAME, client, sampleOrg);
			constructChannel.constructChannel();
			fooChannel = constructChannel.getNewChannel();

			RunChannel runChannel = new RunChannel(client, fooChannel, true, sampleOrg);
			runChannel.runChannel();
			chaincodeID = runChannel.getChaincodeID();
		   successful = runChannel.getSuccessful();
		   failed = runChannel.getFailed();
		   fooChannel.sendTransaction(successful, fooChannel.getOrderers());
			Out.out("\n");
			Out.out("Set up folks!");
		} catch (Exception e) {
			e.printStackTrace();

			fail(e.getMessage());
		}
		return fooChannel;

	}
	  public void invokeapplication(Channel channel,String ID,String value,String searchName) {
			try {
			Collection<Orderer> orderers = channel.getOrderers();
			Collection<Peer> channelPeers = channel.getPeers();
		
				Invoke invoke = new Invoke(channelPeers, orderers, transactionEvent, channel, client, sampleOrg,
						successful, failed, TESTUSER_1_NAME, chaincodeID,ID,value, searchName);
				invoke.invoke();
				transactionEvent = invoke.getTransactionEvent();
				successful=invoke.getSuccessful();
				////////////////////////////
				channel.sendTransaction(successful, orderers).thenApply(transactionEvent -> {
				testTxID=transactionEvent.getTransactionID();
				return null;
			
		}).exceptionally(e -> {
			if (e instanceof TransactionEventException) {
				BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
				if (te != null) {
					fail(format("Transaction with txid %s failed. %s", te.getTransactionID(), e.getMessage()));
				}
			}
			fail(format("Test failed with %s exception %s", e.getClass().getName(), e.getMessage()));
			return null;
		}).get(testConfig.getTransactionWaitTime(), TimeUnit.SECONDS);

		//	fooChannel.shutdown(true); // Force foo channel to shutdown clean up resources.
			///bar
			
			Out.out("Invoke folks!2");
		} catch (Exception e) {
			e.printStackTrace();

			fail(e.getMessage());
		}
	}
public String queryapplication(Channel channel,String ID) {
	String check = null;
	try {
			Collection<Orderer> orderers = channel.getOrderers();
			Collection<Peer> channelPeers = channel.getPeers();
			Query.query(ID,testTxID, channelPeers, orderers, channel, client,sampleOrg,successful, failed, TESTUSER_1_NAME, chaincodeID);		
			check=Query.getPayload();
			Out.out("Query folks!3");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	return check;
	}
public String checkapplication(Channel channel,String contractID,String medicareID) {
	String check = null;
	try {
			Collection<Orderer> orderers = channel.getOrderers();
			Collection<Peer> channelPeers = channel.getPeers();
			Out.out(contractID+"Invoke folks!2"+medicareID);
			CheckChaincode.CheckChaincode(contractID, medicareID,testTxID, channelPeers, orderers, channel, client,sampleOrg,successful, failed, TESTUSER_1_NAME, chaincodeID);
			check=CheckChaincode.getPayload();
			Out.out("check folks!5");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	return check;
	}

}
