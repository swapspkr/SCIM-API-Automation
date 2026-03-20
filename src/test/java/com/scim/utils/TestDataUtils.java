package com.scim.utils;

import java.util.Random;

public class TestDataUtils {
	private static final Random random = new Random();

	public static String getUsername() {
		return "SCIMCloudRestAssuredTestUser_" + random.nextInt(10000);
	}

	// displayName should be same as username
	public static String getDisplayName(String username) {
		return username;
	}

	public static String getEmail(String username) {
		return username.toLowerCase() + "@test.com";
	}

	public static String getPassword() {
		return "Test@" + random.nextInt(10000);
	}
}
