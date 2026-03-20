package com.scim.base;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

import java.util.HashMap;
import java.util.Map;

import com.scim.models.request.LoginRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

	private static final String BASE_URI = "https://scimcloud.secureplatform.io/";
	private RequestSpecification requestspecification;

	public BaseService() {
		requestspecification = given().baseUri(BASE_URI);
	}
	
	// setToken
	public void setToken(String token) {
	    requestspecification.header("Authorization", "Bearer " + token);
	}
	
	// Generic GET request 
	protected Response getRequest(String endpoint) {
		return requestspecification.get(endpoint);
	}

	 //Generic POST request with JSON body 
	protected Response postRequest(Object payload, String endpoint) {
		return requestspecification.contentType(ContentType.JSON).body(payload).post(endpoint);
	}

	//Generic POST request with form parameters (x-www-form-urlencoded)	
	protected Response postForm(LoginRequest payload, String endpoint) {
		RequestSpecification req = requestspecification.contentType(ContentType.URLENC);
		Map<String, String> formParams = new HashMap<>();
		formParams.put("grant_type", "client_credentials");
		formParams.put("scope", "xpmheadless");
		formParams.put("client_id", payload.getUsername());
		formParams.put("client_secret", payload.getPassword());
		// add each key-value pair as form param
		formParams.forEach(req::formParam);
		return req.post(endpoint);
	}
}
