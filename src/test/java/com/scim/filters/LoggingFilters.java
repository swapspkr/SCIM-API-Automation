package com.scim.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.scim.utils.ExtentManager;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilters implements Filter {

	private static final Logger logger = LogManager.getLogger(LoggingFilters.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {

		long start = System.currentTimeMillis();

		String request = buildRequest(requestSpec);
		logger.info(request);
		ExtentManager.info(request);

		Response response = ctx.next(requestSpec, responseSpec);

		long time = System.currentTimeMillis() - start;

		String responseLog = buildResponse(response);
		logger.info(responseLog);
		ExtentManager.info(responseLog);

		logger.info("Response Time: {} ms", time);
		ExtentManager.info("Response Time: " + time + " ms");

		// Mark API status in report
		if (response.getStatusCode() >= 400) {
			ExtentManager.fail("API FAILED: HTTP " + response.getStatusCode());
		} else {
			ExtentManager.pass("API PASSED: HTTP " + response.getStatusCode());
		}

		return response;
	}

	private String buildRequest(FilterableRequestSpecification req) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n===== REQUEST =====\n");
		sb.append(req.getMethod()).append(" ").append(req.getURI()).append("\n");

		// Mask sensitive headers
		req.getHeaders().forEach(
				h -> sb.append(h.getName()).append(": ").append(maskHeader(h.getName(), h.getValue())).append("\n"));

		if (req.getBody() != null) {
			sb.append("\nBODY:\n").append(pretty(req.getBody().toString()));
		}
		return sb.toString();
	}

	private String buildResponse(Response res) {
		String body = res.getBody().asString();
		body = maskSensitiveFields(body); // mask access_token / refresh_token

		return "\n===== RESPONSE =====\n" + "STATUS: " + res.getStatusCode() + "\n\n" + "BODY:\n" + pretty(body);
	}

	private String pretty(String body) {
		try {
			return io.restassured.path.json.JsonPath.from(body).prettify();
		} catch (Exception e) {
			return body;
		}
	}

	private String maskHeader(String header, String value) {
		if ("Authorization".equalsIgnoreCase(header)) {
			return "Bearer ****";
		}
		return value;
	}

	private String maskSensitiveFields(String body) {
		try {
			io.restassured.path.json.JsonPath jp = new io.restassured.path.json.JsonPath(body);
			if (jp.get("access_token") != null) {
				body = body.replace(jp.getString("access_token"), "****");
			}
			if (jp.get("refresh_token") != null) {
				body = body.replace(jp.getString("refresh_token"), "****");
			}
			return body;
		} catch (Exception e) {
			// If body is not JSON, return as-is
			return body;
		}
	}

}
