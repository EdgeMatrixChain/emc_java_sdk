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

public class EdgeService {
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

    public BigInteger getNextTelegramNonce(EdgeWeb3j web3j, String address) throws ExecutionException, InterruptedException {
        if (web3j == null) return null;

        EthGetTransactionCount ethGetTransactionCount = web3j.edgeGetTelegramCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount != null) {
            return ethGetTransactionCount.getTransactionCount();
        }
        return null;
    }

    public TransactionReceipt getTelegramReceipt(EdgeWeb3j web3j, String telegramHash) throws IOException {
        EthGetTransactionReceipt transactionReceipt = web3j.edgeGetTelegramReceipt(telegramHash).send();
        if (transactionReceipt != null && transactionReceipt.getTransactionReceipt().isPresent()) {
            return transactionReceipt.getTransactionReceipt().get();
        } else {
            return null;
        }
    }
}
