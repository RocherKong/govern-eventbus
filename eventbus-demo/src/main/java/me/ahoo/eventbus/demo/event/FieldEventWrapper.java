/*
 * Copyright [2021-2021] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.eventbus.demo.event;

import me.ahoo.eventbus.core.annotation.Event;

/**
 * @author ahoo wang
 * createTime 2020/3/30 21:35
 */
public class FieldEventWrapper {
    private String resp;
    @Event
    private FieldEventData fieldEventData;

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public FieldEventData getFieldEventData() {
        return fieldEventData;
    }

    public void setFieldEventData(FieldEventData fieldEventData) {
        this.fieldEventData = fieldEventData;
    }
}
