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
package pro.edgematrix.common;

public enum PrecompileAddress {
    EDGE_RTC_SUBJECT("0x0000000000000000000000000000000000003101"),
    EDGE_CALL("0x0000000000000000000000000000000000003001");

    private String address;

    PrecompileAddress(String addr) {
        this.address = addr;
    }

    public String getAddress() {
        return address;
    }
}
