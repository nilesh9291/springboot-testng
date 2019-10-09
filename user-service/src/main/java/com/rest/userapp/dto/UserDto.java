package com.rest.userapp.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rest.userapp.custom.validator.ValidPassword;

import io.swagger.annotations.ApiModelProperty;

/**
 * This dto class act as a data transfer object between the controller and the data layer.  
 *
 */

public class UserDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "The database generated user ID")
    private Long userId;
	
	@ApiModelProperty(notes = "The unique username of the user.")	
	@NotBlank(message = "User Name can not be empty.")
	@Size(min=2, max=20, message = "User Name must be between 2 to 20 characters.")
	private String userName;
	
    @ApiModelProperty(notes = "User's first name")
    @NotBlank(message = "First Name can not be empty.")
    @Size(min=2, max=20, message = "First Name must be between 2 to 20 characters.")
	private String firstName;

    @ApiModelProperty(notes = "User's last name")
    @NotBlank(message = "Last Name can not be empty.")
    @Size(min=2, max=20, message = "Last Name must be between 2 to 20 characters.")
	private String lastName;
    	
    @ApiModelProperty(notes = "User's age")
    @Min(value = 18, message="User's age must be above 18.")
    private Long age;
    
    @ApiModelProperty(notes = "The password of the user.")	
	@NotBlank(message = "Password can not be empty.")
    @ValidPassword
    private String password;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserDto() {
	}

	public UserDto(String userName, String firstName, String lastName) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", age=" + age + "]";
	}		
}
