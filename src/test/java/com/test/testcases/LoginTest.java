package com.test.testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.test.pages.HomePage;
import com.test.pages.LoginPage;
import com.test.pages.ZohoAppPage;
import com.test.utilities.Utilities;

public class LoginTest extends BaseTest {

	@Test(dataProviderClass = Utilities.class, dataProvider = "dp")
	public void loginTest(Hashtable<String, String> data) {
		HomePage home = new HomePage();
		LoginPage login = home.goToLogin();			
		ZohoAppPage zohoApp = login.doLogin(data.get("userName"), data.get("password"));
		
	}
}
