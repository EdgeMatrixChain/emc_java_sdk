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

import org.web3j.rlp.RlpType;
import pro.edgematrix.crypto.type.IRtcMsg;
import pro.edgematrix.crypto.type.RtcMsg;
import pro.edgematrix.crypto.type.RtcMsgType;

import java.util.List;

/**
 * RawRtcMsg class used for signing RawRtcMsg locally.<br>
 * For the specification, refer to <a href="http://www.edgematrix.pro/api/paper.pdf">yellow
 * paper</a>.
 */
public class RawRtcMsg {

    private final IRtcMsg transaction;

    protected RawRtcMsg(final IRtcMsg transaction) {
        this.transaction = transaction;
    }


    public static RawRtcMsg createRtcMsg(
            String subject, String application, String content, String to) {
        return new RawRtcMsg(
                RtcMsg.createContractTransaction(
                        subject, application, content, to));
    }

    public List<RlpType> asRlpValues(Sign.SignatureData signatureData) {
        return transaction.asRlpValues(signatureData);
    }

    public String getSubject() {
        return transaction.getSubject();
    }

    public String getApplication() {
        return transaction.getApplication();
    }

    public String getContent() {
        return transaction.getContent();
    }

    public String getTo() {
        return transaction.getTo();
    }

    public RtcMsgType getType() {
        return transaction.getType();
    }

    public IRtcMsg getTransaction() {
        return transaction;
    }
}
