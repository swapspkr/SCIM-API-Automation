package com.scim.base;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import com.scim.filters.LoggingFilters;
import com.scim.models.request.LoginRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

	  // ThreadLocal RequestSpecification for parallel execution
    private static final ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();

    private static final String BASE_URI = "https://scimintegration.secureplatform.io/";

    // Static block to attach filters globally
    static {
        RestAssured.filters(new LoggingFilters());
    }

    // =========================
    // GET OR INITIALIZE THREADLOCAL SPEC
    // =========================
    protected RequestSpecification getRequestSpec() {
        RequestSpecification spec = requestSpec.get();
        if (spec == null) {
            spec = initRequestSpec();
            requestSpec.set(spec);
        }
        return spec;
    }

    // =========================
    // INIT NEW SPEC
    // =========================
    private RequestSpecification initRequestSpec() {
        return given().baseUri(BASE_URI).contentType(ContentType.JSON).accept(ContentType.JSON);
    }

    // =========================
    // CLEAR THREADLOCAL SPEC
    // =========================
    protected void clearRequestSpec() {
        requestSpec.remove();
    }

    // =========================
    // SET AUTH TOKEN
    // =========================
    public void setToken(String token) {
    	requestSpec.set(getRequestSpec().auth().oauth2(token));
    }

    // =========================
    // GENERIC API METHODS
    // =========================
    protected Response getRequest(String endpoint) {
        return getRequestSpec().get(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return getRequestSpec().body(payload).post(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint) {
        return getRequestSpec().body(payload).put(endpoint);
    }

    protected Response postForm(LoginRequest payload, String endpoint) {
        RequestSpecification req = RestAssured.given()
                .baseUri(BASE_URI)
                .contentType(ContentType.URLENC);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("scope", "xpmheadless");
        formParams.put("client_id", payload.getUsername());
        formParams.put("client_secret", payload.getPassword());

        formParams.forEach(req::formParam);
        return req.post(endpoint);
    }
}
