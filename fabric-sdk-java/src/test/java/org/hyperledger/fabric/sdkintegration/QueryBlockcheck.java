package org.hyperledger.fabric.sdkintegration;

import static org.junit.Assert.assertEquals;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.TransactionInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

public class QueryBlockcheck {
	public static void queryBlockcheck(String testTxID,String channelName,Channel channel) throws ProposalException, InvalidArgumentException {
	// Peer queryPeer = peerSet.iterator().next();
	// out("Using peer %s for channel queries", queryPeer.getName());

	BlockchainInfo channelInfo = channel.queryBlockchainInfo();
	Out.out("Channel info for : " + channelName);
	Out.out("Channel height: " + channelInfo.getHeight());
	String chainCurrentHash = Hex.encodeHexString(channelInfo.getCurrentBlockHash());
	String chainPreviousHash = Hex.encodeHexString(channelInfo.getPreviousBlockHash());
	Out.out("Chain current block hash: " + chainCurrentHash);
	Out.out("Chainl previous block hash: " + chainPreviousHash);

	// Query by block number. Should return latest block, i.e. block number 2
	BlockInfo returnedBlock = channel.queryBlockByNumber(channelInfo.getHeight() - 1);
	String previousHash = Hex.encodeHexString(returnedBlock.getPreviousHash());
	Out.out("queryBlockByNumber returned correct block with blockNumber " + returnedBlock.getBlockNumber()
			+ " \n previous_hash " + previousHash);
	assertEquals(channelInfo.getHeight() - 1, returnedBlock.getBlockNumber());
	assertEquals(chainPreviousHash, previousHash);

	// Query by block hash. Using latest block's previous hash so should return
	// block number 1
	byte[] hashQuery = returnedBlock.getPreviousHash();
	returnedBlock = channel.queryBlockByHash(hashQuery);
	Out.out("queryBlockByHash returned block with blockNumber " + returnedBlock.getBlockNumber());
	assertEquals(channelInfo.getHeight() - 2, returnedBlock.getBlockNumber());

	// Query block by TxID. Since it's the last TxID, should be block 2
	returnedBlock = channel.queryBlockByTransactionID(testTxID);
	Out.out("queryBlockByTxID returned block with blockNumber " + returnedBlock.getBlockNumber());
	assertEquals(channelInfo.getHeight() - 1, returnedBlock.getBlockNumber());

	// query transaction by ID
	TransactionInfo txInfo = channel.queryTransactionByID(testTxID);
	Out.out("QueryTransactionByID returned TransactionInfo: txID " + txInfo.getTransactionID()
			+ "\n     validation code " + txInfo.getValidationCode().getNumber());

	Out.out("Running for Channel %s done", channelName);

    } 
 }
