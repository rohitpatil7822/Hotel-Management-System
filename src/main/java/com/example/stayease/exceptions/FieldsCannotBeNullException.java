package com.example.stayease.exceptions;
import org.springframework.http.HttpStatus;

public class FieldsCannotBeNullException extends RuntimeException{
    
    private HttpStatus status;

    public FieldsCannotBeNullException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getHttpStatus(){

        return this.status;
    }

}