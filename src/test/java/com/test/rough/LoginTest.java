package com.test.rough;

import com.test.base.Page;
import com.test.pages.HomePage;
import com.test.pages.LoginPage;
import com.test.pages.ZohoAppPage;
import com.test.pages.CRM.accounts.AccountsPage;
import com.test.pages.CRM.accounts.CreateAccountPage;

public class LoginTest {

	public static void main(String[] args) throws InterruptedException {
		
		HomePage home = new HomePage();
		LoginPage login = home.goToLogin();			
		ZohoAppPage zohoApp = login.doLogin("sony.swastik@gmail.com", "Selenium@1");		
		zohoApp.goToCRM();		
		
		Thread.sleep(2000);	
		AccountsPage account = Page.topMenu.goToAccounts();		
		CreateAccountPage cap = account.goToCreateAccount();		
		cap.createAccount("Susovita");

	}

}
