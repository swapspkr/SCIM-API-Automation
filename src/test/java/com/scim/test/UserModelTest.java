package com.scim.test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.scim.base.UserService;
import com.scim.models.request.UserPutRequest;
import com.scim.models.request.UserRequest;
import com.scim.models.request.UserRequest.Email;
import com.scim.models.response.UserResponse;
import com.scim.utils.ExtentManager;
import com.scim.utils.TestDataUtils;
import com.scim.utils.TokenManager;

import io.restassured.response.Response;

@Listeners(com.scim.listeners.TestListener.class)
public class UserModelTest {

	private static String createdUserId; // store id for PUT
	private static String username;
	private static String displayName;
	private static String email;

	@Test(description = "Validate getUser API.",priority=0)
	public void getUsers() {
		ExtentManager.info("Starting GET Users API test...");
		UserService userService = new UserService();
		Response userResponse = userService.getUser(TokenManager.getAuthToken());

		// Logging
		ExtentManager.info("Validating response status...");
		Assert.assertEquals(userResponse.getStatusCode(), 200);
		ExtentManager.pass("GET Users API returned 200 OK");
	}

	@Test(description = "Validate user is created using POST User API.",priority=1)
	public void createUser() {
		ExtentManager.info("Starting POST User API test...");

		username = TestDataUtils.getUsername();
		displayName = TestDataUtils.getDisplayName(username);
		email = TestDataUtils.getEmail(username);
		String password = TestDataUtils.getPassword();
		ExtentManager.info("Creating user with username: " + username);

		UserRequest userRequest = new UserRequest.Builder()
				.schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")).userName(username)
				.password(password).displayName(displayName).emails(Arrays.asList(new Email("home", email))).build();
		UserService userService = new UserService();
		Response response = userService.postUser(TokenManager.getAuthToken(), userRequest);
		UserResponse userResponse = response.as(UserResponse.class);

		// Save the user id for PUT
		createdUserId = userResponse.getId();
		// Logging
		ExtentManager.info("User Created successfully. ID: " + createdUserId);

		Assert.assertEquals(userResponse.getDisplayName(), displayName);
		ExtentManager.pass("DisplayName validated successfully: " + displayName);
		Assert.assertEquals(userResponse.getUserName(), username.toLowerCase());
		ExtentManager.pass("Username validated successfully: " + username.toLowerCase());
	}

	@Test(description = "Validate user update using User PUT API.", dependsOnMethods = "createUser",priority=2)
	public void putUser() {
		ExtentManager.info("Starting PUT User API test...");
		String updatedDisplayName = displayName + "_UPDATED";
		String password = TestDataUtils.getPassword();

		// Build PUT request using the ID from created user
		UserPutRequest putRequest = new UserPutRequest.Builder()
				.schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")).id(createdUserId) // <-- pass the
																										// ID here
				.userName(username).displayName(updatedDisplayName)
				.emails(Arrays.asList(new UserPutRequest.Email("home", email))).build();

		UserService userService = new UserService();
		Response response = userService.putUser(TokenManager.getAuthToken(), putRequest, createdUserId);
		UserPutResponse userResponse = response.as(UserPutResponse.class);
		ExtentManager.info("Validating updated DisplayName and Username...");
		
		Assert.assertEquals(userResponse.getDisplayName(), updatedDisplayName);
		ExtentManager.pass("DisplayName updated successfully: " + updatedDisplayName);
		Assert.assertEquals(userResponse.getUserName(), username.toLowerCase());
		ExtentManager.pass("Username remains unchanged: " + username.toLowerCase());
	}
	
	
}
