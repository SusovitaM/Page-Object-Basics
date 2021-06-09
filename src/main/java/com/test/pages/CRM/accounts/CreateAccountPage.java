package com.test.pages.CRM.accounts;

import com.test.base.Page;

public class CreateAccountPage extends Page{

	//CRMTopMenu topMenu = new CRMTopMenu();
	public void createAccount(String accountName) {
		
		type("accountName_CSS", accountName);
		
	}
}
