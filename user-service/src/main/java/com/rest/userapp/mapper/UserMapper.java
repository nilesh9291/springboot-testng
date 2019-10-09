package com.rest.userapp.mapper;

import org.springframework.stereotype.Component;

import com.rest.userapp.dto.UserDto;
import com.rest.userapp.entity.User;

@Component
public class UserMapper {
	public User convertDtoToEntity(UserDto userDto) {
		User user = null;

		if (userDto != null) {
			user = new User();
			user.setId(userDto.getUserId());
			user.setUserName(userDto.getUserName());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setAge(userDto.getAge());
			user.setPassword(userDto.getPassword());
		}

		return user;
	}
	
	public UserDto convertEntityToDto(User user) {
		UserDto userDto = null;

		if (user != null) {
			userDto = new UserDto();
			userDto.setUserId(user.getId());
			userDto.setUserName(user.getUserName());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setAge(user.getAge());
			userDto.setPassword(user.getPassword());
		}

		return userDto;
	}

}
