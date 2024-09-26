package com.example.stayease.exceptions;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends RuntimeException{
    
    private HttpStatus status;

    public ResourceAlreadyExistsException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getHttpStatus(){

        return this.status;
    }

}

