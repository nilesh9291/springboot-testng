package com.rest.userapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * This dto class act as a data transfer object between the controller and the data layer.  
 *
 */

public class LoginDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "The unique username of the user.")	
	@NotBlank(message = "User Name can not be empty.")
	private String userName;
    
    @ApiModelProperty(notes = "The password of the user.")	
	@NotBlank(message = "Password can not be empty.")
    private String password;

	public LoginDto() {
	}

	
	public LoginDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginDto [userName=" + userName + ", password=" + password + "]";
	}
}
