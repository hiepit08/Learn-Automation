package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import objectUIs.DynamicUIs;


public class CustomerPageObject extends AbstractPage {
	public CustomerPageObject(WebDriver mappingDriver) {
		driver = mappingDriver;
	}

	private WebDriver driver;
	

	
}
