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
 * Create RLP encoded transaction, implementation <a href="http://www.edgematrix.pro/api/paper.pdf">yellow
 * paper</a>.
 */
public class RtcMsgEncoder {

    /**
     * Use for RtcMsg
     *
     * @return signature
     */
    public static byte[] signMessage(
            RawRtcMsg rawTransaction, long chainId, Credentials credentials) {

        byte[] encodedTransaction = encode(rawTransaction, chainId);
        Sign.SignatureData signatureData =
                Sign.signMessage(encodedTransaction, credentials.getEcKeyPair());

        Sign.SignatureData eip155SignatureData = createEip155SignatureData(signatureData, chainId);
        return encode(rawTransaction, eip155SignatureData);
    }

    public static Sign.SignatureData createEip155SignatureData(
            Sign.SignatureData signatureData, long chainId) {
        BigInteger v = Numeric.toBigInt(signatureData.getV());
        v = v.subtract(BigInteger.valueOf(LOWER_REAL_V));
        v = v.add(BigInteger.valueOf(chainId).multiply(BigInteger.valueOf(2)));
        v = v.add(BigInteger.valueOf(CHAIN_ID_INC));

        return new Sign.SignatureData(v.toByteArray(), signatureData.getR(), signatureData.getS());
    }

    public static byte[] encode(RawRtcMsg rawTransaction) {
        return encode(rawTransaction, null);
    }

    /**
     * Encode RtcMsg with chainId together
     *
     * @return encoded bytes
     */
    public static byte[] encode(RawRtcMsg rawTransaction, long chainId) {
        Sign.SignatureData signatureData =
                new Sign.SignatureData(longToBytes(chainId), new byte[]{}, new byte[]{});
        return encode(rawTransaction, signatureData);
    }

    public static byte[] encode(RawRtcMsg rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        byte[] encoded = RlpEncoder.encode(rlpList);

        return encoded;
    }

    private static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static List<RlpType> asRlpValues(
            RawRtcMsg rawTransaction, Sign.SignatureData signatureData) {
        return rawTransaction.getTransaction().asRlpValues(signatureData);
    }
}
