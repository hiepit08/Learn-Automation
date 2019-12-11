package guru.follow;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import guru.data.AccountData;
import guru.data.LoginData;
import objectUIs.DynamicUIs;
import pageObjects.AccountPageOject;
import pageObjects.CustomerPageObject;
import pageObjects.DepositPageObject;
import pageObjects.PageFactoryManager;

@Test
public class FollowGuruAccount extends AbstractTest {
	public WebDriver driver;
	private CustomerPageObject customer;
	private AccountPageOject account;
	private DepositPageObject deposit;
	private String userID;
	private String pass;
	private String urlCurrent;
	private String email = LoginData.EMAIL + randomNumber() + "@vnpay.vn";
	private String CustomerID;
	private String AccountID;
	private String AccountID_other;
	private int initial;
	private int transfer;
	private int withdrawIn;
	private int transferOther;
	private int currentBalance;
	private int currentBalanceTransfer;
	private int currentBalanceWithdraw;

	@Parameters({ "browser", "version", "url" })
	@BeforeTest
	public void beforeClass(String browserName, String driverVersion, String url) {
		driver = openMultiBrowser(browserName, driverVersion, url);
	}

	@Test
	public void TC_01_getAcc() throws InterruptedException {
		customer = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_01_Step: mo browser");
		urlCurrent = customer.getCurrentURL(driver);
		customer.openURL(driver, LoginData.HERE_LINK);

		log.info("TC_01_Step: Nhap email");
		customer.senkeyDynamicText(driver, email, "emailid");

		log.info("TC_01_Step: Click login");
		customer.clickToElement(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "btnLogin");

		log.info("TC_01_Step: Lay thong tin account");
		userID = customer.getDynamicText(driver, "User ID :");
		pass = customer.getDynamicText(driver, "Password :");
	}

