package com.smartmug.device.management.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name="device")
public class Device {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private DeviceGroup deviceGroup;

    @Column(name="partition_id")
    private int partitionId;

    @Column(name="registration_date")
    private OffsetDateTime registrationDate;

    @Column(name="last_modif_date")
    private OffsetDateTime lastModifDate;

    @Column(name="last_modif_user")
    private String lastModifUser;

    @Column(name="device_type")
    private String deviceType;

    @Column(name="device_id")
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceGroup getGroup() {
        return deviceGroup;
    }

    public void setGroup(DeviceGroup deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }

    public OffsetDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(OffsetDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public OffsetDateTime getLastModifDate() {
        return lastModifDate;
    }

    public void setLastModifDate(OffsetDateTime lastModifDate) {
        this.lastModifDate = lastModifDate;
    }

    public String getLastModifUser() {
        return lastModifUser;
    }

    public void setLastModifUser(String lastModifUser) {
        this.lastModifUser = lastModifUser;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
