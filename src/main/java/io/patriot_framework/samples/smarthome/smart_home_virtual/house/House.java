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

import io.patriot_framework.samples.smarthome.smart_home_virtual.house.parsing.HouseComposer;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.parsing.ParserException;
import io.patriot_framework.samples.smarthome.smart_home_virtual.house.parsing.YamlHouseParser;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public final class House {

    private static Map<String, House> houses = new ConcurrentHashMap<>();
    private String name;
    private Map<String, Device> devices;

    private House(String name, Map<String, Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public static House getHouseInstanceFromURL(URL path) throws IOException, ParserException {
        if (!houses.containsKey(path.toString())) {
            houses.put(path.toString(), new HouseComposer(new YamlHouseParser(path)).getHouse());
        }

        return houses.get(path.toString());
    }

    public Device getDeviceWithId(String id) {
        return devices.get(id);
    }

    @Nullable
    public <T> T getDeviceWithId(String id, Class<T> type) {
        Device dev = getDeviceWithId(id);
        if (type.isInstance(dev)) {
            return type.cast(dev);
        }

        return null;  // or throw exception?
    }

    public <T> List<T> getAllDevices(Class<T> type) {
        ArrayList<T> toReturn = new ArrayList<>();

        for (Device d : devices.values()) {
            if (type.isInstance(d)) {
                toReturn.add(type.cast(d));
            }
        }
        return toReturn;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String houseName = "";
        private Map<String, Device> devices = new ConcurrentHashMap<>();

        public Builder withDevices(List<Device> devices) {
            for (Device d : devices) {
                this.devices.put(d.getLabel(), d);
            }
            return this;
        }

        public Builder withDevice(Device device) {
            this.devices.put(device.getLabel(), device);
            return this;
        }

        public Builder withDevice(String name, String type, Object... args) throws IllegalArgumentException {
            this.devices.put(name, Device.createDevice(type, name, args));
            return this;
        }

        public Builder withHouseName(String houseName) {
            this.houseName = houseName;
            return this;
        }

        public House createHouse() {
            return new House(houseName, devices);
        }
    }


}
