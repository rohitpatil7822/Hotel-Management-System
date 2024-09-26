package com.example.stayease.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundException(ResourceNotFoundException ex) {
        // ResourceNotFoundException errorDetails = new ResourceNotFoundException(HttpStatus.NOT_FOUND, ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("message", errorMessage);
                                 
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FieldsCannotBeNullException.class)
    public ResponseEntity<Map<String, Object>> handleFieldsCannotBeNullException(FieldsCannotBeNullException ex){
        // FieldsCannotBeNullException errorDetails = new FieldsCannotBeNullException(HttpStatus.BAD_REQUEST, ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        // ResourceAlreadyExistsException errorDetails = new ResourceAlreadyExistsException(HttpStatus.CONFLICT, ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("message", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
        // ErrorResponseException errorDetails = new ErrorResponseException(HttpStatus.BAD_REQUEST, ex.getMessage());
        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalExceptionHandler(Exception ex) {
        // ErrorResponseException errorDetails = new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
