package com.scim.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.scim.base.AuthService;
import com.scim.models.request.LoginRequest;
import com.scim.models.response.LoginResponse;
import com.scim.utils.ConfigReader;

import io.restassured.response.Response;

public class AuthenticationModelTest {

	
	@Test
	public void validateGetToken() {
        // Use ConfigReader for credentials instead of hardcoding
        String username = ConfigReader.get("PlatformApplicationUser");
        String password = ConfigReader.get("PlatformApplicationUserPassword");

		LoginRequest loginRequest = new LoginRequest(username, password);
		AuthService authService = new AuthService();
		// replace with your actual credentials
        Response response = authService.getToken(loginRequest);
        
        LoginResponse loginResponse = response.as(LoginResponse.class);
        
        System.out.println("Response: " + response.asPrettyString());
        System.out.println(loginResponse.getAccess_token());
        
		// simple assertion Assert.assertEquals(200, response.getStatusCode());
		Assert.assertTrue(loginResponse.getAccess_token()!=null);
		 
	}
}
