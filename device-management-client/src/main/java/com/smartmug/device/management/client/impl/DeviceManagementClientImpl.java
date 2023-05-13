package com.smartmug.device.management.client.impl;

import com.smartmug.device.management.client.error.DeviceManagementClientError;
import com.smartmug.device.management.client.spi.DeviceManagementClient;
import com.smartmug.device.management.dto.DeviceConnectionProperties;
import com.smartmug.device.management.dto.DeviceResourceProperties;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;

import static com.smartmug.device.management.client.impl.DeviceManagementClientFactory.BASE_URL;

@Service
public class DeviceManagementClientImpl implements DeviceManagementClient {

    @Autowired
    DeviceManagementClientFactory deviceManagementClientFactory;

    @Override
    public DeviceResourceProperties getDeviceResourceProperties(final String deviceId,final String resourceType) throws URISyntaxException {
        final URIBuilder builder = new URIBuilder(BASE_URL + File.separator + "device" + File.separator
                + deviceId + File.separator + resourceType);
        final String requestPath = builder.build().toString();
        final Client client = deviceManagementClientFactory.getClient();
        final Response response = client.target(requestPath).request(MediaType.APPLICATION_JSON_TYPE).get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            return response.readEntity(DeviceResourceProperties.class);
        }else{
            throw DeviceManagementClientError.newError(response.getStatusInfo().getReasonPhrase(),
                    response.getStatus());
        }
    }

    @Override
    public DeviceConnectionProperties getDeviceConnectionProperties(String deviceId) throws URISyntaxException {
        final URIBuilder builder = new URIBuilder(BASE_URL + File.separator + "device" +
                File.separator + deviceId);
        final String requestPath = builder.build().toString();
        final Client client = deviceManagementClientFactory.getClient();
        final Response response = client.target(requestPath).request(MediaType.APPLICATION_JSON_TYPE).get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            return response.readEntity(DeviceConnectionProperties.class);
        }else{
            throw DeviceManagementClientError.newError(response.getStatusInfo().getReasonPhrase(),
                    response.getStatus());
        }
    }
}
