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

import org.junit.jupiter.api.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Numeric;
import pro.edgematrix.common.ChainId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RtcMsgEncoderTest {

    // Example from https://eips.ethereum.org/EIPS/eip-155
    private final String SIGN_RESULT_EXAMPLE =
            "0xf8acb84230783865656233333832333961646132326438316666623761646339393566653331613464316463326437303162633861353866666665356235336531343238316589656467655f636861748568656c6c6f94000000000000000000000000000000000000000028a0c2ef640e01996a92a323233b847348b8ab3107673e4ad120c6561a24a24e1267a060e348a187ed7bd7668783ebe1e87db36d59ea72cda99560dc78c048668b42d2";

    @Test
    public void testSignMessageAfterEip155() {
        byte[] signedMessage =
                RtcMsgEncoder.signMessage(
                        createRtcMsg(),
                        ChainId.TEST_NET.getId(),
                        SampleKeys.CREDENTIALS);

        String hexMessage = Numeric.toHexString(signedMessage);
        System.out.println("Actual  : " + hexMessage);
        System.out.println("Expected: " + SIGN_RESULT_EXAMPLE);
        assertEquals(SIGN_RESULT_EXAMPLE, hexMessage);
    }

    private static RawRtcMsg createRtcMsg() {
        return RawRtcMsg.createRtcMsg(
                "0x8eeb338239ada22d81ffb7adc995fe31a4d1dc2d701bc8a58fffe5b53e14281e",
                "edge_chat",
                "hello",
                new Address("0x0").toString());
    }

}
