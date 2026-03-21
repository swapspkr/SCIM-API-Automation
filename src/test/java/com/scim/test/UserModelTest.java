package com.scim.test;

import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.scim.base.UserService;
import com.scim.models.request.UserPutRequest;
import com.scim.models.request.UserRequest;
import com.scim.models.request.UserRequest.Email;
import com.scim.models.response.UserResponse;
import com.scim.utils.TestDataUtils;
import com.scim.utils.TokenManager;

import io.restassured.response.Response;

public class UserModelTest {

    private static String createdUserId; // store id for PUT
    private static String username;
    private static String displayName;
    private static String email;
    
    
	@Test
	public void getUsers() {
		UserService userService = new UserService();
		Response userResponse = userService.getUser(TokenManager.getAuthToken());
		System.out.println(userResponse.asPrettyString());
		Assert.assertEquals(userResponse.getStatusCode(), 200);
	}

	@Test
	public void createUser() {
		username = TestDataUtils.getUsername();
		displayName = TestDataUtils.getDisplayName(username);
		email = TestDataUtils.getEmail(username);
		String password = TestDataUtils.getPassword();

		UserRequest userRequest = new UserRequest.Builder()
				.schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")).userName(username)
				.password(password).displayName(displayName).emails(Arrays.asList(new Email("home", email))).build();
		UserService userService = new UserService();
		Response response  = userService.postUser(TokenManager.getAuthToken(),userRequest);
		UserResponse userResponse = response.as(UserResponse.class);

        // Save the user id for PUT
        createdUserId = userResponse.getId();
        
		Assert.assertEquals(userResponse.getDisplayName(), displayName);
		
		Assert.assertEquals(userResponse.getUserName(), username.toLowerCase());
	}
	
	@Test(dependsOnMethods = "createUser")
	public void putUser() {
		 String updatedDisplayName = displayName + "_UPDATED";
		 
		String password = TestDataUtils.getPassword();

		// Build PUT request using the ID from created user
		UserPutRequest putRequest = new UserPutRequest.Builder()
                .schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User"))
                .id(createdUserId) // <-- pass the ID here
                .userName(username)
                .displayName(updatedDisplayName)
                .emails(Arrays.asList(new UserPutRequest.Email("home", email)))
                .build();
		
		UserService userService = new UserService();
		Response response  = userService.putUser(TokenManager.getAuthToken(),putRequest,createdUserId);
		UserPutResponse userResponse = response.as(UserPutResponse.class);

		Assert.assertEquals(userResponse.getDisplayName(), updatedDisplayName);
		
		Assert.assertEquals(userResponse.getUserName(), username.toLowerCase());
	}
}
