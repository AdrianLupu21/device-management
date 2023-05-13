package com.smartmug.device.management.error;

import java.text.MessageFormat;

public class DeviceManagementError {

    public static DeviceManagementException newError(final DeviceManagementErrorCode deviceManagementErrorCode,
                                                     final String... args){
        return new DeviceManagementException(MessageFormat.format(deviceManagementErrorCode.getValue(), args),
                deviceManagementErrorCode);
    }

    public static class DeviceManagementException extends RuntimeException{

        private DeviceManagementErrorCode deviceManagementErrorCode;
        public DeviceManagementException(final String message, final DeviceManagementErrorCode deviceManagementErrorCode) {
            super(message);
            this.deviceManagementErrorCode = deviceManagementErrorCode;
        }

        public DeviceManagementErrorCode getDeviceManagementErrorCode() {
            return deviceManagementErrorCode;
        }
    }
}
