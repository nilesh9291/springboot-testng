package com.rest.userapp.constants;

public class UserApplicationConstants {	
	public static final String SUCCESS = "Operation Successful";

	public static final String NO_CONTENT = "The server successfully processed the request and is not returning any content";

	public static final String NOT_AUTHORIZED = "You are not authorized to view the resource";

	public static final String FORBIDDEN = "You might not have the necessary permissions to access this resource";
	
	public static final String INTRERNAL_SERVER_ERROR = "Internal Server Error";
	
	public static final String USER_CREATED = "A new user has been created successfully";
	
	public static final String USER_DELETED = "The requested user has been deleted successfully";
	
	public static final String USERS_DELETED = "All the users have been deleted successfully";

	public static final String USER_NOT_FOUND = "The requested user can not be found at the moment";

	public static final String USER_NAME_ALREADY_EXISTS = "The requested user name already exists. Please choose a different userName";
	
	public static final String USER_LOGIN_FAILURE = "The userName and/or password are incorrect. Please try with the correct values";
	
	public static final String USER_ID_INCORRECT = "The userId's in the request are not matching. Please try with the correct values";
	
	public static final String USERS_NOT_FOUND = "No users found at the moment";
}