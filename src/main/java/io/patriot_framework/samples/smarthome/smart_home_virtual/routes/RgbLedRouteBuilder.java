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

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.HouseBean;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.RGBLight;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class RgbLedRouteBuilder extends IntelligentHomeRouteBuilder {
    private void configureRESTRoutes() throws Exception {
        from(restBaseUri() + "/led/batch?httpMethodRestrict=POST")
                .to("direct:led-set-batch");

        from(restBaseUri() + "/led/setrgb?httpMethodRestrict=GET")
                .setBody(simple("${header.led};r;${header.r}\n"
                        + "${header.led};g;${header.g}\n"
                        + "${header.led};b;${header.b}\n"
                ))
                .to("direct:led-set-batch");

        final String allLed = Integer.toString(Integer.MAX_VALUE);
        from(restBaseUri() + "/led/setrgb/all?httpMethodRestrict=GET")
                .setBody(simple(allLed + ";r;${header.r}\n"
                        + allLed + ";g;${header.g}\n"
                        + allLed + ";b;${header.b}\n"
                ))
                .to("direct:led-set-batch");
    }

    @Override
    public void configure() throws Exception {
        HouseBean houseBean = HouseBean.getInstance();
        // direct routes
        from("direct:led-set-batch")
                .bean(houseBean, "configureRGBLight");

        // REST API routes
        configureRESTRoutes();

        from(restBaseUri() + "/led?httpMethodRestrict=GET")
                .to("direct:led");

        from("direct:led")
                .setProperty("type", simple(RGBLight.class.getSimpleName()))
                .bean(houseBean, "objInfo")
                .setHeader("content-type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }
}
