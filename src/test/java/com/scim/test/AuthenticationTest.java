package com.scim.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.scim.base.AuthService;

import io.restassured.response.Response;

public class AuthenticationTest {

	
	@Test
	public void validateGetToken() {
		AuthService authService = new AuthService();
		// replace with your actual credentials
        Response response = authService.getToken("serviceqauser@scimcloud", "Cybage@123");

        System.out.println("Response: " + response.asPrettyString());
        System.out.println("Status code: " + response.getStatusCode());

        // simple assertion
        assertEquals(200, response.getStatusCode());
	}
}