	@Test
	public void TC_02_Login() {
		customer = PageFactoryManager.getLoginPageObject(driver);
		customer.openURL(driver, urlCurrent);

		log.info("TC_01_Step: Nhap account");
		customer.senkeyDynamicText(driver, userID, "uid");
		customer.senkeyDynamicText(driver, pass, "password");

		log.info("TC_01_Step: login");
		customer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "btnLogin");
	}

	@Test
	public void TC_03_MyAccount() throws InterruptedException {
		customer = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_03_Step: chon button new customer");
		customer.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Customer");

		log.info("TC_03_Step: Nhap name");
		customer.senkeyDynamicText(driver, LoginData.NAME, "name");

		log.info("TC_03_Step: Nhap ngay sinh");
		customer.senkeyDynamicText(driver, LoginData.BIRTH, "dob");

		log.info("TC_03_Step: Nhap dia chi");
		customer.senkeyDynamicArea(driver, LoginData.ADDRESS, "addr");

		log.info("TC_03_Step: Nhap city");
		customer.senkeyDynamicText(driver, LoginData.CITY, "city");

		log.info("TC_03_Step: Nhap state");
		customer.senkeyDynamicText(driver, LoginData.STATE, "state");

		log.info("TC_03_Step: Nhap so dien thoai");
		customer.senkeyDynamicText(driver, LoginData.MOBI, "telephoneno");

		log.info("TC_03_Step: Nhap pin");
		customer.senkeyDynamicText(driver, LoginData.PIN, "pinno");

		log.info("TC_03_Step: Nhap email");
		customer.senkeyDynamicText(driver, email, "emailid");

		log.info("TC_03_Step: Nhap pass");
		customer.senkeyDynamicText(driver, pass, "password");

		log.info("TC_03_Step: submit");
		customer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "sub");
	}

	@Test
	public void TC_04_verifyAccount() {
		customer = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_Step: erify mesage");
		verifyTrue(customer.isDynamicMessage(driver, objectUIs.Message.CREATE_SUCCESS));

		log.info("TC_Step: get cusID");

		CustomerID = customer.getDynamicText(driver, "Customer ID");
		System.out.println(CustomerID);

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "Customer Name"), LoginData.NAME);

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "Address"), LoginData.ADDRESS);

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "City"), LoginData.CITY);

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "State"), LoginData.STATE);

		log.info("TC_Step: verify PIN");
		verifyEquals(customer.getDynamicText(driver, "Pin"), LoginData.PIN);

		log.info("TC_Step: verify phone");
		verifyEquals(customer.getDynamicText(driver, "Mobile No."), LoginData.MOBI);

	}

	@Test
	public void TC_05_EditCustomer() throws InterruptedException {
		customer = PageFactoryManager.getLoginPageObject(driver);

		log.info("TC_05_Step: Edit Customer");
		customer.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Customer");

		log.info("TC_05_Step: input cusid");
		customer.senkeyDynamicText(driver, CustomerID, "cusid");

		log.info("TC_05_Step: click submit");
		customer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_05_Step: Edit Address");
		customer.senkeyDynamicArea(driver, LoginData.ADDRESS_EDIT, "addr");

		log.info("TC_05_Step: Edit City");
		customer.senkeyDynamicText(driver, LoginData.CITY_EDIT, "city");

		log.info("TC_05_Step: Edit PIN");
		customer.senkeyDynamicText(driver, LoginData.PIN_EDIT, "pinno");

		log.info("TC_05_Step: Edit phone");
		customer.senkeyDynamicText(driver, LoginData.MOBI_EDIT, "telephoneno");

		log.info("TC_05_Step: Click submit");
		customer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "sub");

		log.info("TC_05_Step: verify mesage");
		verifyTrue(customer.isDynamicMessage(driver, objectUIs.Message.EDIT_SUCCESS));

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "Address"), LoginData.ADDRESS_EDIT);

		log.info("TC_Step: verify PIN");
		verifyEquals(customer.getDynamicText(driver, "Pin"), LoginData.PIN_EDIT);

		log.info("TC_Step: verify phone");
		verifyEquals(customer.getDynamicText(driver, "Mobile No."), LoginData.MOBI_EDIT);

		log.info("TC_Step: verify name");
		verifyEquals(customer.getDynamicText(driver, "City"), LoginData.CITY_EDIT);
	}

	@Test
	public void TC_07_addAccount() throws InterruptedException {
		account = PageFactoryManager.getAccountPageObject(driver);

		log.info("TC_Step: Click new account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Account");

		log.info("TC_Step: input customerID");
		account.senkeyDynamicText(driver, CustomerID, "cusid");

		log.info("TC_Step: Chon Account type ");
		account.clickDynamicOption(driver, DynamicUIs.DYNAMIC_OPTION, "Savings");

		log.info("TC_Step: Chon Initial deposit");
		account.senkeyDynamicText(driver, AccountData.INITIAL, "inideposit");

		log.info("TC_Step: Click submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "button2");
	}

	@Test
	public void TC_08_verifyAccount() {
		account = PageFactoryManager.getAccountPageObject(driver);

		log.info("TC_Step: verify mesage");
		verifyTrue(account.isDynamicMessage(driver, objectUIs.Message.ACCOUNT_SUCCESS));

		log.info("TC_Step: get AccountID");
		AccountID = account.getDynamicText(driver, "Account ID");
		System.out.println(AccountID);

		log.info("TC_Step: verify CusID");
		verifyEquals(account.getDynamicText(driver, "Customer ID"), CustomerID);

		log.info("TC_Step: verify account type");
		verifyEquals(account.getDynamicText(driver, "Account Type"), "Savings");

		log.info("TC_Step: verify Current Amount");
		verifyEquals(account.getDynamicText(driver, "Current Amount"), AccountData.INITIAL);

		log.info("TC_Step: chuyen kieu du lieu string -> int bien INITIAL");
		verifyEquals(account.getDynamicText(driver, "Current Amount"), AccountData.INITIAL);
		initial = Integer.parseInt(AccountData.INITIAL);
	}

	@Test
	public void TC_09_editAccount() {
		account = PageFactoryManager.getAccountPageObject(driver);

		log.info("TC_Step: Click edit account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Account");

		log.info("TC_Step: Input accountID");
		account.senkeyDynamicText(driver, AccountID, "accountno");

		log.info("TC_Step: click button submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: Edit Account type la  Current");
		account.clickDynamicOption(driver, DynamicUIs.DYNAMIC_OPTION, "Current");

		log.info("TC_Step: click button submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: verify mesage");
		verifyTrue(account.isDynamicMessage(driver, objectUIs.Message.ACCOUNT_EDIT_SUCCESS));

		log.info("TC_Step: verify account type");
		verifyEquals(account.getDynamicText(driver, "Account Type"), "Current");
	}

	@Test
	public void TC_10_transferMoney() throws InterruptedException {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: Click Deposit");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Deposit");

		log.info("TC_Step: Input accountID");
		deposit.senkeyDynamicText(driver, AccountID, "accountno");

		log.info("TC_Step: Input amount");
		deposit.senkeyDynamicText(driver, AccountData.TRANSFER, "ammount");

		log.info("TC_Step: Input description");
		deposit.senkeyDynamicText(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_Step: click button submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

	}

	@Test
	public void TC_11_verifyTransferMoney() {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: verify amount credited");
		verifyEquals(deposit.getDynamicText(driver, "Amount Credited"), AccountData.TRANSFER);

		log.info("TC_Step: verify type transaction");
		verifyEquals(deposit.getDynamicText(driver, "Type of Transaction"), "Deposit");

		log.info("TC_Step: Chuyen kieu du lieu string -> int Amount");
		transfer = Integer.parseInt(AccountData.TRANSFER);

		log.info("TC_Step: Tinh so tien current Balance");
		currentBalance = transfer + initial;
		String currentBalanceString = Integer.toString(currentBalance);

		log.info("TC_Step: Verify Current Balance");
		verifyEquals(deposit.getDynamicText(driver, "Current Balance"), currentBalanceString);
		currentBalance = Integer.parseInt(currentBalanceString);
	}

	@Test
	public void TC_12_withdrawMoney() {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: Click withdraw");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Withdrawal");

		log.info("TC_Step: Input accountID");
		deposit.senkeyDynamicText(driver, AccountID, "accountno");

		log.info("TC_Step: Input amount");
		deposit.senkeyDynamicText(driver, AccountData.WITHDRAW, "ammount");

		log.info("TC_Step: Input description");
		deposit.senkeyDynamicText(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_Step: click button submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

	}

	@Test
	public void TC_13_verifyTransferMoney() {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: verify amount credited");
		verifyEquals(deposit.getDynamicText(driver, "Amount Debited"), AccountData.WITHDRAW);

		log.info("TC_Step: verify type transaction");
		verifyEquals(deposit.getDynamicText(driver, "Type of Transaction"), "Withdrawal");

		log.info("TC_Step: Chuyen kieu du lieu string -> int Amount");
		withdrawIn = Integer.parseInt(AccountData.WITHDRAW);

		log.info("TC_Step: Tinh so tien current Balance");
		currentBalanceWithdraw = currentBalance - withdrawIn;

		log.info("TC_Step: Chuyen kieu du lieu string -> int Amount");
		String currentBalanceWithdrawString = Integer.toString(currentBalanceWithdraw);

		log.info("TC_Step: Verify mount");
		verifyEquals(deposit.getDynamicText(driver, "Current Balance"), currentBalanceWithdrawString);
		currentBalanceWithdraw = Integer.parseInt(currentBalanceWithdrawString);
	}

	@Test
	public void TC_14_transferOtherAccount() throws InterruptedException {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: Click new account other");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Account");

		log.info("TC_Step: input customerID");
		deposit.senkeyDynamicText(driver, CustomerID, "cusid");

		log.info("TC_Step: Chon Account type ");
		deposit.clickDynamicOption(driver, DynamicUIs.DYNAMIC_OPTION, "Savings");

		log.info("TC_Step: Chon Initial deposit");
		deposit.senkeyDynamicText(driver, AccountData.INITIAL_OTHER, "inideposit");

		log.info("TC_Step: Click submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "button2");

		log.info("TC_Step: get AccountID");
		AccountID_other = deposit.getDynamicText(driver, "Account ID");

		log.info("TC_Step: Fund Transfer");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Fund Transfer");

		log.info("TC_Step: Input accountID");
		deposit.senkeyDynamicText(driver, AccountID, "payersaccount");

		log.info("TC_Step: Input accountID");
		deposit.senkeyDynamicText(driver,AccountID_other, "payeeaccount");

		log.info("TC_Step: Input amount");
		deposit.senkeyDynamicText(driver, AccountData.TRANSFER_OTHER, "ammount");

		log.info("TC_Step: Input description");
		deposit.senkeyDynamicText(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_Step: click button submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");
	}

	@Test
	public void TC_15_verifyTransferOtherAccount() {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_Step: Click Balance Details");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Balance Enquiry");

		log.info("TC_Step: Input accountID");
		deposit.senkeyDynamicText(driver, AccountID, "accountno");
		
		log.info("TC_Step: click button submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: Chuyen kieu du lieu string -> int Amount");
		transferOther = Integer.parseInt(AccountData.TRANSFER_OTHER);

		log.info("TC_Step: Tinh so tien current Balance");
		currentBalanceTransfer = currentBalanceWithdraw - transferOther;

		log.info("TC_Step: Chuyen kieu du lieu string -> int Amount");
		String currentBalanceTransferString = Integer.toString(currentBalanceTransfer);

		log.info("TC_Step: Verify mount");
		verifyEquals(deposit.getDynamicText(driver, "Balance"), currentBalanceTransferString);
		currentBalanceTransfer = Integer.parseInt(currentBalanceTransferString);
	}

	@Test
	public void TC_16_deleteAllAccount() {
		account = PageFactoryManager.getAccountPageObject(driver);

		log.info("TC_Step: Click delete account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Delete Account");

		log.info("TC_Step: input accountID");
		account.senkeyDynamicText(driver, AccountID, "accountno");

		log.info("TC_Step: Click submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: Khai bao bien alert");
		Alert alert = driver.switchTo().alert();

		log.info("TC_Step: Click accept javascript");
		alert.accept();
		alert.accept();

		log.info("TC_Step: Click edit account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Account");

		log.info("TC_Step: Input accountID");
		account.senkeyDynamicText(driver, AccountID, "accountno");

		log.info("TC_Step: click button submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		String notExist = alert.getText();
		verifyEquals(notExist, "Account does not exist");
		alert.accept();

		log.info("TC_Step: Click delete account other");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Delete Account");

		log.info("TC_Step: input accountID");
		account.senkeyDynamicText(driver, AccountID_other, "accountno");

		log.info("TC_Step: Click submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: Khai bao bien alert");

		log.info("TC_Step: Click accept javascript");
		alert.accept();
		alert.accept();

		log.info("TC_Step: Click edit account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Account");

		log.info("TC_Step: Input accountID");
		account.senkeyDynamicText(driver, AccountID_other, "accountno");

		log.info("TC_Step: click button submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		String notExistOther = alert.getText();
		verifyEquals(notExistOther, "Account does not exist");
		alert.accept();
	}

	@Test
	public void TC_17_deleteCustomer() {
		account = PageFactoryManager.getAccountPageObject(driver);

		log.info("TC_Step: Click delete account");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Delete Customer");

		log.info("TC_Step: input customerID");
		account.senkeyDynamicText(driver, CustomerID, "cusid");

		log.info("TC_Step: Click submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		log.info("TC_Step: Khai bao bien alert");
		Alert alert = driver.switchTo().alert();

		log.info("TC_Step: Click accept javascript");
		alert.accept();

		String deleteCustomder = alert.getText();

		log.info("TC_Step: verify text");
		verifyEquals(deleteCustomder, "Customer deleted Successfully");
		alert.accept();

		log.info("TC_Step: Click edit customer");
		account.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Customer");

		log.info("TC_Step: Input accountID");
		account.senkeyDynamicText(driver, CustomerID, "cusid");

		log.info("TC_Step: click button submit");
		account.clickDynamicButton(driver, DynamicUIs.DYNAMIC_TEXT_BUTTON, "AccSubmit");

		String notExistOther = alert.getText();
		
		log.info("TC_Step: verify text");
		verifyEquals(notExistOther, "Customer does not exist!!");
		alert.accept();

	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
	closeBrowserAndriver(driver);
	}

}
