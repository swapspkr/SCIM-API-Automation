package com.scim.base;

import com.scim.models.request.LoginRequest;

import io.restassured.response.Response;

public class AuthService extends BaseService{
   
	/**
     * Generate OAuth2 token using client credentials
     */
    public Response generateToken(LoginRequest payload) {
        return postForm(payload,"/identity/api/oauth2/token/xpmplatform");
    }
}
