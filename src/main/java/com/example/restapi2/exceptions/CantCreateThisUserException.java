package com.example.restapi2.exceptions;

public class CantCreateThisUserException extends RuntimeException {
    private static final long serialVersionUID = 2;

    public CantCreateThisUserException(String message) {
        super(message);
    }
}
