package com.scim.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // =========================
    // INIT REPORT
    // =========================
    public synchronized static ExtentReports getReporter() {

        if (extent == null) {

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            String reportPath = System.getProperty("user.dir")
                    + "/reports/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle("SCIM API Automation Report");
            spark.config().setReportName("SCIM RestAssured Execution Report");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System Info
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Framework", "SCIM RestAssured API Automation");
        }

        return extent;
    }

    // =========================
    // START TEST
    // =========================
    public synchronized static ExtentTest startTest(String testName) {
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    // =========================
    // GET CURRENT TEST
    // =========================
    public static ExtentTest getTest() {
        return test.get();
    }

    // =========================
    // FLUSH REPORT
    // =========================
    public synchronized static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    // =========================
    // LOG METHODS
    // =========================
    public static void info(String message) {
        getTest().info(format(message));
    }

    public static void pass(String message) {
        getTest().pass(format(message));
    }

    public static void fail(String message) {
        getTest().fail(color(message, "red"));
    }

    public static void skip(String message) {
        getTest().skip(color(message, "orange"));
    }

    // =========================
    // HTML FORMATTING
    // =========================
    private static String format(String message) {
        return "<pre>" + message + "</pre>";
    }

    private static String color(String message, String color) {
        return "<span style='color:" + color + ";'>" + message + "</span>";
    }

    // =========================
    // API HELPERS (OPTIONAL)
    // =========================
    public static void logRequest(String request) {
        getTest().info("<details><summary><b>REQUEST</b></summary>" +
                "<pre>" + request + "</pre></details>");
    }

    public static void logResponse(String response) {
        getTest().info("<details><summary><b>RESPONSE</b></summary>" +
                "<pre>" + response + "</pre></details>");
    }

    public static void logValidation(String message) {
        getTest().pass("<b>✔ VALIDATION:</b> " + message);
    }

    public static void logError(String message) {
        getTest().fail("<b>✘ ERROR:</b> " + message);
    }
}
