package com.scim.utils;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.core.util.Assert;


import io.restassured.response.Response;

public class TokenManager {

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
}
