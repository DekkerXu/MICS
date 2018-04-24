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

package org.hyperledger.fabric.sdkintegration;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TestConfigHelper;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.exception.TransactionEventException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.testutils.TestConfig;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.main.test.Contract;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static java.lang.String.format;
import static org.junit.Assert.fail;

/**
 * Test end to end scenario
 */
public class End2endIT {

	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String TEST_ADMIN_NAME = "admin";
	private static final String TESTUSER_1_NAME = "user1";
	private static final String FOO_CHANNEL_NAME = "foo";
	private static final String BAR_CHANNEL_NAME = "bar";
	Channel channel;
	HFClient client;
	SampleOrg sampleOrg;
	Collection<ProposalResponse> failed;
	ChaincodeID chaincodeID;
	Channel fooChannel;
	Collection<ProposalResponse> successful;
	String testTxID = null; // save the CC invoke TxID and use in queries
	static String testTxID1 = null;
	private final TestConfigHelper configHelper = new TestConfigHelper();

	private Collection<SampleOrg> testSampleOrgs;
	private String userID="1";
	private String name="a";
	private String namevalue="300";
	private String money="b";
	private String moneyvalue="400";
//	public End2endIT(String name, String namevalue, String money, String moneyvalue) {
//		super();
//		this.namevalue = namevalue;
//		this.moneyvalue = moneyvalue;
//		this.money = money;
//		this.name = name;
//	}
//


	@Before
	public void checkConfig() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, MalformedURLException {
		Out.out("\n\n\nRUNNING: End2endIT.\n");
		configHelper.clearConfig();
		configHelper.customizeConfig();

		testSampleOrgs = testConfig.getIntegrationTestsSampleOrgs();
		// Set up hfca for each sample org

		for (SampleOrg sampleOrg : testSampleOrgs) {
			sampleOrg.setCAClient(HFCAClient.createNewInstance(sampleOrg.getCALocation(), sampleOrg.getCAProperties()));
		}
	}

	@After
	public void clearConfig() {
		try {
		//	configHelper.clearConfig();
		} catch (Exception e) {
		}
	}


	public void setup() {

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

			RunChannel runChannel = new RunChannel(name,namevalue,money,moneyvalue,client, fooChannel, true, sampleOrg, 0);
			runChannel.runChannel();
			chaincodeID = runChannel.getChaincodeID();
		   successful = runChannel.getSuccessful();
			System.out.println("++++++" + successful);
		   failed = runChannel.getFailed();
			Out.out("\n");
			// invoke and query

			channel = fooChannel;
			Collection<Orderer> orderers = channel.getOrderers();
			Collection<Peer> channelPeers = channel.getPeers();
			final String channelName = channel.getName();
			channel.sendTransaction(successful, orderers).<TransactionEvent>thenApply(transactionEvent -> {
				Invoke invoke = new Invoke(channelPeers, orderers, transactionEvent, channel, client, sampleOrg, 0,
						successful, failed, TESTUSER_1_NAME, chaincodeID);
				invoke.invoke();
				transactionEvent = invoke.getTransactionEvent();
//
//				////////////////////////////
				System.out.println("88888888888888888888888"+transactionEvent.getTransactionID());
				return transactionEvent;
			}).<TransactionEvent>thenApply(transactionEvent -> {
				System.out.println("88888888888888888888888"+transactionEvent.getTransactionID());
				Query query = new Query("a", testTxID, channelPeers, orderers, transactionEvent, channel, client,
						sampleOrg, 0, successful, failed, channelName, chaincodeID);
				Query query1 = new Query("b", testTxID1, channelPeers, orderers, transactionEvent, channel, client,
						sampleOrg, 0, successful, failed, channelName, chaincodeID);

				query.query();
				query1.query();
				testTxID = query.getTestTxID();
				testTxID1 = query1.getTestTxID();
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

			QueryBlockcheck.queryBlockcheck(testTxID, channelName, channel);
			fooChannel.shutdown(true); // Force foo channel to shutdown clean up resources.
///bar
			SampleOrg sampleOrg1 = testConfig.getIntegrationTestsSampleOrg("peerOrg2");
			CreatChannel constructChannel1 = new CreatChannel(BAR_CHANNEL_NAME, client, sampleOrg1);
			constructChannel1.constructChannel();
			Channel barChannel = constructChannel1.getNewChannel();
			RunChannel runChannel1 = new RunChannel(name,namevalue,money,moneyvalue,client, barChannel, true, sampleOrg1, 0);
			runChannel1.runChannel();
			barChannel.shutdown(true);

			// SampleOrg sampleOrg2 = testConfig.getIntegrationTestsSampleOrg("peerOrg1");
			// Channel barChannel1 = CreatChannel.constructChannel(BAR_CHANNEL_NAME, client,
			// sampleOrg2);
			// RunChannel runChannel2=new RunChannel(client, barChannel1, true, sampleOrg2,
			// 0);
			// runChannel2.runChannel();
			// ChaincodeID chaincodeID1=runChannel1.getChaincodeID();
			// Collection<ProposalResponse> successful1=runChannel1.getSuccessful();
			// System.out.println("++++++"+successful1);
			// Collection<ProposalResponse> failed1=runChannel1.getFailed(); //let bar
			// channel just shutdown so we have both scenarios.

			// out("\nTraverse the blocks for chain %s ", barChannel.getName());
			// blockWalker(barChannel);

			Out.out("That's all folks!");

		} catch (Exception e) {
			e.printStackTrace();

			fail(e.getMessage());
		}

	}
	  public void invokeapplication() {
			try {
		   channel = fooChannel;
		   System.out.println("3333"+channel);
			Collection<Orderer> orderers = channel.getOrderers();
			Collection<Peer> channelPeers = channel.getPeers();
			final String channelName = channel.getName();
			channel.sendTransaction(successful, orderers).<TransactionEvent>thenApply(transactionEvent -> {
				Invoke invoke = new Invoke(channelPeers, orderers, transactionEvent, channel, client, sampleOrg, 0,
						successful, failed, TESTUSER_1_NAME, chaincodeID);
				invoke.invoke();
				transactionEvent = invoke.getTransactionEvent();

				////////////////////////////
				System.out.println(transactionEvent);
				System.out.println("444"+channel);
				return transactionEvent;
			
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
			
			Out.out("That's all folks!2");
			System.out.println("444"+channel);
		} catch (Exception e) {
			e.printStackTrace();

			fail(e.getMessage());
		}
			 System.out.println("2222"+channel);
			   System.out.println("2222"+fooChannel);	
	}
	@Test
	public void testsue() {
		System.out.println("2222"+fooChannel);	
		setup();
		//invokeapplication();
		//invokeapplication();
		System.out.println("2222"+channel);	
	}

}
