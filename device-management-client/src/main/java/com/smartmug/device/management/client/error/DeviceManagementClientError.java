package com.smartmug.device.management.client.error;


public class DeviceManagementClientError {

    public static DeviceManagementClientException newError(final String responseMessage, final int responseStatus){
        return new DeviceManagementClientException(responseMessage, responseStatus);
    }

    public static class DeviceManagementClientException extends RuntimeException{
        private int responseStatus;

        public DeviceManagementClientException(String message, int responseStatus) {
            super(message);
            this.responseStatus = responseStatus;
        }

        public int getResponseStatus() {
            return responseStatus;
        }
    }
}
