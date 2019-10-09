package com.rest.userapp.service;

import java.util.List;

import com.rest.userapp.dto.LoginDto;
import com.rest.userapp.dto.UserDto;

public interface UserService {
	UserDto save(UserDto userDto);
	
	UserDto update(long userId, UserDto userDto);

	UserDto findById(long id);

	List<UserDto> findAll();

	String delete(long userId);
	
	String deleteAll();

	UserDto findByUserName(String userName);
	
	UserDto login(LoginDto loginDto);
}
