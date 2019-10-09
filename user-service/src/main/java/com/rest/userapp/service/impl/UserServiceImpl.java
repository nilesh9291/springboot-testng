package com.rest.userapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rest.userapp.constants.UserApplicationConstants;
import com.rest.userapp.dto.LoginDto;
import com.rest.userapp.dto.UserDto;
import com.rest.userapp.entity.User;
import com.rest.userapp.exceptions.UserAlreadyExistsException;
import com.rest.userapp.exceptions.UserNotFoundException;
import com.rest.userapp.mapper.UserMapper;
import com.rest.userapp.repository.UserRepository;
import com.rest.userapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

	@Transactional
	public UserDto save(UserDto userDto) {
		Optional<User> user = repository.findByUserName(userDto.getUserName());

		if (user != null && user.isPresent()) {
			throw new UserAlreadyExistsException(HttpStatus.CONFLICT,UserApplicationConstants.USER_NAME_ALREADY_EXISTS);
		} else {
			return mapper.convertEntityToDto(repository.save(mapper.convertDtoToEntity(userDto)));
		}
	}

	@Transactional
	public UserDto update(long userId, UserDto userDto) {

		if (userId == userDto.getUserId()) {
			Optional<User> user = repository.findById(userId);

			if (user != null && user.isPresent()) {
				return mapper.convertEntityToDto(repository.save(mapper.convertDtoToEntity(userDto)));
			} else {
				throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND);
			}
		} else {
			throw new UserNotFoundException(HttpStatus.CONFLICT, UserApplicationConstants.USER_ID_INCORRECT);
		}
	}

	public UserDto findById(long userId) {
		Optional<User> user = repository.findById(userId);

		if (user != null && user.isPresent()) {
			return mapper.convertEntityToDto(user.get());
		} else {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND);
		}
	}

	public List<UserDto> findAll() {
		List<User> userList = repository.findAll();

		if (userList == null || userList.isEmpty()) {
			//throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USERS_NOT_FOUND);
			return new ArrayList<UserDto>();
		}

		return userList.stream().map(user -> mapper.convertEntityToDto(user)).collect(Collectors.toList());
	}

	@Transactional
	public String delete(long userId) {
		Optional<User> user = repository.findById(userId);

		if (user != null && user.isPresent()) {
			repository.deleteById(userId);
			
			return UserApplicationConstants.USER_DELETED;
		} else {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND);
		}
	}

	@Transactional
	public String deleteAll() {
		List<User> userList = repository.findAll();

		if (userList == null || userList.isEmpty()) {
			//throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USERS_NOT_FOUND);
			return UserApplicationConstants.USERS_NOT_FOUND;
		} else {
			repository.deleteAll();
			
			return UserApplicationConstants.USERS_DELETED;
		}
	}

	public UserDto findByUserName(String userName) {
		Optional<User> user = repository.findByUserName(userName);

		if (user != null && user.isPresent()) {
			return mapper.convertEntityToDto(user.get());
		} else {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND);
		}
	}

	public UserDto login(LoginDto loginDto) {
		Optional<User> user = repository.findByUserNameAndPassword(loginDto.getUserName(), loginDto.getPassword());

		if (user != null && user.isPresent()) {
			return mapper.convertEntityToDto(user.get());
		} else {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_LOGIN_FAILURE);
		}
	}
}
