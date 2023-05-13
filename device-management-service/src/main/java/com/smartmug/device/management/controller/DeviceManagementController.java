package com.smartmug.device.management.controller;

import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceGroupProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;
import com.smartmug.device.management.processor.DeviceManagementProcessorImpl;
import com.smartmug.device.management.resource.DeviceManagementResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/management/v1")
public class DeviceManagementController implements DeviceManagementResource {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManagementController.class);

    @Autowired
    private DeviceManagementProcessorImpl deviceManagementProcessorImpl;

    @Override
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DeviceConnectionProperties> getDeviceConnectionProperties(String deviceId) {
        logger.info("running method 'getDeviceConnectionProperties'");
        final DeviceConnectionProperties deviceConnectionProperties =
                deviceManagementProcessorImpl.fetchDeviceConnectionProperties(deviceId);
        return ResponseEntity.ok(deviceConnectionProperties);
    }

    @Override
    public ResponseEntity<DeviceResourceProperties> getDeviceResourceProperties(String deviceId, String resourceType) {
        logger.info("running method 'getDeviceResourceProperties'");
        final DeviceResourceProperties deviceResourceProperties =
                deviceManagementProcessorImpl.fetchResource(deviceId, resourceType);
        return ResponseEntity.ok(deviceResourceProperties);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response registerDevice(DeviceConnectionProperties deviceConnectionProperties) {
        logger.info("running method 'registerDevice'");
        deviceManagementProcessorImpl.registerDevice(deviceConnectionProperties);
        return Response.ok().build();
    }

    @Override
    public Response mapResourceToDevice(DeviceResourceProperties deviceResourceProperties) {
        logger.info("running method 'mapResourceToDevice'");
        deviceManagementProcessorImpl.addResourceToDevice(deviceResourceProperties);
        return Response.ok().build();
    }

    @Override
    public ResponseEntity<DeviceResourceProperties> updateDeviceResource(DeviceResourceProperties deviceResourceProperties) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Response registerGroup(final DeviceGroupProperties deviceGroupProperties) {
        logger.info("running method 'registerGroup'");
        deviceManagementProcessorImpl.registerGroup(deviceGroupProperties);
        return Response.ok().build();
    }

    @Override
    public ResponseEntity<DeviceGroupProperties> updateGroup(DeviceGroupProperties deviceGroupProperties) {
        logger.info("running method 'updateGroup'");
        deviceManagementProcessorImpl.updateGroup(deviceGroupProperties);
        return ResponseEntity.ok(deviceGroupProperties);
    }
}
