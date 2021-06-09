package com.test.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.test.utilities.ExcelReader;
import com.test.utilities.ExtentManager;
import com.test.utilities.Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Page {

	public static WebDriver driver;
	public static CRMTopMenu topMenu;

	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static WebDriverWait wait;
	public static String browser;

	public ExtentReports extent = ExtentManager.getReportObject();
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public static Logger logger = LogManager.getLogger();
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\com\\test\\excel\\testdata.xlsx");

	public Page() {
		if (driver == null) {

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\test\\properties\\config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				logger.debug("Config file loaded");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\test\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				logger.debug("OR file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// To check whether the browser value is selected from Jenkins
			if (System.getenv("browser") != null && (!System.getenv("browser").isEmpty())) {
				browser = System.getenv("browser");// browser value selected from jenkins and assign to browser
			} else {
				browser = config.getProperty("browser");// browser value from config.properties and assign to browser
			}
			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("chrome")) {

				WebDriverManager.chromedriver().setup();
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);

				ChromeOptions options = new ChromeOptions();
				// to reduce the font size
				//options.addArguments("force-device-scale-factor=0.50");
				//options.addArguments("high-dpi-support=0.50");

				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");
				// options.addArguments("--start-maximized");
				driver = new ChromeDriver(options);
				logger.debug("Chrome launched");

			} else if (config.getProperty("browser").equals("firefox")) {

				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("ie")) {

				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			}

			driver.get(config.getProperty("testSiteUrl"));
			logger.debug("Navigated to :" + config.getProperty("testSiteUrl"));

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);

			topMenu = new CRMTopMenu();
		}
	}

	public static void click(String locator) {

		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		logger.debug("Clicking on an element :" + locator);
		extentTest.get().log(Status.INFO, "Clicking on: " + locator);
	}

	public static void clear(String locator) {

		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).clear();
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).clear();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).clear();
		}
	}

	public static void type(String locator, String value) {

		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		logger.debug("Entering value " + value + " in an element :" + locator);
		extentTest.get().log(Status.INFO, "Typing in: " + locator + " and entered value is: " + value);
	}

	static WebElement dropdown;

	public static void select(String locator, String value) {

		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		logger.debug("Selecting value " + value + " from dropdown :" + locator);
		extentTest.get().log(Status.INFO, "Selecting from dropdown: " + locator + " and value is: " + value);

	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public static void verifyEquals(String expected, String actual) throws IOException {
		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {
			Utilities.captureScreenshot();

			// Adding screenshot to ReportNG logs
			Reporter.log("<br>" + "Verification Failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src="
					+ Utilities.screenshotName + " height=100 width=100></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");

			// Adding screenshot to ExtentReports logs
			extentTest.get().log(Status.FAIL, "Verification Failed with Exception: " + t.getMessage());
			extentTest.get().addScreenCaptureFromPath(Utilities.screenshotName);
		}
	}

	public static void quit() {
		driver.quit();
	}

}
