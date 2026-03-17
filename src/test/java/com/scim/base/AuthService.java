package com.scim.base;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class AuthService extends BaseService{

	
	private static final String BASE_PATH="/scim/v2";
	
	/*
	 * public Response getToken(String payload) { return
	 * postRequest(payload,"/identity/api/oauth2/token/xpmplatform"); }
	 */
	
	/**
     * Get OAuth2 token using client credentials
     */
    public Response getToken(String clientId, String clientSecret) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("scope", "xpmheadless");
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);

        return postForm(formParams,"/identity/api/oauth2/token/xpmplatform");
    }
}
