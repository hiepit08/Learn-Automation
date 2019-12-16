package guru.payment;

import javax.lang.model.element.Element;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import guru.data.AccountData;
import guru.data.LoginData;
import objectUIs.DynamicUIs;
import pageObjects.NewCustomerPageOject;
import pageObjects.EditCustomerPageObject;
import pageObjects.FundTransferPageObject;
import pageObjects.NewAccountPageObject;
import pageObjects.EditAccountPageOject;
import pageObjects.DeleteAccountPageOject;
import pageObjects.DeleteCustomerPageOject;
import pageObjects.DepositPageObject;
import pageObjects.PageFactoryManager;
import pageObjects.WithdrawalPageObject;
import guru.data.Message;

public class PaymentGuruAccount extends AbstractTest {
	public WebDriver driver;
	private EditCustomerPageObject editCus;
	private NewCustomerPageOject newCus;
	private NewAccountPageObject newAcc;
	private EditAccountPageOject editAcc;
	private DepositPageObject deposit;
	private WithdrawalPageObject withdrawal;
	private FundTransferPageObject fundTransfer;
	private DeleteAccountPageOject deleteAcc;
	private DeleteCustomerPageOject deleteCus;
	private String userID;
	private String pass;
	private String urlCurrent;
	private String email = LoginData.EMAIL + randomNumber() + "@vnpay.vn";
	private String CustomerID;
	private String AccountID;
	private String AccountID_Other;

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
		newCus = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_01_Step: mo browser");
		urlCurrent = newCus.getCurrentURL(driver);
		newCus.openURL(driver, LoginData.HERE_LINK);

		log.info("TC_01_Step: Nhap email");
		newCus.senkeyDynamicToTextAndButton(driver, email, "emailid");

		log.info("TC_01_Step: Click login");
		newCus.clickToElement(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "btnLogin");

		log.info("TC_01_Step: Lay thong tin account");
		userID = newCus.getDynamicText(driver, "User ID :");
		pass = newCus.getDynamicText(driver, "Password :");
		
		newCus.openURL(driver, urlCurrent);

		log.info("TC_01_Step: Nhap account");
		newCus.senkeyDynamicToTextAndButton(driver, userID, "uid");
		newCus.senkeyDynamicToTextAndButton(driver, pass, "password");

