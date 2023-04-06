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
package pro.edgematrix.crypto;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.crypto.exception.CryptoWeb3jException;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;

import static org.web3j.crypto.Sign.CHAIN_ID_INC;
import static org.web3j.crypto.Sign.LOWER_REAL_V;

/**
 * Create RLP encoded transaction, implementation as per p4 of the <a
 * href="http://gavwood.com/paper.pdf">yellow paper</a>.
 */
public class TelegramEncoder {

    /**
     * Use for new transactions Eip1559 (this txs has a new field chainId) or an old one before
     * Eip155
     *
     * @return signature
     */
    public static byte[] signMessage(RawTelegram rawTransaction, Credentials credentials) {
        byte[] encodedTransaction = encode(rawTransaction);
        org.web3j.crypto.Sign.SignatureData signatureData =
                org.web3j.crypto.Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        return encode(rawTransaction, signatureData);
    }

    /**
     * Use for legacy txs (after Eip155 before Eip1559)
     *
     * @return signature
     */
    public static byte[] signMessage(
            RawTelegram rawTransaction, long chainId, Credentials credentials) {

        // Eip1559: Tx has ChainId inside
        if (rawTransaction.getType().isEip1559()) {
            return signMessage(rawTransaction, credentials);
        }

        byte[] encodedTransaction = encode(rawTransaction, chainId);
        org.web3j.crypto.Sign.SignatureData signatureData =
                org.web3j.crypto.Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        org.web3j.crypto.Sign.SignatureData eip155SignatureData = createEip155SignatureData(signatureData, chainId);
        return encode(rawTransaction, eip155SignatureData);
    }


    public static org.web3j.crypto.Sign.SignatureData createEip155SignatureData(
            org.web3j.crypto.Sign.SignatureData signatureData, long chainId) {
        BigInteger v = Numeric.toBigInt(signatureData.getV());
        v = v.subtract(BigInteger.valueOf(LOWER_REAL_V));
        v = v.add(BigInteger.valueOf(chainId).multiply(BigInteger.valueOf(2)));
        v = v.add(BigInteger.valueOf(CHAIN_ID_INC));

        return new org.web3j.crypto.Sign.SignatureData(v.toByteArray(), signatureData.getR(), signatureData.getS());
    }


    public static byte[] encode(RawTelegram rawTransaction) {
        return encode(rawTransaction, null);
    }

    /**
     * Encode transaction with chainId together, it make sense only for Legacy transactions
     *
     * @return encoded bytes
     */
    public static byte[] encode(RawTelegram rawTransaction, long chainId) {
        if (!rawTransaction.getType().isLegacy()) {
            throw new CryptoWeb3jException("Incorrect transaction type. Tx type should be Legacy.");
        }

        org.web3j.crypto.Sign.SignatureData signatureData =
                new org.web3j.crypto.Sign.SignatureData(longToBytes(chainId), new byte[]{}, new byte[]{});
        return encode(rawTransaction, signatureData);
    }

    public static byte[] encode(RawTelegram rawTransaction, org.web3j.crypto.Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        byte[] encoded = RlpEncoder.encode(rlpList);

        if (rawTransaction.getType().isEip1559() || rawTransaction.getType().isEip2930()) {
            return ByteBuffer.allocate(encoded.length + 1)
                    .put(rawTransaction.getType().getRlpType())
                    .put(encoded)
                    .array();
        }
        return encoded;
    }

    private static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static List<RlpType> asRlpValues(
            RawTelegram rawTransaction, Sign.SignatureData signatureData) {
        return rawTransaction.getTransaction().asRlpValues(signatureData);
    }
}
