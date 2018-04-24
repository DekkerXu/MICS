package cn.mics.brank.sdk;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ChannelConfiguration;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;

import cn.mics.brank.fabric.entity.SampleOrg;
import cn.mics.brank.testutils.TestConfig;


public class CreatChannel {
	private static final TestConfig testConfig = TestConfig.getConfig();
	private static final String TEST_FIXTURES_PATH = "src/main/fixture";
	String name;
	HFClient client;
	SampleOrg sampleOrg;
	Channel newChannel;
	Collection<Orderer> orderers = new LinkedList<>();

	public CreatChannel(String name, HFClient client, SampleOrg sampleOrg) {
		super();
		this.name = name;
		this.client = client;
		this.sampleOrg = sampleOrg;
	}

	public Channel getNewChannel() {
		return newChannel;
	}
	public void  constructChannel() throws Exception {
	        ////////////////////////////
	        //Construct the channel
	        //

		     Out.out("Constructing channel %s", name);

	        //Only peer Admin org
	        client.setUserContext(sampleOrg.getPeerAdmin());


	        for (String orderName : sampleOrg.getOrdererNames()) {

	            Properties ordererProperties = testConfig.getOrdererProperties(orderName);

	            //example of setting keepAlive to avoid timeouts on inactive http2 connections.
	            // Under 5 minutes would require changes to server side to accept faster ping rates.
	            ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new Object[] {5L, TimeUnit.MINUTES});
	            ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new Object[] {8L, TimeUnit.SECONDS});

	            orderers.add(client.newOrderer(orderName, sampleOrg.getOrdererLocation(orderName),
	                    ordererProperties));
	        }

	        //Just pick the first orderer in the list to create the channel.

	        Orderer anOrderer = orderers.iterator().next();
	        orderers.remove(anOrderer);

	        ChannelConfiguration channelConfiguration = new ChannelConfiguration(new File(TEST_FIXTURES_PATH + "/sdkintegration/e2e-2Orgs/channel/" + name + ".tx"));

	        //Create channel that has only one signer that is this orgs peer admin. If channel creation policy needed more signature they would need to be added too.
	        newChannel = client.newChannel(name, anOrderer, channelConfiguration, client.getChannelConfigurationSignature(channelConfiguration, sampleOrg.getPeerAdmin()));

	        Out.out("Created channel %s", name);
	        JoinChannel joinChannel=new JoinChannel(newChannel, name, client, sampleOrg);
	        joinChannel.joinChannel();
	        newChannel=joinChannel.getchannel();
	        //JoinChannel.JoinChannel(newChannel, name, client, sampleOrg);

	        for (Orderer orderer : orderers) { //add remaining orderers if any.
	            newChannel.addOrderer(orderer);
	        }

	        for (String eventHubName : sampleOrg.getEventHubNames()) {

	            final Properties eventHubProperties = testConfig.getEventHubProperties(eventHubName);

	            eventHubProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new Object[] {5L, TimeUnit.MINUTES});
	            eventHubProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new Object[] {8L, TimeUnit.SECONDS});

	            EventHub eventHub = client.newEventHub(eventHubName, sampleOrg.getEventHubLocation(eventHubName),
	                    eventHubProperties);
	            newChannel.addEventHub(eventHub);
	        }

	        newChannel.initialize();

	        Out.out("Finished initialization channel %s", name);
		

	        

	    }

}