		log.info("TC_01_Step: login");
		newCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "btnLogin");
	}

	@Test
	public void TC_02_CreateNewCustomer() throws InterruptedException {
		newCus = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_02_Step: chon button new customer");
		newCus.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Customer");

		log.info("TC_02_Step: Nhap name");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.NAME, "name");

		log.info("TC_02_Step: Nhap ngay sinh");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.BIRTH, "dob");

		log.info("TC_02_Step: Nhap dia chi");
		newCus.senkeyDynamicToArea(driver, LoginData.ADDRESS, "addr");

		log.info("TC_02_Step: Nhap city");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.CITY, "city");

		log.info("TC_02_Step: Nhap state");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.STATE, "state");

		log.info("TC_02_Step: Nhap so dien thoai");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.MOBI, "telephoneno");

		log.info("TC_02_Step: Nhap pin");
		newCus.senkeyDynamicToTextAndButton(driver, LoginData.PIN, "pinno");

		log.info("TC_02_Step: Nhap email");
		newCus.senkeyDynamicToTextAndButton(driver, email, "emailid");

		log.info("TC_02_Step: Nhap pass");
		newCus.senkeyDynamicToTextAndButton(driver, pass, "password");

		log.info("TC_02_Step: submit");
		newCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "sub");

		log.info("TC_02_Step: get cusID");

		CustomerID = newCus.getDynamicText(driver, "Customer ID");

		log.info("TC_02_Step: verify name");
		verifyEquals(newCus.getDynamicText(driver, "Customer Name"), LoginData.NAME);

		log.info("TC_02_Step: verify name");
		verifyEquals(newCus.getDynamicText(driver, "Address"), LoginData.ADDRESS);

		log.info("TC_02_Step: verify name");
		verifyEquals(newCus.getDynamicText(driver, "City"), LoginData.CITY);

		log.info("TC_02_Step: verify name");
		verifyEquals(newCus.getDynamicText(driver, "State"), LoginData.STATE);

		log.info("TC_02_Step: verify PIN");
		verifyEquals(newCus.getDynamicText(driver, "Pin"), LoginData.PIN);

		log.info("TC_02_Step: verify phone");
		verifyEquals(newCus.getDynamicText(driver, "Mobile No."), LoginData.MOBI);
	}

	@Test
	public void TC_03_EditCustomer() throws InterruptedException {
		editCus = PageFactoryManager.getEditCustomerPageObject(driver);

		log.info("TC_03_Step: Edit Customer");
		editCus.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Customer");

		log.info("TC_03_Step: input cusid");
		editCus.senkeyDynamicToTextAndButton(driver, CustomerID, "cusid");

		log.info("TC_03_Step: click submit");
		editCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_03_Step: Edit Address");
		editCus.senkeyDynamicToArea(driver, LoginData.ADDRESS_EDIT, "addr");

		log.info("TC_03_Step: Edit City");
		editCus.senkeyDynamicToTextAndButton(driver, LoginData.CITY_EDIT, "city");

		log.info("TC_03_Step: Edit PIN");
		editCus.senkeyDynamicToTextAndButton(driver, LoginData.PIN_EDIT, "pinno");

		log.info("TC_03_Step: Edit phone");
		editCus.senkeyDynamicToTextAndButton(driver, LoginData.MOBI_EDIT, "telephoneno");

		log.info("TC_03_Step: Click submit");
		editCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "sub");

		log.info("TC_03_Step: verify mesage");
		verifyTrue(editCus.isDynamicMessage(driver, Message.EDIT_SUCCESS));

		log.info("TC_03_Step: verify name");
		verifyEquals(editCus.getDynamicText(driver, "Address"), LoginData.ADDRESS_EDIT);

		log.info("TC_03_Step: verify PIN");
		verifyEquals(editCus.getDynamicText(driver, "Pin"), LoginData.PIN_EDIT);

		log.info("TC_03_Step: verify phone");
		verifyEquals(editCus.getDynamicText(driver, "Mobile No."), LoginData.MOBI_EDIT);

		log.info("TC_03_Step: verify name");
		verifyEquals(editCus.getDynamicText(driver, "City"), LoginData.CITY_EDIT);
	}

	@Test
	public void TC_04_addAccount() throws InterruptedException {
		newAcc = PageFactoryManager.getNewAccountPageObject(driver);

		for (int i=0; i<3; i++)
		{
		log.info("TC_04_Step: Click new account");
		newAcc.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Account");

		log.info("TC_04_Step: input customerID");
		newAcc.senkeyDynamicToTextAndButton(driver, CustomerID, "cusid");

		log.info("TC_04_Step: Chon Account type ");
		newAcc.clickDynamicOption(driver, DynamicUIs.DYNAMIC_OPTION, "Savings");

		log.info("TC_04_Step: Chon Initial deposit");
		newAcc.senkeyDynamicToTextAndButton(driver, AccountData.INITIAL, "inideposit");

		log.info("TC_04_Step: Click submit");
		newAcc.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "button2");
		
		log.info("TC_04_Step: verify mesage");
		verifyTrue(newAcc.isDynamicMessage(driver, Message.ACCOUNT_SUCCESS));

		log.info("TC_04_Step: get AccountID");
		AccountID = newAcc.getDynamicText(driver, "Account ID");

		log.info("TC_04_Step: verify CusID");
		verifyEquals(newAcc.getDynamicText(driver, "Customer ID"), CustomerID);

		log.info("TC_4_Step: verify account type");
		verifyEquals(newAcc.getDynamicText(driver, "Account Type"), "Savings");

		log.info("TC_04_Step: verify Current Amount");
		verifyEquals(newAcc.getDynamicText(driver, "Current Amount"), AccountData.INITIAL);

		log.info("TC_04_Step: chuyen kieu du lieu string -> int bien INITIAL");
		verifyEquals(newAcc.getDynamicText(driver, "Current Amount"), AccountData.INITIAL);
		initial = Integer.parseInt(AccountData.INITIAL);
		}
	}

	@Test
	public void TC_05_editAccount() {
		editAcc = PageFactoryManager.getEditAccountPageOject(driver);

		log.info("TC_05_Step: Click edit account");
		editAcc.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Account");

		log.info("TC_05_Step: Input accountID");
		editAcc.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");

		log.info("TC_05_Step: click button submit");
		editAcc.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_05_Step: Edit Account type la  Current");
		editAcc.clickDynamicOption(driver, DynamicUIs.DYNAMIC_OPTION, "Current");

		log.info("TC_05_Step: click button submit");
		editAcc.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_05_Step: verify mesage");
		verifyTrue(editAcc.isDynamicMessage(driver, Message.ACCOUNT_EDIT_SUCCESS));

		log.info("TC_05_Step: verify account type");
		verifyEquals(editAcc.getDynamicText(driver, "Account Type"), "Current");
	}

	@Test
	public void TC_06_transferMoney() throws InterruptedException {
		deposit = PageFactoryManager.getDepositPageObject(driver);

		log.info("TC_06_Step: Click Deposit");
		deposit.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Deposit");

		log.info("TC_06_Step: Input accountID");
		deposit.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");

		log.info("TC_06_Step: Input amount");
		deposit.senkeyDynamicToTextAndButton(driver, AccountData.TRANSFER, "ammount");

		log.info("TC_06_Step: Input description");
		deposit.senkeyDynamicToTextAndButton(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_06_Step: click button submit");
		deposit.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_06_Step: verify amount credited");
		verifyEquals(deposit.getDynamicText(driver, "Amount Credited"), AccountData.TRANSFER);

		log.info("TC_06_Step: verify type transaction");
		verifyEquals(deposit.getDynamicText(driver, "Type of Transaction"), "Deposit");

		log.info("TC_06_Step: Chuyen kieu du lieu string -> int Amount");
		transfer = Integer.parseInt(AccountData.TRANSFER);

		log.info("TC_06_Step: Tinh so tien current Balance");
		currentBalance = transfer + initial;
		String currentBalanceString = Integer.toString(currentBalance);

		log.info("TC_06_Step: Verify Current Balance");
		verifyEquals(deposit.getDynamicText(driver, "Current Balance"), currentBalanceString);
		currentBalance = Integer.parseInt(currentBalanceString);
	}

	@Test
	public void TC_07_withdrawMoney() {
		withdrawal = PageFactoryManager.getWithdrawalPageObject(driver);

		log.info("TC_07_Step: Click withdraw");
		withdrawal.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Withdrawal");

		log.info("TC_07_Step: Input accountID");
		withdrawal.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");

		log.info("TC_07_Step: Input amount");
		withdrawal.senkeyDynamicToTextAndButton(driver, AccountData.WITHDRAW, "ammount");

		log.info("TC_07_Step: Input description");
		withdrawal.senkeyDynamicToTextAndButton(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_07_Step: click button submit");
		withdrawal.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_07_Step: verify amount credited");
		verifyEquals(withdrawal.getDynamicText(driver, "Amount Debited"), AccountData.WITHDRAW);

		log.info("TC_07_Step: verify type transaction");
		verifyEquals(withdrawal.getDynamicText(driver, "Type of Transaction"), "Withdrawal");

		log.info("TC_07_Step: Chuyen kieu du lieu string -> int Amount");
		withdrawIn = Integer.parseInt(AccountData.WITHDRAW);

		log.info("TC_07_Step: Tinh so tien current Balance");
		currentBalanceWithdraw = currentBalance - withdrawIn;

		log.info("TC_07_Step: Chuyen kieu du lieu string -> int Amount");
		String currentBalanceWithdrawString = Integer.toString(currentBalanceWithdraw);

		log.info("TC_07_Step: Verify mount");
		verifyEquals(withdrawal.getDynamicText(driver, "Current Balance"), currentBalanceWithdrawString);
		currentBalanceWithdraw = Integer.parseInt(currentBalanceWithdrawString);
	}

	@Test
	public void TC_08_transferOtherAccount() throws InterruptedException {
		fundTransfer = PageFactoryManager.getFundTransferPageObject(driver);

		log.info("TC_08_Step: convert AccountID int");		
		int AccountIDInt = Integer.parseInt(AccountID);
		
		log.info("TC_08_Step: get accountID other");	
		int AccountID_otherString = AccountIDInt -1;
		AccountID_Other = Integer.toString(AccountID_otherString);
		
		log.info("TC_08_Step: Fund Transfer");
		fundTransfer.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Fund Transfer");

		log.info("TC_08_Step: Input accountID");
		fundTransfer.senkeyDynamicToTextAndButton(driver, AccountID, "payersaccount");

		log.info("TC_08_Step: Input accountID");
		fundTransfer.senkeyDynamicToTextAndButton(driver,AccountID_Other, "payeeaccount");

		log.info("TC_08_Step: Input amount");
		fundTransfer.senkeyDynamicToTextAndButton(driver, AccountData.TRANSFER_OTHER, "ammount");

		log.info("TC_08_Step: Input description");
		fundTransfer.senkeyDynamicToTextAndButton(driver, AccountData.DESCRIPTION, "desc");

		log.info("TC_08_Step: click button submit");
		fundTransfer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");
	}

	@Test
	public void TC_09_verifyTransferOtherAccount() {
		fundTransfer = PageFactoryManager.getFundTransferPageObject(driver);

		log.info("TC_09_Step: Click Balance Details");
		fundTransfer.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Balance Enquiry");

		log.info("TC_09_Step: Input accountID");
		fundTransfer.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");
		
		log.info("TC_09_Step: click button submit");
		fundTransfer.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_09_Step: Chuyen kieu du lieu string -> int Amount");
		transferOther = Integer.parseInt(AccountData.TRANSFER_OTHER);

		log.info("TC_09_Step: Tinh so tien current Balance");
		currentBalanceTransfer = currentBalanceWithdraw - transferOther;

		log.info("TC_09_Step: Chuyen kieu du lieu string -> int Amount");
		String currentBalanceTransferString = Integer.toString(currentBalanceTransfer);

		log.info("TC_09_Step: Verify mount");
		verifyEquals(fundTransfer.getDynamicText(driver, "Balance"), currentBalanceTransferString);
		currentBalanceTransfer = Integer.parseInt(currentBalanceTransferString);
	}

	@Test
	public void TC_10_deleteAllAccount() {
		deleteAcc = PageFactoryManager.getDeleteAccountPageOject(driver);

		for (int i=0; i<3; i++)
		{
		log.info("TC_10_Step: Click delete account");
		deleteAcc.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Delete Account");

		log.info("TC_010_Step: input accountID");
		deleteAcc.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");

		log.info("TC_10_Step: Click submit");
		deleteAcc.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_10_Step: Khai bao bien alert");
		Alert alert = driver.switchTo().alert();

		log.info("TC_10_Step: Click accept javascript");
		alert.accept();
		alert.accept();

		log.info("TC_10_Step: Click edit account");
		deleteAcc.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Account");

		log.info("TC_10_Step: Input accountID");
		deleteAcc.senkeyDynamicToTextAndButton(driver, AccountID, "accountno");

		log.info("TC_10_Step: click button submit");
		deleteAcc.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		String notExist = alert.getText();
		verifyEquals(notExist, "Account does not exist");
		alert.accept();
		}
	}

	@Test
	public void TC_11_deleteCustomer() {
		deleteCus = PageFactoryManager.getDeleteCustomerPageOject(driver);

		log.info("TC_11_Step: Click delete account");
		deleteCus.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Delete Customer");

		log.info("TC_11_Step: input customerID");
		deleteCus.senkeyDynamicToTextAndButton(driver, CustomerID, "cusid");

		log.info("TC_11_Step: Click submit");
		deleteCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

		log.info("TC_11_Step: Khai bao bien alert");
		Alert alert = driver.switchTo().alert();

		log.info("TC_11_Step: Click accept javascript");
		alert.accept();
		alert.accept();

		log.info("TC_11_Step: Click edit customer");
		deleteCus.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "Edit Customer");

		log.info("TC_11_Step: Input accountID");
		deleteCus.senkeyDynamicToTextAndButton(driver, CustomerID, "cusid");

		log.info("TC_11_Step: click button submit");
		deleteCus.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "AccSubmit");

	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
	closeBrowserAndriver(driver);
	}

}
