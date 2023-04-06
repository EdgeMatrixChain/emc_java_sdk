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

import org.web3j.rlp.RlpType;
import pro.edgematrix.crypto.Sign;

import java.util.List;

public interface IRtcMsg {

    List<RlpType> asRlpValues(Sign.SignatureData signatureData);

     String getSubject();

    String getApplication();

    String getContent();

    String getTo();

    RtcMsgType getType();
}
