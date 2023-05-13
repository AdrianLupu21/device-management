package com.smartmug.device.management.processor;

import com.smartmug.device.management.dao.DeviceDAO;
import com.smartmug.device.management.dao.DeviceGroupDAO;
import com.smartmug.device.management.dao.ResourceDAO;
import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceGroupProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;
import com.smartmug.device.management.entity.Device;
import com.smartmug.device.management.entity.DeviceGroup;
import com.smartmug.device.management.entity.Resources;
import com.smartmug.device.management.error.DeviceManagementError;
import com.smartmug.device.management.error.DeviceManagementErrorCode;
import com.smartmug.device.management.processor.spi.DeviceManagerProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class DeviceManagementProcessorImpl implements DeviceManagerProcessor {

    @Autowired
    private DeviceGroupDAO deviceGroupDAO;
    @Autowired
    private DeviceDAO deviceDAO;
    @Autowired
    private ResourceDAO resourceDAO;

    @Override
    public void registerDevice(final DeviceConnectionProperties deviceConnectionProperties){
        if(!isGroupRegistered(deviceConnectionProperties.getDeviceGroupProperties().getGroupName())){
            throw DeviceManagementError.newError(DeviceManagementErrorCode.NO_GROUP_ASSOCIATED_TO_DEVICE,
                    deviceConnectionProperties.getDeviceGroupProperties().getGroupName(),
                    deviceConnectionProperties.getDeviceId());
        }
        final Device device = transformDeviceConnectionPropertiesToDevice(deviceConnectionProperties);
        deviceDAO.registerDevice(device);
    }

    @Override
    public void registerGroup(final DeviceGroupProperties deviceGroupProperties){
        if(isGroupRegistered(deviceGroupProperties.getGroupName())){
            throw DeviceManagementError.newError(DeviceManagementErrorCode.DUPLICATE_GROUP,
                    deviceGroupProperties.getGroupName());
        }
        final DeviceGroup deviceGroup = transformDeviceGroupPropertiesToDeviceGroup(deviceGroupProperties);
        deviceGroupDAO.saveGroup(deviceGroup);
    }

    @Override
    public DeviceGroupProperties updateGroup(final DeviceGroupProperties deviceGroupProperties) {
        if(!isGroupRegistered(deviceGroupProperties.getGroupName())){
            throw DeviceManagementError.newError(DeviceManagementErrorCode.NO_GROUP_FOUND,
                    deviceGroupProperties.getGroupName());
        }
        final DeviceGroup deviceGroup = transformDeviceGroupPropertiesToDeviceGroup(deviceGroupProperties);
        deviceGroupDAO.saveGroup(deviceGroup);
        return deviceGroupProperties;
    }

    @Override
    public DeviceConnectionProperties fetchDeviceConnectionProperties(String deviceId) {
        final Device device = deviceDAO.getDeviceById(deviceId);
        if(null == device){
            throw DeviceManagementError.newError(DeviceManagementErrorCode.NO_DEVICE_FOUND,
                    deviceId);
        }
        final DeviceGroupProperties deviceGroupProperties =
                transformDeviceGroupToDeviceGroupProperties(device.getGroup());
        final DeviceConnectionProperties deviceConnectionProperties = new DeviceConnectionProperties();
        deviceConnectionProperties.setDeviceId(deviceId);
        deviceConnectionProperties.setDeviceType(device.getDeviceType());
        deviceConnectionProperties.setPartitionId(device.getPartitionId());
        deviceConnectionProperties.setDeviceGroupProperties(deviceGroupProperties);
        return deviceConnectionProperties;
    }

    @Override
    public DeviceGroupProperties fetchDeviceGroupProperties(String groupName) {
        return null;
    }

    @Override
    public DeviceResourceProperties fetchResource(String deviceId, String resourceType) {
        final Device device = deviceDAO.getDeviceById(deviceId);
        if(null == device){
            throw DeviceManagementError.newError(DeviceManagementErrorCode.NO_DEVICE_FOUND,
                    deviceId);
        }
        final Resources resources = resourceDAO.getResourceByDeviceIdAndResourceType(device, resourceType);
        return transformResourcesToDeviceResourceProperties(resources);
    }

    public void addResourceToDevice(final DeviceResourceProperties deviceResourceProperties){
        final Resources resources = transformDeviceResourcePropertiesToResources(deviceResourceProperties);
        resourceDAO.addResource(resources);
    }

    private DeviceResourceProperties transformResourcesToDeviceResourceProperties(final Resources resources){
        final DeviceResourceProperties deviceResourceProperties = new DeviceResourceProperties();
        deviceResourceProperties.setDeviceId(resources.getDevice().getDeviceId());
        deviceResourceProperties.setResourcePath(resources.getResourcePath());
        deviceResourceProperties.setResourceType(resources.getResourceType());
        return deviceResourceProperties;
    }
    private boolean isGroupRegistered(final String groupName){
        final DeviceGroup deviceGroup =
                deviceGroupDAO.findGroupByName(groupName);
        return null != deviceGroup;
    }
    private Device transformDeviceConnectionPropertiesToDevice(final DeviceConnectionProperties deviceConnectionProperties){
        final Device device = new Device();
        device.setDeviceType(deviceConnectionProperties.getDeviceType());
        device.setGroup(deviceGroupDAO.findGroupByName(deviceConnectionProperties.getDeviceGroupProperties().getGroupName()));
        device.setPartitionId(deviceConnectionProperties.getPartitionId());
        device.setLastModifUser("test-user");
        device.setLastModifDate(OffsetDateTime.now());
        device.setDeviceId(deviceConnectionProperties.getDeviceId());
        return device;
    }

    private DeviceConnectionProperties transformDeviceToDeviceConnectionProperties(final Device device){
        final DeviceConnectionProperties deviceConnectionProperties = new DeviceConnectionProperties();
        deviceConnectionProperties.setDeviceId(device.getDeviceId());
        deviceConnectionProperties.setDeviceType(device.getDeviceType());
        deviceConnectionProperties.setPartitionId(device.getPartitionId());
        return deviceConnectionProperties;
    }

    private DeviceGroupProperties transformDeviceGroupToDeviceGroupProperties(final DeviceGroup deviceGroup){
        final DeviceGroupProperties deviceGroupProperties = new DeviceGroupProperties();
        deviceGroupProperties.setGroupName(deviceGroup.getConsumerGroup());
        deviceGroupProperties.setTopicName(deviceGroup.getTopicName());
        return deviceGroupProperties;
    }

    private DeviceGroup transformDeviceGroupPropertiesToDeviceGroup(final DeviceGroupProperties deviceGroupProperties){
        final DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setConsumerGroup(deviceGroupProperties.getGroupName());
        deviceGroup.setTopicName(deviceGroupProperties.getTopicName());
        return deviceGroup;
    }

    private Resources transformDeviceResourcePropertiesToResources(final DeviceResourceProperties deviceResourceProperties){
        final Device device = deviceDAO.getDeviceById(deviceResourceProperties.getDeviceId());
        final Resources resources = new Resources();
        resources.setDevice(device);
        resources.setResourcePath(deviceResourceProperties.getResourcePath());
        resources.setResourceType(deviceResourceProperties.getResourceType());
        return resources;
    }

}
