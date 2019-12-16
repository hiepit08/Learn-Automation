package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageFactoryManager {
	

	public static EditCustomerPageObject getLoginPageObject(WebDriver driver) {
		return new EditCustomerPageObject(driver);
		
	}
	
	public static NewCustomerPageOject getNewCustomerPageOject(WebDriver driver) {
		return new NewCustomerPageOject(driver);
		
	}
	
	public static EditCustomerPageObject getEditCustomerPageObject(WebDriver driver) {
		return new EditCustomerPageObject(driver);
		
	}
	
	public static NewAccountPageObject getNewAccountPageObject(WebDriver driver) {
		return new NewAccountPageObject(driver);
		
	}
	
	public static EditAccountPageOject getEditAccountPageOject(WebDriver driver) {
		return new EditAccountPageOject(driver);
		
	}
	
	public static DepositPageObject getDepositPageObject(WebDriver driver) {
		return new DepositPageObject(driver);
	}
	
	public static WithdrawalPageObject getWithdrawalPageObject(WebDriver driver) {
		return new WithdrawalPageObject(driver);
	}
	
	public static FundTransferPageObject getFundTransferPageObject(WebDriver driver) {
		return new FundTransferPageObject(driver);
	}
	
	public static DeleteAccountPageOject getDeleteAccountPageOject(WebDriver driver) {
		return new DeleteAccountPageOject(driver);
	}
	
	public static DeleteCustomerPageOject getDeleteCustomerPageOject(WebDriver driver) {
		return new DeleteCustomerPageOject(driver);
	}
	}

