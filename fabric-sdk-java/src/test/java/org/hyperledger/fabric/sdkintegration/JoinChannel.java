package org.hyperledger.fabric.sdkintegration;

import java.util.Properties;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.testutils.TestConfig;
import org.hyperledger.fabric.sdk.Channel;
public class JoinChannel {
	Channel channel;
	String name;
	HFClient client;
	SampleOrg sampleOrg;
	public JoinChannel(Channel channel, String name, HFClient client, SampleOrg sampleOrg) {
		super();
		this.channel = channel;
		this.name = name;
		this.client = client;
		this.sampleOrg = sampleOrg;
		
	}
	
	public Channel getchannel() {
		return channel;
	}
	public void setchannel(Channel channel) {
		this.channel = channel;
	}

	private static final TestConfig testConfig = TestConfig.getConfig();
	public void joinChannel() throws InvalidArgumentException, ProposalException{
	  for (String peerName : sampleOrg.getPeerNames()) {
          String peerLocation = sampleOrg.getPeerLocation(peerName);

          Properties peerProperties = testConfig.getPeerProperties(peerName); //test properties for peer.. if any.
          if (peerProperties == null) {
              peerProperties = new Properties();
          }
          //Example of setting specific options on grpc's NettyChannelBuilder
          peerProperties.put("grpc.NettyChannelBuilderOption.maxInboundMessageSize", 9000000);

          Peer peer = client.newPeer(peerName, peerLocation, peerProperties);
          channel.joinPeer(peer);
          Out.out("Peer %s joined channel %s", peerName, name);
          sampleOrg.addPeer(peer);
    
      }
	
	}
}
