/*
 * Copyright 2019 Web3 Labs Ltd.
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

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class EdgeServiceTest {

    /**
     *  "http://3.145.214.36:40012/"  is one of testnet node's rpc url, may be unavailable
     */
    private static EdgeWeb3j web3j = EdgeWeb3j.build(new HttpService("http://3.145.214.36:40012/"));

    @BeforeAll
    public static void prepare() {

    }

    @AfterAll
    public static void finish() {
        web3j.shutdown();
    }

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

    @Test
    public void testCreateRtcSubject() throws ExecutionException, InterruptedException {
        EdgeService edgeService = new EdgeService();
        String s = edgeService.createRtcSubject(
                web3j,
                ChainId.TEST_NET.getId(),
                edgeService.getNextTelegramNonce(web3j, SampleKeys.ADDRESS),
                SampleKeys.CREDENTIALS);
        System.out.println(s);
    }

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
            System.out.println(String.format("nextNonce: %s", telegramCount));
        }
    }

    @Test
    public void testGetTelegramReceipt() throws IOException {
        EdgeService edgeService = new EdgeService();
        TransactionReceipt receipt = edgeService.getTelegramReceipt(web3j, "0x6b7c880d58fef940e7b7932b9239d2737b4a71583c4640757e234de94bb98c0b");
        if (receipt != null && receipt.getBlockHash() != null && receipt.getStatus() != null && receipt.getTo() != null) {
            System.out.println(String.format("blockHash: %s, status: %s, to: %s", receipt.getBlockHash(), receipt.getStatus(), receipt.getTo()));
        }
    }

}
