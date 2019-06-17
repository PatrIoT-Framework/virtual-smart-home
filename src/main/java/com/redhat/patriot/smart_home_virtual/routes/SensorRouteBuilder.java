/*
 * Copyright 2018 Patriot project
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
package com.redhat.patriot.smart_home_virtual.routes;

import com.redhat.patriot.smart_home_virtual.house.HouseBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class SensorRouteBuilder extends IntelligentHomeRouteBuilder {

    @Override
    public void configure() throws Exception {
        //TODO: implement using data generators
//      final SensorDataProcessor sensorDataProcessor = new SensorDataProcessor();
//
//      from(restBaseUri() + "/sensorData?httpMethodRestrict=GET")
//            .setHeader("address", simple(CONFIG.getSensorAddress()))
//            .setBody(simple(""))
//            .to("bulldog:i2c?readLength=2")
//            .process(sensorDataProcessor)
//            .marshal().json(JsonLibrary.Jackson, true);
//
      from("timer:sensorBroadcast?period=5000")
              .setBody(simple("\"temperature\" : 10, \"humidity\" : 100, "))
              .log("firing")
            .to("websocket:weather?sendToAll=true");

        HouseBean houseBean = HouseBean.getInstance();

        from(restBaseUri() + "/all?httpMethodRestrict=GET")
                .setProperty("type", simple(Object.class.getSimpleName()))
                .bean(houseBean, "objInfo")
                .marshal().json(JsonLibrary.Jackson, true);


    }
}
