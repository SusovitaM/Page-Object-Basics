package com.test.base;

import com.test.pages.CRM.accounts.AccountsPage;

public class CRMTopMenu {
	public void goToHome() {

	}

	public void goToLeads() {

	}

	public void goToContacts() {

	}

	public AccountsPage goToAccounts() {
		Page.click("accountMenu_CSS");
		return new AccountsPage();
	}

	public void goToDeals() {

	}

	public void goToActivities() {

	}

	public void goToReports() {

	}
	
	public void signOut() {
		
	}
}
