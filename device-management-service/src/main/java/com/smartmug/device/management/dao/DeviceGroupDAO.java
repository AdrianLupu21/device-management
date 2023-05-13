package com.smartmug.device.management.dao;

import com.smartmug.device.management.entity.DeviceGroup;
import com.smartmug.device.management.repository.DeviceGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceGroupDAO {
    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    public void saveGroup(final DeviceGroup deviceGroup){
        if(null != deviceGroup){
            deviceGroupRepository.save(deviceGroup);
        }
    }

    public DeviceGroup findGroupByName(final String groupName){
        if(null != groupName && !groupName.isEmpty()) {
            return deviceGroupRepository.findGroupByName(groupName);
        }else {
            throw new RuntimeException();
        }
    }
}
