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

    @Test
    public void testSignTelegram() {
        byte[] signedMessage =
                TelegramEncoder.signMessage(
                        createTestEdgeCallTelegram(),
                        ChainId.TEST_NET.getId(),
                        SampleKeys.CREDENTIALS);

        String hexMessage = Numeric.toHexString(signedMessage);
        System.out.println(hexMessage);
        String SIGN_RESULT_ETH_EXAMPLE = "0xf9054a14808094000000000000000000000000000000000000300180b904ec7b22706565724964223a2231365569753248416d31347841736e4a4844716e514e513251716f3153617064526b396a386d424b59366d67685644503942397535222c22656e64706f696e74223a222f617069222c22496e707574223a7b226d6574686f64223a2022504f5354222c2268656164657273223a5b5d2c2270617468223a222f73646170692f76312f74787432696d67222c22626f6479223a7b0a20202020202022656e61626c655f6872223a2066616c73652c0a2020202020202264656e6f6973696e675f737472656e677468223a20302c0a20202020202022666972737470686173655f7769647468223a20302c0a20202020202022666972737470686173655f686569676874223a20302c0a2020202020202268725f7363616c65223a20322c0a2020202020202268725f75707363616c6572223a2022222c0a2020202020202268725f7365636f6e645f706173735f7374657073223a20302c0a2020202020202268725f726573697a655f78223a20302c0a2020202020202268725f726573697a655f79223a20302c0a2020202020202270726f6d7074223a202277686974652063617420616e6420646f67222c0a202020202020227374796c6573223a205b0a202020202020202022220a2020202020205d2c0a2020202020202273656564223a202d312c0a2020202020202273756273656564223a202d312c0a20202020202022737562736565645f737472656e677468223a20302c0a20202020202022736565645f726573697a655f66726f6d5f68223a202d312c0a20202020202022736565645f726573697a655f66726f6d5f77223a202d312c0a2020202020202273616d706c65725f6e616d65223a2022222c0a2020202020202262617463685f73697a65223a20312c0a202020202020226e5f69746572223a20312c0a202020202020227374657073223a2035302c0a202020202020226366675f7363616c65223a20372c0a202020202020227769647468223a203531322c0a20202020202022686569676874223a203531322c0a20202020202022726573746f72655f6661636573223a2066616c73652c0a2020202020202274696c696e67223a2066616c73652c0a20202020202022646f5f6e6f745f736176655f73616d706c6573223a2066616c73652c0a20202020202022646f5f6e6f745f736176655f67726964223a2066616c73652c0a202020202020226e656761746976655f70726f6d7074223a2022222c0a20202020202022657461223a20302c0a20202020202022735f636875726e223a20302c0a20202020202022735f746d6178223a20302c0a20202020202022735f746d696e223a20302c0a20202020202022735f6e6f697365223a20312c0a202020202020226f766572726964655f73657474696e6773223a207b7d2c0a202020202020226f766572726964655f73657474696e67735f726573746f72655f61667465727761726473223a20747275652c0a202020202020227363726970745f61726773223a205b5d2c0a2020202020202273616d706c65725f696e646578223a202245756c6572222c0a202020202020227363726970745f6e616d65223a2022222c0a2020202020202273656e645f696d61676573223a20747275652c0a20202020202022736176655f696d61676573223a2066616c73652c0a20202020202022616c776179736f6e5f73637269707473223a207b7d0a202020207d7d7d28a04d738460dceedcd65074366c35c37e12fbe4e85f4b662fb306a61633cb1623d19f0cfc68b55ae48009ef981a6b2668cdc316466a7a82e181e4a3527a6b2036bd";
        assertEquals(SIGN_RESULT_ETH_EXAMPLE, hexMessage);
    }


    static RawTelegram createTestEdgeCallTelegram() {
        return RawTelegram.createTransaction(
                BigInteger.valueOf(20),
                BigInteger.valueOf(0),
                BigInteger.valueOf(0),
                PrecompileAddress.EDGE_CALL.getAddress(),
                BigInteger.valueOf(0),
                "{\"peerId\":\"16Uiu2HAm14xAsnJHDqnQNQ2Qqo1SapdRk9j8mBKY6mghVDP9B9u5\",\"endpoint\":\"/api\",\"Input\":{\"method\": \"POST\",\"headers\":[],\"path\":\"/sdapi/v1/txt2img\",\"body\":{\n" +
                        "      \"enable_hr\": false,\n" +
                        "      \"denoising_strength\": 0,\n" +
                        "      \"firstphase_width\": 0,\n" +
                        "      \"firstphase_height\": 0,\n" +
                        "      \"hr_scale\": 2,\n" +
                        "      \"hr_upscaler\": \"\",\n" +
                        "      \"hr_second_pass_steps\": 0,\n" +
                        "      \"hr_resize_x\": 0,\n" +
                        "      \"hr_resize_y\": 0,\n" +
                        "      \"prompt\": \"white cat and dog\",\n" +
                        "      \"styles\": [\n" +
                        "        \"\"\n" +
                        "      ],\n" +
                        "      \"seed\": -1,\n" +
                        "      \"subseed\": -1,\n" +
                        "      \"subseed_strength\": 0,\n" +
                        "      \"seed_resize_from_h\": -1,\n" +
                        "      \"seed_resize_from_w\": -1,\n" +
                        "      \"sampler_name\": \"\",\n" +
                        "      \"batch_size\": 1,\n" +
                        "      \"n_iter\": 1,\n" +
                        "      \"steps\": 50,\n" +
                        "      \"cfg_scale\": 7,\n" +
                        "      \"width\": 512,\n" +
                        "      \"height\": 512,\n" +
                        "      \"restore_faces\": false,\n" +
                        "      \"tiling\": false,\n" +
                        "      \"do_not_save_samples\": false,\n" +
                        "      \"do_not_save_grid\": false,\n" +
                        "      \"negative_prompt\": \"\",\n" +
                        "      \"eta\": 0,\n" +
                        "      \"s_churn\": 0,\n" +
                        "      \"s_tmax\": 0,\n" +
                        "      \"s_tmin\": 0,\n" +
                        "      \"s_noise\": 1,\n" +
                        "      \"override_settings\": {},\n" +
                        "      \"override_settings_restore_afterwards\": true,\n" +
                        "      \"script_args\": [],\n" +
                        "      \"sampler_index\": \"Euler\",\n" +
                        "      \"script_name\": \"\",\n" +
                        "      \"send_images\": true,\n" +
                        "      \"save_images\": false,\n" +
                        "      \"alwayson_scripts\": {}\n" +
                        "    }}}");
    }

}
