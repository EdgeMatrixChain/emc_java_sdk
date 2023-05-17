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
package pro.edgematrix;

/**
 * rtc message
 */
public class RtcMsg {

    // RTC channel hash.
    private String subject;

    // application name the rtc channel belongs to
    private String application;

    // rtc text content.
    private String content;

    // address of rtc message to.
    // 0x0 is broadcast address
    private String to;

    public static RtcMsg createRtcMsg(
            String subject, String application, String content, String to) {
        RtcMsg rtcMsg = new RtcMsg();
        rtcMsg.application = application;
        rtcMsg.subject = subject;
        rtcMsg.to = to;
        rtcMsg.content = content;
        return rtcMsg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
