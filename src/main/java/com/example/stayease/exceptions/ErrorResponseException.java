package com.example.stayease.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends Exception{

    private HttpStatus status;

    public ErrorResponseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }


    public HttpStatus getHttpStatus(){

        return this.status;
    }
    
}
