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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import pro.edgematrix.EdgeService;
import pro.edgematrix.EdgeWeb3j;
import pro.edgematrix.RtcMsg;
import pro.edgematrix.common.ChainId;
import pro.edgematrix.common.PrecompileAddress;
import pro.edgematrix.protocol.methods.response.EdgeCallResult;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class EdgeServiceTest {

    /**
     * "oregon.edgematrix.xyz"  is one of testnet node's rpcUrl
     */
    private static EdgeWeb3j web3j = null;
    static {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
         web3j = EdgeWeb3j.build(new HttpService("https://oregon.edgematrix.xyz",httpClient));
    }

    // A edge-matrix node that is running Stable Diffusion service for testing purpose.
    private final static String TEST_PEERID = "16Uiu2HAmHsjCvAAVmWUUKt6WA85gHUEnepa6xzNqvAeC8gA4AWUC";


    @BeforeAll
    public static void prepare() {
    }

    @AfterAll
    public static void finish() {
        web3j.shutdown();
    }

    /**
     * Test for send a telegram to a precompile contract with data
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSendTelegram() throws ExecutionException, InterruptedException {
        EdgeService edgeService = new EdgeService();
        String s = edgeService.sendTelegram(
                web3j,
                ChainId.TEST_NET.getId(),
                edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS),
                PrecompileAddress.EDGE_RTC_SUBJECT.getAddress(),
                SampleKeys.CREDENTIALS,
                "");
        System.out.println(s);
    }

    /**
     * Test for create a rtc chat channel
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCreateRtcSubject() throws ExecutionException, InterruptedException {
        EdgeService edgeService = new EdgeService();
        String s = edgeService.createRtcSubject(
                web3j,
                ChainId.TEST_NET.getId(),
                edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS),
                SampleKeys.CREDENTIALS);
        // returen a rtc subject hash as rtc channel hash
        System.out.println(s);
    }

    /**
     * Test for a Stable Diffusion txt2img api call
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCallEdgeApi() throws ExecutionException, InterruptedException, JsonProcessingException {
        EdgeService edgeService = new EdgeService();
        String peerId = TEST_PEERID;
        String apiHttpMethod = "POST";
        String apiPath = "/sdapi/v1/txt2img";
        String apiData = "{\n" +
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
                "    }";
        String s = edgeService.callEdgeApi(
                web3j,
                ChainId.TEST_NET.getId(),
                edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS),
                SampleKeys.CREDENTIALS,
                peerId,
                apiHttpMethod,
                apiPath,
                "{\"prompt\":\"(Raw photo,realistic,photorealistic),dynamic angle,gorgeous,amazing,epic,ultra-detailed,1girl\\\\(mecha\\\\),(techpunkmask:1.2),slim body,kong-fu,black hair,black eyes,Chinese hairstyle,see-through,glowing body,cyberpunk,cyborg ninja,cinematic Lighting,Accent Lighting,dramatic_shadow,ray_tracing,complex clothes and patterns, fighting,(holding laser-katana:1.3),hair-bangs,red long scarf,highly real skin,outdoors, strong dynamic pose, neon lights, beautiful glowing,\",\"negative_prompt\":\"(nsfw,nude,loli,children,childish,child, EasyNegative)\",\"sampler\":\"Euler a\",\"steps\":40,\"width\":256,\"height\":256,\"cfg_scale\":7,\"seed\":1405709391,\"clip_skip\":2}");
        if (isValidJSON(s)) {
            ObjectMapper objectMapper = new ObjectMapper();
            EdgeCallResult edgeCallResult = objectMapper.readValue(s, EdgeCallResult.class);
            System.out.println("telegram_hash: " + edgeCallResult.getTelegram_hash());
            System.out.println("response: " + new String(Base64.getDecoder().decode(edgeCallResult.getResponse())));
        } else {
            System.out.println("invalid json");
        }
    }

    public boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            final JsonParser parser = new ObjectMapper().createParser(json);
            while (parser.nextToken() != null) {
            }
            valid = true;
        } catch (JsonParseException jpe) {
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return valid;
    }

    /**
     * send a broadcast rtc msg to a rtc channel
     */
    @Test
    public void testSendRtcMsg() {
        EdgeService edgeService = new EdgeService();
        String s = edgeService.sendRtcMsg(
                web3j,
                ChainId.TEST_NET.getId(),
                SampleKeys.CREDENTIALS,
                RtcMsg.createRtcMsg(
                        "0x8eeb338239ada22d81ffb7adc995fe31a4d1dc2d701bc8a58fffe5b53e14281e",
                        "edge_chat",
                        "hello",
                        new Address("0x0").toString()));
        System.out.println(s);
    }

    @Test
    public void testGetNextTelegramNonce() throws ExecutionException, InterruptedException {
        EdgeService edgeService = new EdgeService();
        BigInteger telegramCount = edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS);
        if (telegramCount != null) {
            System.out.printf("nextNonce: %s%n", telegramCount);
        }
    }

    @Test
    public void testGetTelegramReceipt() throws IOException {
        EdgeService edgeService = new EdgeService();
        TransactionReceipt receipt = edgeService.getTelegramReceipt(web3j, "0x6b7c880d58fef940e7b7932b9239d2737b4a71583c4640757e234de94bb98c0b");
        if (receipt != null && receipt.getBlockHash() != null && receipt.getStatus() != null && receipt.getTo() != null) {
            System.out.printf("blockHash: %s, status: %s, to: %s%n", receipt.getBlockHash(), receipt.getStatus(), receipt.getTo());
        }
    }

}
