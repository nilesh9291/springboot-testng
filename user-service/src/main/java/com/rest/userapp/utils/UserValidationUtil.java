package com.rest.userapp.utils;

import com.rest.userapp.exceptions.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class UserValidationUtil {

	public static List<ErrorDetails> fromBindingErrors(Errors errors) {
		List<ErrorDetails> errorDetailsList = new ArrayList<ErrorDetails>();

		for (ObjectError objectError : errors.getAllErrors()) {
			errorDetailsList.add(new ErrorDetails(HttpStatus.BAD_REQUEST.toString(),objectError.getDefaultMessage()));
		}
		
		return errorDetailsList;
	}
}