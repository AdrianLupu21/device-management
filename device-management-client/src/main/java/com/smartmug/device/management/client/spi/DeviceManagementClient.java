package com.smartmug.device.management.client.spi;

import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;

import java.net.URISyntaxException;

public interface DeviceManagementClient {

    DeviceResourceProperties getDeviceResourceProperties(String deviceId, String resourceType) throws URISyntaxException ;

    DeviceConnectionProperties getDeviceConnectionProperties(String deviceId) throws URISyntaxException;
}
