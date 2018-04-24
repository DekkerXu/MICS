//package cn.mics.brank.sdk;
//
//import static java.lang.String.format;
//import static org.hyperledger.fabric.sdk.BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.codec.binary.Hex;
//import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset;
//import org.hyperledger.fabric.sdk.BlockInfo;
//import org.hyperledger.fabric.sdk.BlockchainInfo;
//import org.hyperledger.fabric.sdk.Channel;
//import org.hyperledger.fabric.sdk.SDKUtils;
//import org.hyperledger.fabric.sdk.TxReadWriteSetInfo;
//import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
//import org.hyperledger.fabric.sdk.exception.InvalidProtocolBufferRuntimeException;
//import org.hyperledger.fabric.sdk.exception.ProposalException;
//
//public class BlockWalker {
//	  private static final Map<String, String> TX_EXPECTED;
//	    static {
//	        TX_EXPECTED = new HashMap<>();
//	        TX_EXPECTED.put("readset1", "Missing readset for channel bar block 1");
//	        TX_EXPECTED.put("writeset1", "Missing writeset for channel bar block 1");
//	    }
//	static String printableString(final String string) {
//        int maxLogStringLength = 64;
//        if (string == null || string.length() == 0) {
//            return string;
//        }
//
//        String ret = string.replaceAll("[^\\p{Print}]", "?");
//
//        ret = ret.substring(0, Math.min(ret.length(), maxLogStringLength)) + (ret.length() > maxLogStringLength ? "..." : "");
//
//        return ret;
//
//    }
//	void blockWalker(Channel channel) throws InvalidArgumentException, ProposalException, IOException {
//        try {
//            BlockchainInfo channelInfo = channel.queryBlockchainInfo();
//
//            for (long current = channelInfo.getHeight() - 1; current > -1; --current) {
//                BlockInfo returnedBlock = channel.queryBlockByNumber(current);
//                final long blockNumber = returnedBlock.getBlockNumber();
//
//                Out.out("current block number %d has data hash: %s", blockNumber, Hex.encodeHexString(returnedBlock.getDataHash()));
//                Out.out("current block number %d has previous hash id: %s", blockNumber, Hex.encodeHexString(returnedBlock.getPreviousHash()));
//                Out.out("current block number %d has calculated block hash is %s", blockNumber, Hex.encodeHexString(SDKUtils.calculateBlockHash(blockNumber, returnedBlock.getPreviousHash(), returnedBlock.getDataHash())));
//
//                final int envelopeCount = returnedBlock.getEnvelopeCount();
//                assertEquals(1, envelopeCount);
//                Out.out("current block number %d has %d envelope count:", blockNumber, returnedBlock.getEnvelopeCount());
//                int i = 0;
//                for (BlockInfo.EnvelopeInfo envelopeInfo : returnedBlock.getEnvelopeInfos()) {
//                    ++i;
//
//                    Out.out("  Transaction number %d has transaction id: %s", i, envelopeInfo.getTransactionID());
//                    final String channelId = envelopeInfo.getChannelId();
//                    assertTrue("foo".equals(channelId) || "bar".equals(channelId));
//
//                    Out.out("  Transaction number %d has channel id: %s", i, channelId);
//                    Out.out("  Transaction number %d has epoch: %d", i, envelopeInfo.getEpoch());
//                    Out.out("  Transaction number %d has transaction timestamp: %tB %<te,  %<tY  %<tT %<Tp", i, envelopeInfo.getTimestamp());
//                    Out.out("  Transaction number %d has type id: %s", i, "" + envelopeInfo.getType());
//
//                    if (envelopeInfo.getType() == TRANSACTION_ENVELOPE) {
//                        BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeInfo;
//
//                        Out.out("  Transaction number %d has %d actions", i, transactionEnvelopeInfo.getTransactionActionInfoCount());
//                        assertEquals(1, transactionEnvelopeInfo.getTransactionActionInfoCount()); // for now there is only 1 action per transaction.
//                        Out.out("  Transaction number %d isValid %b", i, transactionEnvelopeInfo.isValid());
//                        assertEquals(transactionEnvelopeInfo.isValid(), true);
//                        Out.out("  Transaction number %d validation code %d", i, transactionEnvelopeInfo.getValidationCode());
//                        assertEquals(0, transactionEnvelopeInfo.getValidationCode());
//
//                        int j = 0;
//                        for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo transactionActionInfo : transactionEnvelopeInfo.getTransactionActionInfos()) {
//                            ++j;
//                            Out.out("   Transaction action %d has response status %d", j, transactionActionInfo.getResponseStatus());
//                            assertEquals(200, transactionActionInfo.getResponseStatus());
//                            Out.out("   Transaction action %d has response message bytes as string: %s", j,
//                                    printableString(new String(transactionActionInfo.getResponseMessageBytes(), "UTF-8")));
//                            Out.out("   Transaction action %d has %d endorsements", j, transactionActionInfo.getEndorsementsCount());
//                            assertEquals(2, transactionActionInfo.getEndorsementsCount());
//
//                            for (int n = 0; n < transactionActionInfo.getEndorsementsCount(); ++n) {
//                                BlockInfo.EndorserInfo endorserInfo = transactionActionInfo.getEndorsementInfo(n);
//                                Out.out("Endorser %d signature: %s", n, Hex.encodeHexString(endorserInfo.getSignature()));
//                                Out.out("Endorser %d endorser: %s", n, new String(endorserInfo.getEndorser(), "UTF-8"));
//                            }
//                            Out.out("   Transaction action %d has %d chaincode input arguments", j, transactionActionInfo.getChaincodeInputArgsCount());
//                            for (int z = 0; z < transactionActionInfo.getChaincodeInputArgsCount(); ++z) {
//                                Out.out("     Transaction action %d has chaincode input argument %d is: %s", j, z,
//                                        printableString(new String(transactionActionInfo.getChaincodeInputArgs(z), "UTF-8")));
//                            }
//
//                            Out.out("   Transaction action %d proposal response status: %d", j,
//                                    transactionActionInfo.getProposalResponseStatus());
//                            Out.out("   Transaction action %d proposal response payload: %s", j,
//                                    printableString(new String(transactionActionInfo.getProposalResponsePayload())));
//
//                            TxReadWriteSetInfo rwsetInfo = transactionActionInfo.getTxReadWriteSet();
//                            if (null != rwsetInfo) {
//                                Out.out("   Transaction action %d has %d name space read write sets", j, rwsetInfo.getNsRwsetCount());
//
//                                for (TxReadWriteSetInfo.NsRwsetInfo nsRwsetInfo : rwsetInfo.getNsRwsetInfos()) {
//                                    final String namespace = nsRwsetInfo.getNamespace();
//                                    KvRwset.KVRWSet rws = nsRwsetInfo.getRwset();
//
//                                    int rs = -1;
//                                    for (KvRwset.KVRead readList : rws.getReadsList()) {
//                                        rs++;
//
//                                        Out.out("     Namespace %s read set %d key %s  version [%d:%d]", namespace, rs, readList.getKey(),
//                                                readList.getVersion().getBlockNum(), readList.getVersion().getTxNum());
//
//                                        if ("bar".equals(channelId) && blockNumber == 2) {
//                                            if ("example_cc_go".equals(namespace)) {
//                                                if (rs == 0) {
//                                                    assertEquals("a", readList.getKey());
//                                                    assertEquals(1, readList.getVersion().getBlockNum());
//                                                    assertEquals(0, readList.getVersion().getTxNum());
//                                                } else if (rs == 1) {
//                                                    assertEquals("b", readList.getKey());
//                                                    assertEquals(1, readList.getVersion().getBlockNum());
//                                                    assertEquals(0, readList.getVersion().getTxNum());
//                                                } else {
//                                                    fail(format("unexpected readset %d", rs));
//                                                }
//
//                                                TX_EXPECTED.remove("readset1");
//                                            }
//                                        }
//                                    }
//
//                                    rs = -1;
//                                    for (KvRwset.KVWrite writeList : rws.getWritesList()) {
//                                        rs++;
//                                        String valAsString = printableString(new String(writeList.getValue().toByteArray(), "UTF-8"));
//
//                                        Out.out("     Namespace %s write set %d key %s has value '%s' ", namespace, rs,
//                                                writeList.getKey(),
//                                                valAsString);
//
//                                        if ("bar".equals(channelId) && blockNumber == 2) {
//                                            if (rs == 0) {
//                                                assertEquals("a", writeList.getKey());
//                                                assertEquals("400", valAsString);
//                                            } else if (rs == 1) {
//                                                assertEquals("b", writeList.getKey());
//                                                assertEquals("400", valAsString);
//                                            } else {
//                                                fail(format("unexpected writeset %d", rs));
//                                            }
//
//                                            TX_EXPECTED.remove("writeset1");
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (!TX_EXPECTED.isEmpty()) {
//                fail(TX_EXPECTED.get(0));
//            }
//        } catch (InvalidProtocolBufferRuntimeException e) {
//            throw e.getCause();
//        }
//    }
//}
