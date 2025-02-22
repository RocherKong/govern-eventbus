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

package me.ahoo.eventbus.core.publisher.impl;

import me.ahoo.eventbus.core.EventBusException;
import me.ahoo.eventbus.core.publisher.EventDataGetter;

import java.lang.reflect.Field;

/**
 * @author ahoo wang
 */
public class FieldEventDataGetter implements EventDataGetter {
    private final Field eventDataField;

    public FieldEventDataGetter(Field eventDataField) {
        this.eventDataField = eventDataField;
        eventDataField.setAccessible(true);
    }

    @Override
    public Object getEventData(Object targetObject) {
        try {
            return eventDataField.get(targetObject);
        } catch (IllegalAccessException e) {
            throw new EventBusException(e.getMessage(), e);
        }
    }
}
