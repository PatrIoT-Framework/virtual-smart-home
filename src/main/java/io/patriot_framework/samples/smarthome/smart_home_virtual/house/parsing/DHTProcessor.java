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

package io.patriot_framework.samples.smarthome.smart_home_virtual.house.parsing;

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.DHT11Device;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHTProcessor implements Processor {
    static Logger log = LoggerFactory.getLogger(DHTProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Processing event {}", exchange);
        DHT11Device dh = exchange.getIn().getBody(DHT11Device.class);
        String message = dh.buildMessage();
        log.debug("exchange = " + exchange + message);
        exchange.getOut().setBody(message);
    }
}
