package com.rest.userapp.wrapper;

import com.rest.userapp.enums.UserResponseStatus;
import com.rest.userapp.exceptions.ErrorDetails;

import java.util.List;

public class UserResponseWrapper<T> {

    private UserResponseStatus status;
    private T data;
    private List<ErrorDetails> errorDetails;

    public UserResponseWrapper() {
    }

    public UserResponseWrapper(UserResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public UserResponseWrapper(UserResponseStatus status, List<ErrorDetails> errorDetails) {
        this.status = status;
        this.errorDetails = errorDetails;
    }

    public UserResponseStatus getStatus() {
        return status;
    }

    public void setStatus(UserResponseStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<ErrorDetails> errorDetails) {
        this.errorDetails = errorDetails;
    }

    @Override
	public String toString() {
		return "UserResponseWrapper [status=" + status + ", data=" + data + ", errorDetails=" + errorDetails + "]";
	}	
}
