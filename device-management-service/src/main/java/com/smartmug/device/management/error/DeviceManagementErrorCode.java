package com.smartmug.device.management.error;

public enum DeviceManagementErrorCode {
    NO_GROUP_ASSOCIATED_TO_DEVICE("There is no group {0} associated to device {1}"),
    DUPLICATE_GROUP("A group with name {0} is already registered in the database"),
    NO_DEVICE_FOUND("There is no device with the id {0} registered in the database"),
    NO_GROUP_FOUND("There is no group called {0} registered in the database");

    private String value;

    DeviceManagementErrorCode(final String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
