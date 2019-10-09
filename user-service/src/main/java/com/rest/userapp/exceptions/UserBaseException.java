package com.rest.userapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserBaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private HttpStatus statusCode;
	
	private String errorMessage;

	public UserBaseException(HttpStatus statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}