package com.scim.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	 private static final String filepath = System.getProperty("user.dir")+File.separator + "/src/test/resources/config.properties";
	 private static Properties properties;

	    static {
	        try {
	            FileInputStream fis = new FileInputStream(filepath);
	            properties = new Properties();
	            properties.load(fis);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to load config.properties", e);
	        }
	    }

	    public static String get(String key) {
	        String value = properties.getProperty(key);
	        if (value == null) {
	            throw new RuntimeException("Key '" + key + "' not found in config.properties");
	        }
	        return value.trim(); // trim whitespace
	    }
}
