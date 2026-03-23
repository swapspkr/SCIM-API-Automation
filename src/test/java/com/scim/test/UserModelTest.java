package com.scim.test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.scim.base.UserService;
import com.scim.models.request.UserPutRequest;
import com.scim.models.request.UserRequest;
import com.scim.models.request.UserRequest.Email;
import com.scim.models.response.UserPutResponse;
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
	private static UserService userService;
	private static UserRequest userRequest;
	private static Response response;
	private static UserResponse userResponse;

	@Test(description = "Validate getUser API.", priority = 0)
	public void validategetUserAPI() {
		SoftAssert softAssert = new SoftAssert();
		ExtentManager.info("Starting GET Users API test...");
		userService = new UserService();
		Response response = userService.getUser(TokenManager.getAuthToken());

		String responseBody = response.getBody().asString();
		ExtentManager.info("Response body is not empty.");
		softAssert.assertFalse(responseBody.isEmpty(), "Response body should not be empty");
		
		// Logging
		ExtentManager.info("Validating response status...");
		softAssert.assertEquals(response.getStatusCode(), 200);
		ExtentManager.pass("GET Users API returned 200 OK");
		softAssert.assertAll();
	}

	@Test(description = "Validate user is created using POST User API.", priority = 1)
	public void validatePostUserAPI() {
		ExtentManager.info("Starting POST User API test...");

		username = TestDataUtils.getUsername();
		displayName = TestDataUtils.getDisplayName(username);
		email = TestDataUtils.getEmail(username);
		String password = TestDataUtils.getPassword();
		ExtentManager.info("Creating user with username: " + username);

		userRequest = new UserRequest.Builder().schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User"))
				.userName(username).password(password).displayName(displayName)
				.emails(Arrays.asList(new Email("home", email))).build();
		userService = new UserService();
		response = userService.postUser(TokenManager.getAuthToken(), userRequest);
		userResponse = response.as(UserResponse.class);

		// Save the user id for PUT
		createdUserId = userResponse.getId();
		// Logging
		ExtentManager.info("User Created successfully. ID: " + createdUserId);

		Assert.assertEquals(userResponse.getDisplayName(), displayName);
		ExtentManager.pass("DisplayName validated successfully: " + displayName);
		Assert.assertEquals(userResponse.getUserName(), username.toLowerCase());
		ExtentManager.pass("Username validated successfully: " + username.toLowerCase());
	}

	@Test(description = "Validate get user response when pass by ID.",dependsOnMethods = "validatePostUserAPI", priority = 2)
	public void validateGetUserById() {
		SoftAssert softAssert = new SoftAssert();
		
		ExtentManager.info("Starting Get UserById API test...");
		userService = new UserService();
		response = userService.getUserById(TokenManager.getAuthToken(), createdUserId);
		
		String responseBody = response.getBody().asString();
		ExtentManager.info("Response body is not empty.");
		softAssert.assertFalse(responseBody.isEmpty(), "Response body should not be empty");
	
		userResponse = response.as(UserResponse.class);
		softAssert.assertNotNull(userResponse.getId(), "id should not be null");
	    ExtentManager.info("id is not null: " + userResponse.getId());
		
		softAssert.assertNotNull(userResponse.getUserName(), "userName should not be null");
	    ExtentManager.info("userName is not null: " + userResponse.getUserName());	    

		try {
			softAssert.assertEquals(userResponse.getId(), createdUserId);
			ExtentManager.pass("Success !. Able to fetch User by ID ..");
		} catch (Exception e) {
			ExtentManager.logError("Validation Failed . Failed to getUser by ID ..");
			e.printStackTrace();
		}
	    softAssert.assertAll();
	}

	@Test(description = "Validate user update using User PUT API.", dependsOnMethods = "validatePostUserAPI", priority = 3)
	public void validateputUserAPI() {
		ExtentManager.info("Starting PUT User API test...");
		String updatedDisplayName = displayName + "_UPDATED";
		String password = TestDataUtils.getPassword();

		// Build PUT request using the ID from created user
		UserPutRequest putRequest = new UserPutRequest.Builder()
				.schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")).id(createdUserId) // <-- pass the
																										// ID here
				.userName(username).displayName(updatedDisplayName).active(true)
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
