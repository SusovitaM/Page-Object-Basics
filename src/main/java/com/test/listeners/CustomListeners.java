package com.test.listeners;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.Status;
import com.test.base.Page;
import com.test.utilities.MonitoringMail;
import com.test.utilities.TestConfig;
import com.test.utilities.Utilities;

public class CustomListeners extends Page implements ITestListener, ISuiteListener {

	public String messageBody;
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName().toUpperCase());
		extentTest.set(test);

		if (!Utilities.isTestRunnable(result.getName(), excel)) {
			throw new SkipException("Skipping the test" + result.getName().toUpperCase() + " as run mode is NO");
		}

	}

	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, result.getName().toUpperCase() + "TEST PASSED");
		extent.flush();

	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			Utilities.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		extentTest.get().log(Status.FAIL,
				result.getName().toUpperCase() + "TEST FAILED with Exception: " + result.getThrowable());

		try {
			extentTest.get().addScreenCaptureFromPath(Utilities.screenshotName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Reporter.log("Capturing screenshot!");
		Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + ">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src=" + Utilities.screenshotName
				+ " height=100 width=100></img></a>");

		extent.flush();

	}

	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, result.getName().toUpperCase() + " Skipped the test as runmode is NO!");
		extent.flush();

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onStart(ISuite suite) {

	}

	public void onFinish(ISuite suite) {
		
		MonitoringMail mail = new MonitoringMail();
		
		try {
			messageBody = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8081/job/PageObjectModel/Extent_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);

	}

}
