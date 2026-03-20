package com.scim.utils;

import com.scim.base.AuthService;
import com.scim.models.request.LoginRequest;
import com.scim.models.response.LoginResponse;

import io.restassured.response.Response;

public class TokenManager {
	private static String token;
	private static long tokenTime;

	// Token valid for 50 mins (adjust based on API)
	private static final long EXPIRY_TIME = 50 * 60 * 1000;

	/*
	 * private static String token; public Response response;
	 * 
	 * @Test public void getToken() { if (token == null) { response =
	 * generateToken(); } Assert.assertEquals(response.statusCode(), 200); }
	 * 
	 * public Response generateToken() { response =
	 * given().baseUri(baseURI).header("Content-Type",
	 * "application/x-www-form-urlencoded") .formParam("grant_type",
	 * "client_credentials").formParam("scope", "xpmheadless")
	 * .formParam("client_id", "serviceqauser@scimcloud").formParam("client_secret",
	 * "Cybage@123") .post("/identity/api/oauth2/token/xpmplatform");
	 * 
	 * System.out.println(response.asPrettyString()); return response; }
	 */

	public static String getAuthToken() {

		// reuse token if not expired
		if (token != null && (System.currentTimeMillis() - tokenTime) < EXPIRY_TIME) {
			return token;
		}

		// Use ConfigReader for credentials instead of hardcoding
		String username = ConfigReader.get("PlatformApplicationUser");
		String password = ConfigReader.get("PlatformApplicationUserPassword");

		AuthService authService = new AuthService();
		Response response = authService.getToken(new LoginRequest(username, password));

		// validate token response
		if (response.getStatusCode() != 200) {
			throw new RuntimeException("Failed to generate token: " + response.asPrettyString());
		}
		LoginResponse loginResponse = response.as(LoginResponse.class);
		token = loginResponse.getAccess_token();

		// set tokenTime AFTER successful generation
		tokenTime = System.currentTimeMillis();

		System.out.println("Generated New Token: " + token);

		return token;
	}
}
