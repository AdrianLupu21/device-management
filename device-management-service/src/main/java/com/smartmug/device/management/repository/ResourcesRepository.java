package com.smartmug.device.management.repository;

import com.smartmug.device.management.entity.Device;
import com.smartmug.device.management.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Integer> {
    @Query("SELECT r FROM com.smartmug.device.management.entity.Resources r WHERE r.resourceType= :resourceType " +
            "AND r.device= :device")
    Resources findByDeviceIdAndResourceType(@Param("device") final Device device, @Param("resourceType") final String resourceType);
}
