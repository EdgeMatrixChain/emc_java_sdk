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


public enum RtcMsgType {
    SubjectMsg(((byte) 0x0)),
    StateMsg(((byte) 0x01)),
    SubscribeMsg(((byte) 0x02));

    Byte type;

    RtcMsgType(final Byte type) {
        this.type = type;
    }

    public Byte getRlpType() {
        return type;
    }

    public boolean isSubject() {
        return this.equals(RtcMsgType.SubjectMsg);
    }

    public boolean isState() {
        return this.equals(RtcMsgType.StateMsg);
    }

    public boolean isSubscribe() {
        return this.equals(RtcMsgType.SubscribeMsg);
    }
}
