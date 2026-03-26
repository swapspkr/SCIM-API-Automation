package com.scim.utils;

import com.scim.base.AuthService;
import com.scim.models.request.LoginRequest;
import com.scim.models.response.LoginResponse;

import io.restassured.response.Response;

public class TokenManager {

	private static volatile String sharedToken;
	private static volatile long tokenTime;

	// Token valid for 50 mins
	private static final long EXPIRY_TIME = 50 * 60 * 1000;

	private static final Object lock = new Object();

	public static String getAuthToken() {

		// Fast path (no locking)
		if (sharedToken != null && (System.currentTimeMillis() - tokenTime) < EXPIRY_TIME) {
			return sharedToken;
		}

		// Lock only when needed
		synchronized (lock) {

			// Double-check after acquiring lock
			if (sharedToken != null && (System.currentTimeMillis() - tokenTime) < EXPIRY_TIME) {
				return sharedToken;
			}

			// Generate new token
			sharedToken = generateNewToken();
			tokenTime = System.currentTimeMillis();

			return sharedToken;
		}
	}

	private static String generateNewToken() {

		String username = ConfigReader.get("PlatformApplicationUser");
		String password = ConfigReader.get("PlatformApplicationUserPassword");

		AuthService authService = new AuthService();
		Response response = authService.generateToken(new LoginRequest(username, password));

		if (response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to generate token: " + response.asPrettyString());
		}

		LoginResponse loginResponse = response.as(LoginResponse.class);
		return loginResponse.getAccess_token();
	}
}
