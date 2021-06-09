package com.test.testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.test.base.Page;
import com.test.pages.ZohoAppPage;
import com.test.pages.CRM.accounts.AccountsPage;
import com.test.pages.CRM.accounts.CreateAccountPage;
import com.test.utilities.Utilities;

public class CreateAccountTest {
	
	@Test(dataProviderClass = Utilities.class, dataProvider = "dp")
	public void createAccountTest(Hashtable<String, String> data) {
		
		ZohoAppPage zp = new ZohoAppPage();
		zp.goToCRM();
		 
		AccountsPage account = Page.topMenu.goToAccounts();
		CreateAccountPage cp = account.goToCreateAccount();
		cp.createAccount(data.get("accountName"));
		
	}

}
