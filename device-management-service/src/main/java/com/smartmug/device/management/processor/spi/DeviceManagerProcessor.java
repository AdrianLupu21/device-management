package com.smartmug.device.management.processor.spi;

import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceGroupProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;

public interface DeviceManagerProcessor {

    void registerDevice(final DeviceConnectionProperties deviceConnectionProperties);

    void registerGroup(final DeviceGroupProperties deviceGroupProperties);

    DeviceGroupProperties updateGroup(final DeviceGroupProperties deviceGroupProperties);

    DeviceConnectionProperties fetchDeviceConnectionProperties(final String deviceId);

    DeviceGroupProperties fetchDeviceGroupProperties(final String groupName);

    DeviceResourceProperties fetchResource(final String deviceId, final String resourceType);
}

