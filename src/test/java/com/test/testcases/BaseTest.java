package com.test.testcases;

import org.testng.annotations.AfterSuite;

import com.test.base.Page;

public class BaseTest {

	@AfterSuite
	public void tearDown() {
		Page.quit();
	}
}
