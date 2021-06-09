package com.test.pages;

import com.test.base.Page;
import com.test.pages.CRM.CRMHomePage;

public class ZohoAppPage extends Page {

	
	public void goToBooks() {
		click("booksLink_CSS");
	}

	public void goToCalender() {
		click("calenderLink_CSS");
	}

	public void goToCliq() {
		click("cliqLink_CSS");
	}

	public void goToConnect() {
		click("connectLink_CSS");
	}

	public CRMHomePage goToCRM() {
		click("CRMlink_CSS");
		return new CRMHomePage();
	}

	public void goToWriter() {
		click("writerLink_CSS");
	}

}
