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

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.Ac;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.HouseBean;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class AcRouteBuilder extends IntelligentHomeRouteBuilder {
    @Override
    public void configure() throws Exception {
        HouseBean houseBean = HouseBean.getInstance();

        from(restBaseUri() + "/ac/on?httpMethodRestrict=GET")
                .to("direct:ac-on")
                .to("direct:ac");

        from(restBaseUri() + "/ac/off?httpMethodRestrict=GET")
                .to("direct:ac-off")
                .to("direct:ac");

        from(restBaseUri() + "/ac?httpMethodRestrict=GET")
                .to("direct:ac");

        from("direct:ac")
                .setProperty("type", simple(Ac.class.getSimpleName()))
                .bean(houseBean, "objInfo")
                .marshal().json(JsonLibrary.Jackson);


        from("direct:ac-on")
                .bean(houseBean, "acOn");

        from("direct:ac-off")
                .bean(houseBean, "acOff");
    }
}