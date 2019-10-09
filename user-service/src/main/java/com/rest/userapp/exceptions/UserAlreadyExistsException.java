package com.rest.userapp.exceptions;


import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends UserBaseException {
	private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(HttpStatus statusCode, String errorMessage) {
        super(statusCode,errorMessage);
    }
}