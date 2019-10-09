package com.rest.userapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserBaseException {
 
	private static final long serialVersionUID = 1L;

    public UserNotFoundException(HttpStatus statusCode, String errorMessage) {
        super(statusCode,errorMessage);
    }
}