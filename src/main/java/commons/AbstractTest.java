package commons;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AbstractTest {
	private WebDriver driver;
	public final Log log;
	private final String workingDir = System.getProperty("user.dir");

	protected AbstractTest() {
		log = LogFactory.getLog(getClass());
	}

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite
	public void deleteAllFile() {
		System.out.println("-----------START DELETE ALL FILE -------");
		deleteAllFileInFolder();
	}

	public void deleteAllFileInFolder() {
		try {
			String pathFolderDownload = workingDir + "//ReportNGScreenShots";
			File file = new File(pathFolderDownload);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	protected WebDriver openMultiBrowser(String browserName, String driverVersion, String url) {
		if (browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().version(driverVersion).setup();
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, workingDir + "\\FirefoxLog.txt");
			driver = new FirefoxDriver();
		} else if (browserName.equals("firefoxheadless")) {
			WebDriverManager.firefoxdriver().version(driverVersion).setup();
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, workingDir + "\\FirefoxHeadlessLog.txt");

			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
		} else if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().version(driverVersion).setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("chromeheadless")) {
			WebDriverManager.chromedriver().version(driverVersion).setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--log-level=3");
			options.addArguments("--silent");
			options.addArguments("headless");
			options.addArguments("window-size=1366x768");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().arch32().version(driverVersion).setup();
			driver = new InternetExplorerDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().version(driverVersion).setup();
			driver = new InternetExplorerDriver();
		}
		driver.get(url);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		if (driver.toString().toLowerCase().contains("internet explorer")) {
			try {
				Thread.sleep(7000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return driver;

	}

	public static int randomNumber() {

		Random random = new Random();
		int randomNumber = random.nextInt(9999999);
		return randomNumber;

	}

	protected void closeBrowser() {
		driver.quit();
	}

	protected boolean checkPassed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == true)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
			Assert.assertTrue(condition);
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean string) {
		return checkPassed(string);
	}

	protected boolean checkFailed(boolean condition) {
		boolean pass = true;
		try {
			if (condition == false)
				log.info("===PASSED===");
			else
				log.info("===FAILED===");
			Assert.assertFalse(condition);
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyFailure(boolean condition) {
		return checkFailed(condition);
	}

	protected boolean checkEquals(Object actual, Object expected) {
		boolean pass = true;
		boolean status;
		try {
			if (actual instanceof String && expected instanceof String) {
				actual = actual.toString().trim();
				log.info("Actual = " + actual);
				expected = expected.toString().trim();
				log.info("Expected = " + expected);
				status = (actual.equals(expected));
			} else {
				status = (actual == expected);
			}

			log.info("Compare value = " + status);
			if (status) {
				log.info("===PASSED===");
			} else {
				log.info("===FAILED===");
			}
			Assert.assertEquals(actual, expected, "Value is not matching!");
		} catch (Throwable e) {
			;
			pass = false;
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return checkEquals(actual, expected);
	}

	protected void closeBrowserAndriver(WebDriver driver) {
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			log.info("OS Name =" + osName);
			String cmd = "";
			if (driver != null) {
				driver.manage().deleteAllCookies();
				driver.quit();
			}
			if (driver.toString().toLowerCase().contains("chrome")) {
				if (osName.toLowerCase().contains("mac")) {
					cmd = "pkill chromedriver";
				} else if (osName.toLowerCase().contains("window")) {
					cmd = "taskkill  /F /FI \"IMAGENAME eq chromedriver*\"";
				}
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
			if (driver.toString().toLowerCase().contains("internetexplorer")) {
				if (osName.toLowerCase().contains("window")) {
					cmd = "taskkill  /F /FI \"IMAGENAME eq IEDriverServerr*\"";
					Process process = Runtime.getRuntime().exec(cmd);
					process.waitFor();
				}
			}
			log.info("-----------QUIT BROWSER SUCESS------");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static String getCurrentDay() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int day = nowUTC.getDayOfMonth();
		if (day < 10) {
			String day1 = "0" + day;
			return day1;
		} else {
			return day + "";
		}

	}

	public static String getCurrenMonth() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		int month = nowUTC.getMonthOfYear();
		if (month < 10) {
			String month1 = "0" + month;
			return month1;
		} else {
			return month + "";
		}
	}

	public static String getCurrentYear() {
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		return nowUTC.getYear() + "";

	}

	public void turnOffInternet() throws IOException {
		Runtime.getRuntime().exec("cmd /c ipconfig /release");

	}

	public void turnOnInternet() throws IOException {
		Runtime.getRuntime().exec("netsh wlan connect name= network_name");
	}

	public static String getFromDate() {
		int toDate = Integer.parseInt(getCurrentDay());

		String today = getCurrentDay() + "/" + getCurrenMonth() + "/" + getCurrentYear();
		String[] partsOfToday = getTheLastDayOfMonth(today).toString().split("-");
		int lastDayOfThisMonth = Integer.parseInt(partsOfToday[2]);
		int lastMonth = Integer.parseInt(getCurrenMonth()) - 1;
		String lastMonth1 = lastMonth + "";
		String lastMonthDate;
		if ((lastMonth - 1) > 0) {
			lastMonthDate = getCurrentDay() + "/" + lastMonth1 + "/" + getCurrentYear();
		} else {
			lastMonthDate = getCurrentDay() + "/" + "12" + "/" + getCurrentYear();
		}
		String[] partsOfPreviousDate = getTheLastDayOfMonth(lastMonthDate).toString().split("-");
		int lastDayOfPreviousMonth = Integer.parseInt(partsOfPreviousDate[2]);
		int fromDay = 0;
		if (lastDayOfThisMonth >= lastDayOfPreviousMonth) {
			if (toDate <= 30) {
				fromDay = toDate;

			} else {
				fromDay = toDate - 1;
			}
		} else {
			fromDay = toDate + 1;
		}
		String lastMonth2;
		if (lastDayOfPreviousMonth < 10) {
			lastMonth2 = "0" + lastMonth;
		} else {
			lastMonth2 = lastMonth + "";
		}
		String lastDay;
		if (fromDay < 10) {
			lastDay = "0" + fromDay;
		} else {
			lastDay = fromDay + "";
		}
		int currentMonth = Integer.parseInt(getCurrenMonth());
		int curentYear = Integer.parseInt(getCurrentYear());
		String year;
		if (currentMonth == 1) {
			 year = (Integer.parseInt(getCurrentYear()) -1 )+"";
		} else {
			year = curentYear+"";
		}
		return lastDay +"/"+lastMonth2+"/"+year;
	
	}

	public static LocalDate getTheLastDayOfMonth(String date) {
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/M/yyyy"));
		return convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));

	}

}
