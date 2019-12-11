package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageFactoryManager {
	

	public static CustomerPageObject getLoginPageObject(WebDriver driver) {
		return new CustomerPageObject(driver);
		
	}
	
	public static AccountPageOject getAccountPageObject(WebDriver driver) {
		return new AccountPageOject(driver);
		
	}
	
	public static DepositPageObject getDepositPageObject(WebDriver driver) {
		return new DepositPageObject(driver);
	}
	
	
	public static ValicationPageOject getValicationPageOject(WebDriver driver) {
		return new ValicationPageOject(driver);
	}

	}

