package com.smartmug.device.management.dao;

import com.smartmug.device.management.entity.Device;
import com.smartmug.device.management.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceDAO {

    @Autowired
    private DeviceRepository deviceRepository;

    public void registerDevice(final Device device){
        if(null != device){
            deviceRepository.save(device);
        }
    }

    public Device getDeviceById(final String deviceId){
        return deviceRepository.findByDeviceId(deviceId);
    }
}
