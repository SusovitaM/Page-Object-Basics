package com.test.pages.CRM.accounts;

import com.test.base.Page;

public class AccountsPage extends Page{
	
	//CRMTopMenu topMenu = new CRMTopMenu();
	public CreateAccountPage goToCreateAccount() {
		
		click("createAccountBtn_XPATH");
		return new CreateAccountPage();
	}

	public void goToImportAccount() {

	}
}
