package com.test.pages;
import com.test.base.Page;

public class LoginPage extends Page{
	
	public ZohoAppPage doLogin(String userName, String password) {
		
		clear("email_CSS");
		type("email_CSS", userName);
		click("loginNextBtn_CSS");
		type("password_CSS", password);
		click("signINBtn_CSS");
		
		return new ZohoAppPage();
	}

}
