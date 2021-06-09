package com.test.pages;

import org.openqa.selenium.By;
import com.test.base.Page;


public class HomePage extends Page{

	public void goToCustomers() {
		click("customersLink_CSS");
	}

	public void goToEnterprise() {
		click("enterpriseLink_CSS");
	}

	public void goToSupport() {
		click("supportLink_CSS");
	}

	public void goToSales() {
		click("salesLink_CSS");
	}

	public void goToSignUp() {

		click("signupLink_CSS");
		
	}

	public LoginPage goToLogin() {
		click("loginLink_CSS");
		return new LoginPage();
		
	}

	public void validtaeFooterLinks() {

	}

}
