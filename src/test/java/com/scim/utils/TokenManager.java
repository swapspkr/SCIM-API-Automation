package com.scim.utils;

import com.scim.base.AuthService;
import com.scim.models.request.LoginRequest;
import com.scim.models.response.LoginResponse;

import io.restassured.response.Response;

public class TokenManager {
	private static final ThreadLocal<String> token = new ThreadLocal<>();
	private static final ThreadLocal<Long> tokenTime = new ThreadLocal<>();

	// Token valid for 50 mins (adjust based on API)
	private static final long EXPIRY_TIME = 50 * 60 * 1000;
    String currentToken = token.get();
    Long time = tokenTime.get();
	
	public static String getAuthToken() {

	    //String currentToken = token.get();
	    //Long time = tokenTime.get();

		/*
		 * if (currentToken != null && time != null && (System.currentTimeMillis() -
		 * time) < EXPIRY_TIME) { return currentToken; }
		 */

	    String username = ConfigReader.get("PlatformApplicationUser");
	    String password = ConfigReader.get("PlatformApplicationUserPassword");

	    AuthService authService = new AuthService();
	    Response response = authService.generateToken(new LoginRequest(username, password));

	    if (response.getStatusCode() != 200) {
	        throw new RuntimeException("Failed to generate token: " + response.asPrettyString());
	    }

	    LoginResponse loginResponse = response.as(LoginResponse.class);

	    token.set(loginResponse.getAccess_token());
	    tokenTime.set(System.currentTimeMillis());

	    return token.get();
	}
}
