package com.smartmug.device.management.controller;

import com.smartmug.device.configuration.dto.DeviceConfigurationErrorDTO;
import com.smartmug.device.management.error.DeviceManagementError;
import com.smartmug.device.management.error.DeviceManagementErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.smartmug.device.management.error.DeviceManagementErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class DeviceManagementExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeviceManagementError.DeviceManagementException.class)
    public ResponseEntity<DeviceConfigurationErrorDTO> handleException(
            final DeviceManagementError.DeviceManagementException deviceConfigurationException){
        final Map<DeviceManagementErrorCode, HttpStatus> errorCodes = mapErrorCodes();
        DeviceConfigurationErrorDTO deviceConfigurationErrorDTO = new DeviceConfigurationErrorDTO();
        deviceConfigurationErrorDTO.setCode(deviceConfigurationException.getDeviceManagementErrorCode().name());
        deviceConfigurationErrorDTO.setMessage(deviceConfigurationException.getMessage());
        deviceConfigurationErrorDTO.setTimestamp(Instant.now().toString());
        return new ResponseEntity<>(deviceConfigurationErrorDTO,
                errorCodes.get(deviceConfigurationException.getDeviceManagementErrorCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        final StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName)
                    .append(":")
                    .append(message)
                    .append(System.lineSeparator());
        });
        final DeviceConfigurationErrorDTO deviceConfigurationErrorDTO = new DeviceConfigurationErrorDTO();
        deviceConfigurationErrorDTO.setMessage(errorMessage.toString());
        deviceConfigurationErrorDTO.setCode(BAD_REQUEST.toString());
        deviceConfigurationErrorDTO.setTimestamp(Instant.now().toString());
        return new ResponseEntity<>(deviceConfigurationErrorDTO, HttpStatus.BAD_REQUEST);
    }

    public Map<DeviceManagementErrorCode, HttpStatus> mapErrorCodes(){
        final Map<DeviceManagementErrorCode, HttpStatus> errors = new HashMap<>();
        errors.put(NO_GROUP_ASSOCIATED_TO_DEVICE, BAD_REQUEST);
        errors.put(DUPLICATE_GROUP, BAD_REQUEST);
        errors.put(NO_GROUP_FOUND, NOT_FOUND);
        errors.put(NO_DEVICE_FOUND, NOT_FOUND);
        return errors;
    }
}
