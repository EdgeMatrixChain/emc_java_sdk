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
package pro.edgematrix.crypto.type;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;
import pro.edgematrix.crypto.Sign;

import java.util.ArrayList;
import java.util.List;

/**
 * RtcMsg class used for signing RtcMsg locally.<br>
 * For the specification, refer to <a href="http://www.edgematrix.pro/api/paper.pdf">yellow
 * paper</a>.
 */
public class RtcMsg implements IRtcMsg {

    private RtcMsgType type;

    String subject;
    String application;
    String content;
    String to;

    public RtcMsg(String subject, String application, String content, String to) {
        this.subject = subject;
        this.application = application;
        this.content = content;
        this.to = to;
        this.type = RtcMsgType.SubscribeMsg;
    }

    @Override
    public List<RlpType> asRlpValues(Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(getSubject()));

        result.add(RlpString.create(getApplication()));
        result.add(RlpString.create(getContent()));

        // an empty to address should not be encoded as a numeric 0 value
        String to = getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        if (signatureData != null) {
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getV())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS())));
        }

        return result;
    }

    public static RtcMsg createContractTransaction(
            String subject, String application, String content, String to) {

        return new RtcMsg(subject, application, content, to);
    }

    @Override
    public RtcMsgType getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getApplication() {
        return application;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String getTo() {
        return to;
    }
}
