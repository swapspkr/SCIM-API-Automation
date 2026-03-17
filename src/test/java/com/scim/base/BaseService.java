package com.scim.base;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

	private static final String BASE_URI = "https://scimcloud.secureplatform.io/";
	private RequestSpecification requestspecification;

	public BaseService() {
		requestspecification = given().baseUri(BASE_URI);
	}

	/**
	 * Generic POST request with JSON body
	 */
	protected Response postRequest(String payload, String endpoint) {
		return requestspecification.contentType(ContentType.JSON).body(payload).post(endpoint);
	}

	/**
	 * Generic POST request with form parameters (x-www-form-urlencoded)
	 */
	protected Response postForm(Map<String, String> formParams,String endpoint) {
		RequestSpecification req = requestspecification.contentType(ContentType.URLENC);
		// add each key-value pair as form param
		formParams.forEach(req::formParam);
		return req.post(endpoint);
	}
}
