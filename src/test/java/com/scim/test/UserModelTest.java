package com.scim.test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.scim.base.UserService;
import com.scim.models.request.UserRequest;
import com.scim.models.request.UserRequest.Email;
import com.scim.utils.TestDataUtils;
import com.scim.utils.TokenManager;

import io.restassured.response.Response;

public class UserModelTest {

	@Test
	public void getUsers() {
		UserService userService = new UserService();
		Response userResponse = userService.getUser(TokenManager.getAuthToken());
		System.out.println(userResponse.asPrettyString());
		Assert.assertEquals(userResponse.getStatusCode(), 200);
	}

	@Test
	public void createUser() {
		String username = TestDataUtils.getUsername();
		String displayName = TestDataUtils.getDisplayName(username);
		String email = TestDataUtils.getEmail(username);
		String password = TestDataUtils.getPassword();

		UserRequest userRequest = new UserRequest.Builder()
				.schemas(Arrays.asList("urn:ietf:params:scim:schemas:core:2.0:User")).userName(username)
				.password(password).displayName(displayName).emails(Arrays.asList(new Email("home", email))).build();
		UserService userService = new UserService();
		Response response  = userService.createUser(TokenManager.getAuthToken(),userRequest);
		System.out.println(response.asPrettyString());

	}
}
