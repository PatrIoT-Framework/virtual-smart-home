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
package io.patriot_framework.samples.smarthome.smart_home_virtual.routes;

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.DHT11Device;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.HouseBean;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.parsing.DHTProcessor;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class SensorRouteBuilder extends IntelligentHomeRouteBuilder {

    @Override
    public void configure() throws Exception {
        HouseBean houseBean = HouseBean.getInstance();
        DHTProcessor dhtProcessor = new DHTProcessor();
        from("timer:sensorBroadcast?period=5000")
                .log("Fire")
                .setProperty("type", simple(DHT11Device.class.getSimpleName()))
                .bean(houseBean, "objInfo")
                .process(dhtProcessor)
                .to("websocket:weather?sendToAll=true");


        from(restBaseUri() + "/all?httpMethodRestrict=GET")
                .setProperty("type", simple(Object.class.getSimpleName()))
                .bean(houseBean, "objInfo")
                .setHeader("content-type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson, true);


    }
}
