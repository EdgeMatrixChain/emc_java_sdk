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
package pro.edgematrix;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Batcher;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.rx.Web3jRx;
import pro.edgematrix.protocol.core.JsonRpc2_0Web3j;
import pro.edgematrix.protocol.methods.response.EdgeSendRtcMsg;
import pro.edgematrix.protocol.methods.response.EdgeSendTelegram;

/**
 * JSON-RPC Request object building factory.
 */
public interface EdgeWeb3j extends Ethereum, Web3jRx, Batcher {

    Request<?, EthGetTransactionReceipt> edgeGetTelegramReceipt(String telegramHash);

    Request<?, EthGetTransactionCount> edgeGetTelegramCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, EdgeSendTelegram> edgeSendRawTelegram(String var1);

    Request<?, EdgeSendRtcMsg> edgeSendRawMsg(String var1);

    /**
     * Construct a new Web3j instance.
     *
     * @param web3jService web3j service instance - i.e. HTTP or IPC
     * @return new Web3j instance
     */
    static EdgeWeb3j build(Web3jService web3jService) {
        return new JsonRpc2_0Web3j(web3jService);
    }

    /**
     * Shutdowns a Web3j instance and closes opened resources.
     */
    void shutdown();
}
