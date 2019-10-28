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

import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.dataFeed.NormalDistributionDataFeed;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public class Thermometer extends Sensor implements SimpleValueSensor<Float> {
    public static final String DEFAULT_UNIT = "°C";

    private String unit;
    private DataFeed dataFeed = new NormalDistributionDataFeed(25, 1);
    private io.patriot_framework.generator.device.Device device = new io.patriot_framework.generator.device.impl.basicDevices.Thermometer(getLabel(), dataFeed);

    public Thermometer(String label) {
        this(label, DEFAULT_UNIT);
    }

    public Thermometer(String label, String unit) {
        super(label);
        this.unit = unit;
    }

    @Override
    public Float getValue() {
        return device.requestData().get(0).get(Double.class).floatValue();
    }

    @Override
    public String getUnit() {
        return this.unit;
    }
}
