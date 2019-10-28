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

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.Door;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.HouseBean;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class ServoRouteBuilder extends IntelligentHomeRouteBuilder {
    @Override
    public void configure() throws Exception {
        HouseBean houseBean = HouseBean.getInstance();

        from("direct:door-open").bean(houseBean, "doorOpen");
        from("direct:door-close").bean(houseBean, "doorClose");

        from("direct:window-open").bean(houseBean, "windowOpen");
        from("direct:window-close").bean(houseBean, "windowClose");

        from(restBaseUri() + "/door/open")
                .to("direct:door-open")
                .to("direct:door");

        from(restBaseUri() + "/door/close")
                .to("direct:door-close")
                .to("direct:door");

        from(restBaseUri() + "/door?httpMethodRestrict=GET")
                .to("direct:door");

        from("direct:door")
                .setProperty("type", simple(Door.class.getSimpleName()))
                .setProperty("flag", simple("door"))
                .bean(houseBean, "objInfo")
                .marshal().json(JsonLibrary.Jackson);

        from(restBaseUri() + "/window/open")
                .to("direct:window-open")
                .to("direct:window");

        from(restBaseUri() + "/window/close")
                .to("direct:window-close")
                .to("direct:window");

        from(restBaseUri() + "/window?httpMethodRestrict=GET")
                .to("direct:window");

        from("direct:window")
                .setProperty("type", simple(Door.class.getSimpleName()))
                .setProperty("flag", simple("window"))
                .bean(houseBean, "objInfo")
                .marshal().json(JsonLibrary.Jackson);
    }
}