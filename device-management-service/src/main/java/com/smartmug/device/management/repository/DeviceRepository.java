package com.smartmug.device.management.repository;

import com.smartmug.device.management.entity.Device;
import com.smartmug.device.management.entity.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM com.smartmug.device.management.entity.Device d WHERE d.deviceId= :deviceId")
    Device findByDeviceId(@Param("deviceId") final String deviceId);
}
