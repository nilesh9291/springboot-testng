package com.rest.userapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.userapp.constants.UserApplicationConstants;
import com.rest.userapp.dto.LoginDto;
import com.rest.userapp.dto.UserDto;
import com.rest.userapp.enums.UserResponseStatus;
import com.rest.userapp.service.UserService;
import com.rest.userapp.wrapper.UserResponseWrapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This controller class specifies the operations performed on a user. The
 * typical operations performed includes the create, update, get, getAll, delete
 * and deleteAll operations.
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	// ***************************** Create a User *****************************//
	/**
	 * The method inserts a new record into the database table for the user entity.
	 * On successful insertion the response is returned in jsend format. The
	 * response includes the status as string, data as array and the error object
	 * along with the error code and error message if any.
	 * 
	 * @param userDto
	 * @return response Entity with a Response Wrapper
	 * @see ResponseEntity<UserResponseWrapper<<UserDto>>
	 */
	@ApiOperation(value = "Add an user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = UserApplicationConstants.USER_CREATED),
			@ApiResponse(code = 401, message = UserApplicationConstants.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = UserApplicationConstants.FORBIDDEN),
			@ApiResponse(code = 409, message = UserApplicationConstants.USER_NAME_ALREADY_EXISTS)})
	@PostMapping
	public ResponseEntity<UserResponseWrapper<UserDto>> save(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<UserResponseWrapper<UserDto>>(
				new UserResponseWrapper<UserDto>(UserResponseStatus.SUCCESS, userService.save(userDto)),
				HttpStatus.CREATED);
	}

	// ***************************** Update a User *****************************//
	/**
	 * The method updates an existing record from the database table for the user
	 * entity. On successful update the response is returned in jsend format. The
	 * response includes the status as string "SUCCESS / FAILURE / ERROR", data as
	 * array and the error object along with the error code and error message if
	 * any.
	 * 
	 * @param userId and userDto
	 * @return response Entity with a Response Wrapper
	 * @see ResponseEntity<UserResponseWrapper<<UserDto>>
	 */
	@ApiOperation(value = "Update an user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 401, message = UserApplicationConstants.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = UserApplicationConstants.FORBIDDEN),
			@ApiResponse(code = 409, message = UserApplicationConstants.USER_ID_INCORRECT)})
	@PutMapping(value = "/{userId}")
	public ResponseEntity<UserResponseWrapper<UserDto>> update(@PathVariable("userId") long userId,
			@RequestBody UserDto userDto) {
		return new ResponseEntity<UserResponseWrapper<UserDto>>(
				new UserResponseWrapper<UserDto>(UserResponseStatus.SUCCESS, userService.update(userId, userDto)),
				HttpStatus.OK);
	}

	// ***************************** Get single User *****************************//
	/**
	 * The method retrieves a single record from the database table for the user
	 * entity. On successful retrieval the response is returned in jsend format. The
	 * response includes the status as string "SUCCESS / FAILURE / ERROR", data as
	 * array and the error object along with the error code and error message if
	 * any.
	 * 
	 * @param userId
	 * @return response Entity with a Response Wrapper
	 * @see ResponseEntity<UserResponseWrapper<<UserDto>>
	 */
	@ApiOperation(value = "Search an user with an ID", response = UserDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 404, message = UserApplicationConstants.USER_NOT_FOUND) })
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserResponseWrapper<UserDto>> getById(@PathVariable("userId") long userId) {
		return new ResponseEntity<UserResponseWrapper<UserDto>>(
				new UserResponseWrapper<UserDto>(UserResponseStatus.SUCCESS, userService.findById(userId)),
				HttpStatus.OK);
	}

	/// ***************************** Get all Users *****************************//
	/**
	 * The method retrieves all the records from the database table for the user
	 * entity. On successful retrieval the response is returned in jsend format. The
	 * response includes the status as string "SUCCESS / FAILURE / ERROR", data as
	 * array and the error object along with the error code and error message if
	 * any.
	 * 
	 * @return response Entity with a Response Wrapper
	 * @see ResponseEntity<UserResponseWrapper<List<UserDto>>>
	 */
	@ApiOperation(value = "View a list of available users", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 401, message = UserApplicationConstants.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = UserApplicationConstants.FORBIDDEN),
			@ApiResponse(code = 404, message = UserApplicationConstants.USER_NOT_FOUND) })
	@GetMapping(value = "/")
	public ResponseEntity<UserResponseWrapper<List<UserDto>>> getAll() {
		return new ResponseEntity<UserResponseWrapper<List<UserDto>>>(
				new UserResponseWrapper<List<UserDto>>(UserResponseStatus.SUCCESS, userService.findAll()),
				HttpStatus.OK);
	}

	// ***************************** Delete a User *****************************//
	/**
	 * The method deletes a single record from the database table for the user
	 * entity. On successful deletion response string is returned.
	 * 
	 * @param userId
	 * @return String
	 */
	@ApiOperation(value = "Delete an user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 401, message = UserApplicationConstants.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = UserApplicationConstants.FORBIDDEN),
			@ApiResponse(code = 404, message = UserApplicationConstants.USER_NOT_FOUND) })
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<UserResponseWrapper<String>>  delete(@PathVariable("userId") long userId) {
		return new ResponseEntity<UserResponseWrapper<String>>(
				new UserResponseWrapper<String>(UserResponseStatus.SUCCESS, userService.delete(userId)),
				HttpStatus.OK);
	}

	// ***************************** Delete all Users *****************************//
	/**
	 * The method deletes all the records from the database table for the user
	 * entity. On successful deletion response string is returned.
	 * 
	 * @return String
	 */
	@ApiOperation(value = "Delete all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 401, message = UserApplicationConstants.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = UserApplicationConstants.FORBIDDEN),
			@ApiResponse(code = 404, message = UserApplicationConstants.USER_NOT_FOUND) })
	@DeleteMapping(value = "/")
	public ResponseEntity<UserResponseWrapper<String>> deleteAll() {
		return new ResponseEntity<UserResponseWrapper<String>>(
				new UserResponseWrapper<String>(UserResponseStatus.SUCCESS, userService.deleteAll()),
				HttpStatus.OK);
	}

	// ***************************** User Login *****************************//
	/**
	 * The method retrieves a single record from the database table for the user
	 * entity. On successful retrieval the response is returned in jsend format. The
	 * response includes the status as string "SUCCESS / FAILURE / ERROR", data as
	 * array and the error object along with the error code and error message if
	 * any.
	 * 
	 * @param LoginDto
	 * @return response Entity with a Response Wrapper
	 * @see ResponseEntity<UserResponseWrapper<<UserDto>>
	 */
	@ApiOperation(value = "Login with userName and password", response = UserDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = UserApplicationConstants.SUCCESS),
			@ApiResponse(code = 404, message = UserApplicationConstants.USER_NOT_FOUND) })
	@PostMapping(value = "/login")
	public ResponseEntity<UserResponseWrapper<UserDto>> loginUser(@Valid @RequestBody LoginDto loginDto) {
		return new ResponseEntity<UserResponseWrapper<UserDto>>(
				new UserResponseWrapper<UserDto>(UserResponseStatus.SUCCESS, userService.login(loginDto)),
				HttpStatus.OK);
	}
}