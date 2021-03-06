package guru.valication;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import guru.data.LoginData;
import guru.data.LoginValicationData;
import guru.data.Message;
import objectUIs.DynamicUIs;
import pageObjects.PageFactoryManager;
import pageObjects.NewCustomerPageOject;

@Test
public class CaseValicationCustomer extends AbstractTest {
	public WebDriver driver;

	public String userID;
	public String pass;
	public String urlCurrent;
	private String email = LoginValicationData.EMAIL + randomNumber() + "@vnpay.vn";
	public String CustomerID;
	public String AccountID;
	private NewCustomerPageOject valication;

	@Parameters({ "browser", "version", "url" })
	@BeforeTest
	public void beforeClass(String browserName, String driverVersion, String url) {
		driver = openMultiBrowser(browserName, driverVersion, url);
	}

	@Test
	public void TC_01_getAcc() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_Step: Mo browser");
		urlCurrent = valication.getCurrentURL(driver);
		valication.openURL(driver, LoginData.HERE_LINK);

		log.info("TC_Step: Nhap email");
		valication.senkeyDynamicToTextAndButton(driver, email, "emailid");

		log.info("TC_Step: MClick login");
		valication.clickToElement(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "btnLogin");
		userID = valication.getDynamicText(driver, "User ID :");
		pass = valication.getDynamicText(driver, "Password :");
	}

	@Test
	public void TC_02_Login() {
		log.info("TC_02_Step: Mo URL");
		valication.openURL(driver, urlCurrent);

		log.info("TC__02Step: Nhap account");
		valication.senkeyDynamicToTextAndButton(driver, userID, "uid");
		valication.senkeyDynamicToTextAndButton(driver, pass, "password");
		valication.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "btnLogin");
	}

	@Test
	public void TC_03_invalidName() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);
		valication.clickDynamicPage(driver, DynamicUIs.DYNAMIC_PAGE, "New Customer");

		log.info("TC_03_Step: Name empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.NAME_EMPTY, "name");

		log.info("TC_03_Step: verify text");
		valication.clickToElement(driver, DynamicUIs.DYNAMIC_CLICK);
		verifyEquals(valication.getDynamicError(driver, Message.NAME_EMPTY), "Customer name must not be blank");

		log.info("TC_03_Step: Name cannot be numeric");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.NAME_NUMERIC, "name");

		log.info("TC_03_Step: verify text");
		verifyEquals(valication.getDynamicError(driver, Message.NAME_NUMBER), "Numbers are not allowed");

		log.info("TC_03_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.NAME_SPECIAL, "name");
		verifyEquals(valication.getDynamicError(driver, Message.NAME_SPECIAL), "Special characters are not allowed");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.NAME_SPACE, "name");
		verifyEquals(valication.getDynamicError(driver, Message.NAME_SPACE), "First character can not have space");

	}

	@Test
	public void TC_04_invalidCity() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_04_Step: Name empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.CITY_EMPTY, "city");

		log.info("TC_04_Step: verify text");
		valication.clickToElement(driver, DynamicUIs.DYNAMIC_CLICK);
		verifyEquals(valication.getDynamicError(driver, Message.CITY_EMPTY), "City Field must not be blank");

		log.info("TC_04_Step: Name cannot be numeric");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.CITY_NUMERIC, "city");

		log.info("TC_04_Step: verify text");
		verifyEquals(valication.getDynamicError(driver, Message.CITY_NUMBER), "Numbers are not allowed");

		log.info("TC_04_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.CITY_SPECIAL, "city");
		verifyEquals(valication.getDynamicError(driver, Message.CITY_SPECIAL), "Special characters are not allowed");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.CITY_SPACE, "city");
		verifyEquals(valication.getDynamicError(driver, Message.CITY_SPACE), "First character can not have space");
	}

	@Test
	public void TC_05_invalidState() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_03_Step: Name empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.STATE_EMPTY, "state");

		log.info("TC_03_Step: verify text");
		valication.clickDynamicToLink(driver, DynamicUIs.DYNAMIC_CLICK, "Add New Customer");
		verifyEquals(valication.getDynamicError(driver, Message.STATE_EMPTY), "State must not be blank");

		log.info("TC_03_Step: Name cannot be numeric");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.STATTE_NUMERIC, "state");

		log.info("TC_03_Step: verify text");
		verifyEquals(valication.getDynamicError(driver, Message.STATE_NUMBER), "Numbers are not allowed");

		log.info("TC_03_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.STATE_SPECIAL, "state");
		verifyEquals(valication.getDynamicError(driver, Message.STATE_SPECIAL), "Special characters are not allowed");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.STATE_SPACE, "state");
		verifyEquals(valication.getDynamicError(driver, Message.STATE_SPACE), "First character can not have space");

	}

	@Test
	public void TC_06_invalidPin() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_03_Step: Name empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_EMPTY, "pinno");

		log.info("TC_03_Step: verify text");
		valication.clickDynamicToLink(driver, DynamicUIs.DYNAMIC_CLICK, "Add New Customer");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_EMPTY), "PIN Code must not be blank");

		log.info("TC_03_Step: Name cannot be numeric");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_DIGIT, "pinno");

		log.info("TC_03_Step: verify text");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_DIGIT), "Characters are not allowed");

		log.info("TC_03_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_SPECIAL, "pinno");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_SPECIAL), "Special characters are not allowed");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_SPACE, "pinno");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_SPACE), "First character can not have space");
		
		log.info("TC_03_Step: Name have space after");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_SPACE_AFTER, "pinno");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_SPACE_AFTER), "PIN cannot have space");

		log.info("TC_03_Step: PIN Code must have 6 Digits");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.PIN_LESS_THAN_6, "pinno");
		verifyEquals(valication.getDynamicError(driver, Message.PIN_LESS_THAN_6), "PIN Code must have 6 Digits");

	}

	@Test
	public void TC_07_invalidTelephone() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_03_Step: Name empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.MOBI_EMPTY, "telephoneno");

		log.info("TC_03_Step: verify text");
		valication.clickDynamicToLink(driver, DynamicUIs.DYNAMIC_CLICK, "Add New Customer");
		verifyEquals(valication.getDynamicError(driver, Message.MOBI_EMPTY), "Mobile no must not be blank");

		log.info("TC_03_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.MOBI_SPECIAL, "telephoneno");
		verifyEquals(valication.getDynamicError(driver, Message.MOBI_SPECIAL), "Special characters are not allowed");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.MOBI_SPACE, "telephoneno");
		verifyEquals(valication.getDynamicError(driver, Message.MOBI_SPACE), "First character can not have space");
	}

	@Test
	public void TC_08_invalidEmail() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_Step: Email empty");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.EMAIL_EMPTY, "emailid");

		log.info("TC_03_Step: verify text");
		valication.clickDynamicToLink(driver, DynamicUIs.DYNAMIC_CLICK, "Add New Customer");
		verifyEquals(valication.getDynamicError(driver, Message.EMAIL_EMPTY), "Email-ID must not be blank");

		log.info("TC_03_Step: Name have special");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.EMAIL_INCONNECT_FORMAT, "emailid");
		verifyEquals(valication.getDynamicError(driver, Message.EMAIL_INCONNECT_FORMAT), "Email-ID is not valid");

		log.info("TC_03_Step: Name have space");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.EMAIL_SPACE, "emailid");
		verifyEquals(valication.getDynamicError(driver, Message.EMAIL_SPACE), "First character can not have space");
		
		log.info("TC_03_Step: Name have space after");
		valication.senkeyDynamicToTextAndButton(driver, LoginValicationData.EMAIL_SPACE_AFTER, "emailid");
		verifyEquals(valication.getDynamicError(driver, Message.EMAIL_SPACE_AFTER), "Email-ID is not valid");
	}

	@Test
	public void TC_09_validNewCutomer() throws InterruptedException {
		valication = PageFactoryManager.getNewCustomerPageOject(driver);

		log.info("TC_Step: Input name");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.NAME, "name");

		log.info("TC_Step: Input birth");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.BIRTH, "dob");

		log.info("TC_Step: Input address");
		valication.senkeyDynamicToArea(driver, LoginData.ADDRESS, "addr");

		log.info("TC_Step: Input city");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.CITY, "city");

		log.info("TC_Step: Input state");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.STATE, "state");

		log.info("TC_Step: Input mobi");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.MOBI, "telephoneno");

		log.info("TC_Step: Input pin");
		valication.senkeyDynamicToTextAndButton(driver, LoginData.PIN, "pinno");

		log.info("TC_Step: Input email");
		valication.senkeyDynamicToTextAndButton(driver, email, "emailid");

		log.info("TC_Step: Input pass");
		valication.senkeyDynamicToTextAndButton(driver, pass, "password");

		log.info("TC_Step: Click submit");
		valication.clickDynamicButton(driver, DynamicUIs.DYNAMIC_INPUT_BOX_AND_BUTTON, "sub");
	}


	@AfterTest(alwaysRun = true)
	public void afterTest() {
		closeBrowserAndriver(driver);
	}

}
