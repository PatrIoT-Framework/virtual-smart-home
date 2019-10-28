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

import io.patriot_framework.generator.device.impl.basicActuators.RotaryActuator;
import io.patriot_framework.generator.device.passive.actuators.StateMachine;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public class DoorActuator extends RotaryActuator {
    public DoorActuator(String label) {
        super(label, 1000);
        StateMachine machine = new StateMachine();
        machine.addState("Closed")
                .addState("Opening", 1000)
                .addState("Open")
                .addState("Closing", 1000)
                .build();
        this.setStateMachine(machine);
    }

    public void openDoor() {
        String state = getStateMachine().status();
        if (state.equals("Open") || state.contains("Opening")) {
            return;
        }
        this.controlSignal();
    }

    public void closeDoor() {
        String state = getStateMachine().status();
        if (state.equals("Closed") || state.contains("Closing")) {
            return;
        }
        this.controlSignal();
    }

    public String getStatus() {
        return getStateMachine().status();
    }
}
