package com.scim.base;

import com.scim.models.request.UserRequest;

import io.restassured.response.Response;

public class UserService extends BaseService{

	private static final String BASE_PATH="/scim/v2";
	
	public Response getUser(String token) {
		setToken(token);
		return getRequest(BASE_PATH+"/Users");
	}
	public Response createUser(String token,UserRequest payload) {
		setToken(token);
        return postRequest(payload,BASE_PATH+"/Users");
    }
}
