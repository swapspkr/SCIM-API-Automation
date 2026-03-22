package com.scim.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.scim.base.AuthService;
import com.scim.models.request.LoginRequest;
import com.scim.models.response.LoginResponse;
import com.scim.utils.ConfigReader;
import com.scim.utils.ExtentManager;

import io.restassured.response.Response;

@Listeners(com.scim.listeners.TestListener.class)
public class AuthenticationModelTest {

	@Test(description = "Validate able to generate Auth token")
	public void validateGetToken() {
		// Use ConfigReader for credentials instead of hardcoding
		String username = ConfigReader.get("PlatformApplicationUser");
		String password = ConfigReader.get("PlatformApplicationUserPassword");

		LoginRequest loginRequest = new LoginRequest(username, password);
		AuthService authService = new AuthService();
		// replace with your actual credentials
		Response response = authService.generateToken(loginRequest);

		LoginResponse loginResponse = response.as(LoginResponse.class);

		// Logging
		ExtentManager.info("Validating response status...");
		Assert.assertEquals(response.getStatusCode(), 200);

		ExtentManager.info("Validating access token...");
		Assert.assertNotNull(loginResponse.getAccess_token());

		ExtentManager.pass("Token generated successfully");

	}
}
