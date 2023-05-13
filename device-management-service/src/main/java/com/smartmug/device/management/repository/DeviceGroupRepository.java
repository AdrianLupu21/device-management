package com.smartmug.device.management.repository;

import com.smartmug.device.management.entity.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
    @Query("SELECT g FROM com.smartmug.device.management.entity.DeviceGroup g WHERE g.consumerGroup= :groupName")
    DeviceGroup findGroupByName(@Param("groupName") final String groupName);
}
