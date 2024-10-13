package com.coursemanagement.controller;

import com.coursemanagement.dto.ErrorDTO;
import com.coursemanagement.exception.ErrorCode;
import com.coursemanagement.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.coursemanagement.exception.ErrorCode.RECORD_NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String REQUEST_FAILED_WITH_INTERNAL_SERVER_ERROR =
            "Request Failed With Internal Server Error. Exception: {} Path: {}";

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // This exception handler will catch RecordNotFoundException throws
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(ResourceNotFoundException recordNotFoundException) {
        logger.error("RecordNotFoundException: ", recordNotFoundException);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(RECORD_NOT_FOUND.getCode());
        errorDTO.setMessage(
                recordNotFoundException.getMessage() != null && !recordNotFoundException.getMessage().isEmpty()
                        ? recordNotFoundException.getMessage()
                        : RECORD_NOT_FOUND.getDescription()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(IllegalArgumentException illegalArgumentException) {
        logger.error("IllegalArgumentException: ", illegalArgumentException);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(String.valueOf(ErrorCode.BAD_REQUEST));
        errorDTO.setMessage(
                illegalArgumentException.getMessage()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
