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
import org.web3j.utils.Numeric;
import pro.edgematrix.common.ChainId;
import pro.edgematrix.common.PrecompileAddress;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramEncoderTest {

    @Test
    public void testSignMessageAfterEip155() {
        byte[] signedMessage =
                TelegramEncoder.signMessage(
                        createEip155RawTransaction(),
                        ChainId.TEST_NET.getId(),
                        SampleKeys.CREDENTIALS);

        String hexMessage = Numeric.toHexString(signedMessage);
        String SIGN_RESULT_ETH_EXAMPLE = "0xf85d038080940000000000000000000000000000000000003101808028a0bec9c466527c5d86bba8f9df2c4db78fa19948ed9ff05de532b7607cff939474a0495b85bc7cc2bc8b69860f3033523afa3ef4f839cfc2687c0f4bbe60b718df5d";
        assertEquals(SIGN_RESULT_ETH_EXAMPLE, hexMessage);
    }


    static RawTelegram createEip155RawTransaction() {
        return RawTelegram.createEtherTransaction(
                BigInteger.valueOf(3),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                PrecompileAddress.EDGE_RTC_SUBJECT.getAddress(),
                BigInteger.valueOf(0));
    }

}
