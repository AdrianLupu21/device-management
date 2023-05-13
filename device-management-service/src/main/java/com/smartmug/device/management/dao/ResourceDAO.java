package com.smartmug.device.management.dao;

import com.smartmug.device.management.entity.Device;
import com.smartmug.device.management.entity.Resources;
import com.smartmug.device.management.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceDAO {

    @Autowired
    private ResourcesRepository resourcesRepository;

    public void addResource(final Resources resources){
        if(null != resources) {
            resourcesRepository.save(resources);
        }
    }

    public Resources getResourceByDeviceIdAndResourceType(final Device device, final String resourceType){
        if(null != device && null != resourceType){
            return resourcesRepository.findByDeviceIdAndResourceType(device, resourceType);
        }else {
            throw new RuntimeException();
        }
    }
}
