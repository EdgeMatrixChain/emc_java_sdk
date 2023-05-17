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
package pro.edgematrix.protocol.methods.response;

/** EdgeCallResponse. */
public class EdgeCallResult {
    private String telegram_hash;
    private String response;

    public String getTelegram_hash() {
        return telegram_hash;
    }

    public void setTelegram_hash(String telegram_hash) {
        this.telegram_hash = telegram_hash;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
