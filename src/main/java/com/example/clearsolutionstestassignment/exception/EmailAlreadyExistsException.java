package com.example.clearsolutionstestassignment.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 3;

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
