package com.scim.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.scim.utils.ExtentManager;

public class TestListener implements ITestListener {
	private static final Logger logger = LogManager.getLogger(TestListener.class);

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getReporter(); // initialize report
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentManager.startTest(result.getMethod().getMethodName());
		logger.info("Test Started: {}", result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentManager.pass("Test Passed");
		logger.info("Test Passed: {}", result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentManager.fail("Test Failed: " + result.getThrowable());
		logger.error("Test Failed: {}", result.getName());
		logger.error("Exception: ", result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentManager.skip("Test Skipped");
		logger.info("Test Skipped: {}", result.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flush(); // write report
		logger.info("Write extent report: {}");
	}
}
