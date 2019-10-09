package com.rest.userapp.service.impl;

import static org.mockito.Mockito.when;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.rest.userapp.constants.UserApplicationConstants;
import com.rest.userapp.dto.LoginDto;
import com.rest.userapp.dto.UserDto;
import com.rest.userapp.entity.User;
import com.rest.userapp.exceptions.UserAlreadyExistsException;
import com.rest.userapp.exceptions.UserNotFoundException;
import com.rest.userapp.mapper.UserMapper;
import com.rest.userapp.repository.UserRepository;

public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl serviceImpl;

	@Mock
	UserRepository repository;
	
	@Mock
	UserMapper mapper;
	
	@BeforeTest()
	public void init() {
		System.out.println("init");
		MockitoAnnotations.initMocks(this);
	}

	@AfterTest()
	public void destroy() {
		System.out.println("destroy");
	}
	
	@Test(dataProvider = "DataContainer")
	public void save(UserDto userDto) {
		System.out.println("Save user");

		User user = createUserEntity(userDto);
		
		when(repository.findByUserName(userDto.getUserName())).thenReturn(null);
		when(mapper.convertDtoToEntity(userDto)).thenReturn(user);
		when(repository.save(user)).thenReturn(user);
		when(mapper.convertEntityToDto(user)).thenReturn(userDto);

		UserDto userDto2 = serviceImpl.save(userDto);

		Assert.assertNotNull(userDto2);
		Assert.assertEquals(userDto2.getUserName(), userDto.getUserName());
	}
	
	@Test(expectedExceptions = {UserAlreadyExistsException.class})
	public void saveWithException() {
		System.out.println("save user WithException");

		UserDto userDto = new UserDto("userName1", "firstName1", "lastName1");
		userDto.setPassword("password1");
		userDto.setAge(21L);
		userDto.setUserId(1L);
		
		User user = createUserEntity(userDto);
		when(repository.findByUserName(userDto.getUserName())).thenReturn(Optional.of(user));
	
		Assert.assertEquals(serviceImpl.save(userDto), 
				new UserAlreadyExistsException(HttpStatus.CONFLICT,UserApplicationConstants.USER_NAME_ALREADY_EXISTS));
	}

	@Test(dataProvider = "DataContainer")
	public void findById(UserDto userDto) {
		System.out.println("finding user by Id");

		User user = createUserEntity(userDto);
		
		when(repository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
		when(mapper.convertEntityToDto(user)).thenReturn(userDto);

		UserDto userDto2 = serviceImpl.findById(userDto.getUserId());

		Assert.assertNotNull(userDto2);
		Assert.assertEquals(userDto2.getUserName(), user.getUserName());
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class})
	public void findByIdException() {
		System.out.println("finding user by Id exception");

		Long userId = Mockito.anyLong();
		
		when(repository.findById(userId)).thenReturn(null);

		Assert.assertEquals(serviceImpl.findById(userId), 
				new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND));
	}

	@Test(dataProvider = "DataContainer")
	public void update(UserDto userDto) {
		System.out.println("updating user");
		System.out.println("userDto before update = " + userDto);

		User user = createUserEntity(userDto);
		
		userDto.setFirstName(new StringBuilder(userDto.getFirstName()).append("_updated").toString());
		when(repository.findById(userDto.getUserId())).thenReturn(Optional.of(user));
		when(mapper.convertDtoToEntity(userDto)).thenReturn(user);
		when(repository.save(user)).thenReturn(user);
		when(mapper.convertEntityToDto(user)).thenReturn(userDto);

		UserDto userDto2 = serviceImpl.update(userDto.getUserId(), userDto);
		System.out.println("userDto after update = " + userDto2);

		Assert.assertNotNull(userDto2);
		Assert.assertEquals(userDto2.getFirstName(), userDto.getFirstName());
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class })
	public void updateUserException() {
		System.out.println("update user exception");
		
		UserDto userDto = new UserDto("userName1", "firstName1", "lastName1");
		userDto.setPassword("password1");
		userDto.setAge(21L);
		userDto.setUserId(1L);
		
		Long userId = Mockito.anyLong();
		
		when(repository.findById(userId)).thenReturn(null);

		Assert.assertEquals(serviceImpl.update(userId, userDto), 
				new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND));
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class })
	public void updateUserWithIdConflictException() {
		System.out.println("update user conflict Id exception");
		
		UserDto userDto = new UserDto("userName1", "firstName1", "lastName1");
		userDto.setPassword("password1");
		userDto.setAge(21L);
		userDto.setUserId(1L);
		
		Long userId = Mockito.anyLong();
		
		Assert.assertEquals(serviceImpl.update(userId, userDto), 
				new UserNotFoundException(HttpStatus.CONFLICT, UserApplicationConstants.USER_ID_INCORRECT));
	}

	@Test(dataProvider = "DataContainer")
	public void findByUserName(UserDto userDto) {
		System.out.println("finding user by userName");

		User user = createUserEntity(userDto);

		when(repository.findByUserName(userDto.getUserName())).thenReturn(Optional.of(user));
		when(mapper.convertEntityToDto(user)).thenReturn(userDto);

		UserDto userDto2 = serviceImpl.findByUserName(userDto.getUserName());

		Assert.assertNotNull(userDto2);
		Assert.assertEquals(userDto2.getUserName(), user.getUserName());
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class})
	public void findByUserNameException() {
		System.out.println("findByUserName exception");

		String userName = Mockito.anyString();
		
		when(repository.findByUserName(userName)).thenReturn(null);

		Assert.assertEquals(serviceImpl.findByUserName(userName), 
				new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND));
	}

	@Test(dataProvider = "DataContainer")
	public void login(UserDto userDto) {
		System.out.println("User Login");
		
		User user = createUserEntity(userDto);
		
		when(repository.findByUserNameAndPassword(userDto.getUserName(), userDto.getPassword())).thenReturn(Optional.of(user));
		when(mapper.convertEntityToDto(user)).thenReturn(userDto);

		UserDto userDto2 = serviceImpl.login(new LoginDto(userDto.getUserName(), userDto.getPassword()));

		Assert.assertNotNull(userDto2);
		Assert.assertEquals(userDto2.getUserName(), user.getUserName());
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class})
	public void loginException() {
		System.out.println("User Login exception");
		
		String userName = Mockito.anyString();
		String password = Mockito.anyString();
		
		when(repository.findByUserNameAndPassword(userName, password)).thenReturn(null);

		Assert.assertEquals(serviceImpl.login(new LoginDto(userName, password)), new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND));
	}

	@Test(dataProvider = "DataContainer")
	public void delete(UserDto userDto) {
		System.out.println("deleting user");
		
		User user = createUserEntity(userDto);
		
		when(repository.findById(userDto.getUserId())).thenReturn(Optional.of(user));

		String actual = serviceImpl.delete(1L);

		Assert.assertEquals(actual, UserApplicationConstants.USER_DELETED);
	}
	
	@Test(expectedExceptions = {UserNotFoundException.class })
	public void deleteUserException() {
		System.out.println("delete user exception");

		Long userId = Mockito.anyLong();
		
		when(repository.findById(userId)).thenReturn(null);


		Assert.assertEquals(serviceImpl.delete(userId), 
				new UserNotFoundException(HttpStatus.NOT_FOUND, UserApplicationConstants.USER_NOT_FOUND));
	}
	
	@Test()
	public void findAll() {
		System.out.println("finding all users");

		List<User> userList = new ArrayList<User>();
		userList.add(new User("userName1", "firstName1", "lastName1"));
		userList.add(new User("userName2", "firstName2", "lastName2"));

		when(repository.findAll()).thenReturn(userList);

		List<UserDto> returUserList = serviceImpl.findAll();

		Assert.assertNotNull(returUserList);
		Assert.assertEquals(returUserList.size(), 2);
	}
	
	@Test()
	public void findAllNoUsersFound() {
		System.out.println("finding all users and getting empty list");

		when(repository.findAll()).thenReturn(null);

		List<UserDto> returUserList = serviceImpl.findAll();

		Assert.assertNotNull(returUserList);
		Assert.assertEquals(returUserList.size(), 0);
	}
	
	@Test()
	public void deleteAll() {
		System.out.println("deleting all users");
		
		List<User> userList = new ArrayList<User>();
		userList.add(new User("userName1", "firstName1", "lastName1"));
		userList.add(new User("userName2", "firstName2", "lastName2"));

		when(repository.findAll()).thenReturn(userList);

		String actual = serviceImpl.deleteAll();

		Assert.assertEquals(actual, UserApplicationConstants.USERS_DELETED);
	}

	@Test()
	public void deleteAllNoUsersFound() {
		System.out.println("deleting all users no users found");

		when(repository.findAll()).thenReturn(null);

		String actual = serviceImpl.deleteAll();

		Assert.assertEquals(actual, UserApplicationConstants.USERS_NOT_FOUND);
	}
	
	// A data provider method which will return a required data based on Test method name
	@DataProvider(name = "DataContainer")
	public static Object[] inputDataProvider(Method m, ITestContext iTestContext) {

		// Getting the method name
		String methodName = m.getName();
		String dataFilename = null;
		List<UserDto> userDtoList = new ArrayList<UserDto>();

		switch (methodName) {
		case "save":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);
		case "findById":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);
		case "update":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);	
		case "findByUserName":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);
		case "login":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);
		case "delete":
			dataFilename = iTestContext.getCurrentXmlTest().getParameter("inputData");
			
			readDataFromCsv(dataFilename, userDtoList);
			
			return userDtoList.stream().map(userDto -> new Object[] {userDto}).toArray(Object[][]::new);
		default:
			return new String[] { "No Data File" };
		}
	}
	
	public static void readDataFromCsv(String fileName, List<UserDto> userDtoList) {
		CSVReader reader = null;
		
		try {
			reader = new CSVReader(new FileReader(fileName));
			String[] line;
			
			while ((line = reader.readNext()) != null) {
				UserDto userDto = new UserDto(line[1], line[2], line[3]);
				userDto.setUserId(Long.parseLong(line[0]));
				userDto.setPassword(line[4]);
				userDto.setAge(Long.parseLong(line[5]));
				
				userDtoList.add(userDto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private User createUserEntity(UserDto userDto) {
		User user = new User(userDto.getUserName(), userDto.getFirstName(), userDto.getLastName());
		user.setId(userDto.getUserId());
		user.setAge(userDto.getAge());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
}
