package com.coursemanagement.exception;

import com.coursemanagement.dto.ErrorDTO;

public class ResourceNotFoundException extends Exception{

    private final transient ErrorDTO errorDTO;

    public ResourceNotFoundException(String message) {
        super(message);
        this.errorDTO = null;
    }

    public ResourceNotFoundException(String message, ErrorDTO errorDTO) {
        super(message);
        this.errorDTO = errorDTO;
    }
}
