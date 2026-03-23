package com.scim.base;

import com.scim.models.request.UserPutRequest;
import com.scim.models.request.UserRequest;

import io.restassured.response.Response;

public class UserService extends BaseService{

	private static final String BASE_PATH="/scim/v2";
	
	public Response getUser(String token) {
		setToken(token);
		return getRequest(BASE_PATH+"/Users");
	}
	
	public Response getUserById(String token,String userId) {
		setToken(token);
		return getRequest(BASE_PATH+"/Users/"+userId);
	}
	
	public Response getUserWithCount(String token) {
		setToken(token);
		return getRequest(BASE_PATH+"/Users"+"?Count=100&StartIndex=1");
	}
	
	public Response postUser(String token,UserRequest payload) {
		setToken(token);
        return postRequest(payload,BASE_PATH+"/Users");
    }
	
	public Response putUser(String token,UserPutRequest payload,String userId) {
		setToken(token);
        return putRequest(payload,BASE_PATH+"/Users/"+userId);
    }
}
