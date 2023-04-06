/*
 * Copyright 2023 edgematrix Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pro.edgematrix;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;
import pro.edgematrix.common.PrecompileAddress;
import pro.edgematrix.crypto.RawRtcMsg;
import pro.edgematrix.crypto.RawTelegram;
import pro.edgematrix.crypto.RtcMsgEncoder;
import pro.edgematrix.crypto.TelegramEncoder;
import pro.edgematrix.protocol.methods.response.EdgeSendRtcMsg;
import pro.edgematrix.protocol.methods.response.EdgeSendTelegram;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * JSON-RPC Request service.
 */
public class EdgeService {
    /**
     * send a telegram to edge-matrix node
     *
     * @param web3j           EdgeWeb3j instance
     * @param chainId         EMC chain id, 1 is main net
     * @param nonce           nonce for caller
     * @param contractAddress address to
     * @param credentials     caller's credential
     * @param data            data for call, "" is empty data
     * @return deserialized JSON-RPC responses
     */
    public String sendTelegram(EdgeWeb3j web3j, long chainId, BigInteger nonce, String contractAddress, Credentials credentials, String data) {
        if (web3j == null) return null;

        BigInteger gasPrice = BigInteger.valueOf(0);
        BigInteger gasLimit = BigInteger.valueOf(0);
        BigInteger value = BigInteger.valueOf(0);
        RawTelegram rawTransaction = RawTelegram.createTransaction(nonce, gasPrice, gasLimit, contractAddress, value, data);

        byte[] signMessage = TelegramEncoder.signMessage(rawTransaction, chainId, credentials);
        String signData = Numeric.toHexString(signMessage);

        if (!"".equals(signData)) {
            try {
                EdgeSendTelegram send = web3j.edgeSendRawTelegram(signData).send();
                if (send.hasError()) {
                    throw new RuntimeException(send.getError().getMessage());
                } else {
                    return send.getResult();
                }
            } catch (IOException e) {
                throw new RuntimeException("send telegram exception");
            }
        }
        return null;
    }

    /**
     * create a rtc subject on edge-matrix net
     *
     * @param web3j       EdgeWeb3j instance
     * @param chainId     EMC chain id, 1 is main net
     * @param nonce       nonce for caller
     * @param credentials caller's credential
     * @return deserialized JSON-RPC responses
     */
    public String createRtcSubject(EdgeWeb3j web3j, long chainId, BigInteger nonce, Credentials credentials) {
        if (web3j == null) return null;

        BigInteger gasPrice = BigInteger.valueOf(0);
        BigInteger gasLimit = BigInteger.valueOf(0);
        BigInteger value = BigInteger.valueOf(0);
        RawTelegram rawTransaction = RawTelegram.createTransaction(nonce, gasPrice, gasLimit, PrecompileAddress.EDGE_RTC_SUBJECT.getAddress(), value, "");

        byte[] signMessage = TelegramEncoder.signMessage(rawTransaction, chainId, credentials);
        String signData = Numeric.toHexString(signMessage);

        if (!"".equals(signData)) {
            try {
                EdgeSendTelegram send = web3j.edgeSendRawTelegram(signData).send();
                if (send.hasError()) {
                    throw new RuntimeException(send.getError().getMessage());
                } else {
                    return send.getResult();
                }
            } catch (IOException e) {
                throw new RuntimeException("send telegram exception");
            }
        }
        return null;
    }

    /**
     * send a message to rtc subject
     *
     * @param web3j       EdgeWeb3j instance
     * @param chainId     EMC chain id, 1 is main net
     * @param credentials caller's credential
     * @param rtcMsg      RtcMsg instance to be sent
     * @return deserialized JSON-RPC responses
     */
    public String sendRtcMsg(EdgeWeb3j web3j, long chainId, Credentials credentials, RtcMsg rtcMsg) {
        if (web3j == null) return null;

        RawRtcMsg rawTransaction = RawRtcMsg.createRtcMsg(rtcMsg.getSubject(), rtcMsg.getApplication(), rtcMsg.getContent(), rtcMsg.getTo());

        byte[] signMessage = RtcMsgEncoder.signMessage(rawTransaction, chainId, credentials);
        String signData = Numeric.toHexString(signMessage);

        if (!"".equals(signData)) {
            try {
                EdgeSendRtcMsg send = web3j.edgeSendRawMsg(signData).send();
                if (send.hasError()) {
                    throw new RuntimeException(send.getError().getMessage());
                } else {
                    return send.getResult();
                }
            } catch (IOException e) {
                throw new RuntimeException("send rtcMsg exception");
            }
        }
        return null;
    }

    /**
     * get next nonce for caller
     *
     * @param web3j   EdgeWeb3j instance
     * @param address caller's address - e.g. "0x0aF137aa3EcC7d10d926013ee34049AfA77382e6"
     * @return number of nonce, will be used for sendTelegram
     * @throws ExecutionException ExecutionException
     * @throws InterruptedException InterruptedException
     */
    public BigInteger getNextTelegramNonce(EdgeWeb3j web3j, String address) throws ExecutionException, InterruptedException {
        if (web3j == null) return null;

        EthGetTransactionCount ethGetTransactionCount = web3j.edgeGetTelegramCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount != null) {
            return ethGetTransactionCount.getTransactionCount();
        }
        return null;
    }

    /**
     * get a receipt of sendTelegram call
     *
     * @param web3j        EdgeWeb3j instance
     * @param telegramHash hashString returned by a sendTelegram call,  - e.g. "0x6b7c880d58fef940e7b7932b9239d2737b4a71583c4640757e234de94bb98c0b"
     * @return EthGetTransactionReceipt
     * @throws IOException IOException
     */
    public TransactionReceipt getTelegramReceipt(EdgeWeb3j web3j, String telegramHash) throws IOException {
        EthGetTransactionReceipt transactionReceipt = web3j.edgeGetTelegramReceipt(telegramHash).send();
        if (transactionReceipt != null && transactionReceipt.getTransactionReceipt().isPresent()) {
            return transactionReceipt.getTransactionReceipt().get();
        } else {
            return null;
        }
    }
}
