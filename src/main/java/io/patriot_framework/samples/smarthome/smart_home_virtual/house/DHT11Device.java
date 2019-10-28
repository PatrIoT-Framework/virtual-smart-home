/*
 * Copyright 2019 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.samples.smarthome.smart_home_virtual.house;


import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.dataFeed.NormalDistributionDataFeed;
import io.patriot_framework.generator.device.impl.basicDevices.DHT11;

import java.util.List;
import java.util.stream.Collectors;

public class DHT11Device extends Device {
    private DHT11 device;


    public DHT11Device(String label) {
        super(label);
        DataFeed hygroDF = new NormalDistributionDataFeed(50, 30);
        DataFeed tempDF = new NormalDistributionDataFeed(25, 3);
        device = new DHT11(tempDF, hygroDF);
    }

    public Float getTemperature() {
        return getValue(0);
    }

    public Float getHumidity() {
        return getValue(1);
    }

    private Float getValue(int index) {
        List<Data> data = device.requestData(null);
        return data.get(0).get(Double.class).floatValue();
    }

    public String buildMessage() {
        List<Double> values = device.requestData(null)
                .stream().map(it -> it.get(Double.class)).collect(Collectors.toList());
        return "{\"temperature\" : " + values.get(0).intValue()
                + ", \"humidity\" : " + values.get(1).intValue() +
                ", \"label\" : \"" + getLabel() + "\"}";
    }
}
