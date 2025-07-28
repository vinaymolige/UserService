package com.UserService.UserService.ExceptionHandler;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {

        super(message);
    }
}

