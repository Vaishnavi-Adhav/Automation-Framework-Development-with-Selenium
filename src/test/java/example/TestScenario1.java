package example;

import com.opencsv.CSVReader;
import example.common.Login;
import example.screenshot.Screenshot;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TestScenario1 {
	private WebDriver driver;
	public static String baseUrl = "https://me.northeastern.edu";
	private String testName = "Scenario_1";
	private Login login = new Login();
	public static String driverPath = "/Users/vaishnaviadhav/Downloads/SeleniumTestCases/Selenium/geckodriver";

	public static String[] readDataFromCSV(String filePath) throws IOException {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> credentials = reader.readAll();

			return credentials.get(0);
		}
	}

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Test(priority = 0)
	public void testPortal() throws InterruptedException, IOException {
		driver.get(baseUrl);

		String[] data1 = readDataFromCSV("/Users/vaishnaviadhav/Downloads/SeleniumTestCases/src/test/java/example/data/credentials.csv");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		login.login(driver, wait, baseUrl, testName);

		Screenshot.captureAndSaveScreenshot(driver, testName);
		TimeUnit.SECONDS.sleep(5);
		WebElement resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
		resourcesTab.click();

		Screenshot.captureAndSaveScreenshot(driver, testName);
		TimeUnit.SECONDS.sleep(5);
		WebElement academicsLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("resource-tab-Academics,_Classes_&_Registration")));
		academicsLink.click();

		Screenshot.captureAndSaveScreenshot(driver, testName);
		TimeUnit.SECONDS.sleep(5);
		WebElement myTranscriptsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Transcript")));
		myTranscriptsLink.click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		Screenshot.captureAndSaveScreenshot(driver, testName);
		WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		usernameField.sendKeys(data1[2]);

		WebElement passwordField2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
		passwordField2.sendKeys(data1[1]);
		Screenshot.captureAndSaveScreenshot(driver, testName);

		WebElement loginButton2 = driver.findElement(By.xpath("//button[@class='form-element form-button' and @type='submit' and @name='_eventId_proceed']"));
		loginButton2.click();

		driver.switchTo().frame("duo_iframe");

		WebElement sendPushButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Send Me a Push')]")));
		sendPushButton.click();
		driver.switchTo().defaultContent();

		Screenshot.captureAndSaveScreenshot(driver, testName);
		wait.until(ExpectedConditions.urlToBe("https://bnrordsp.neu.edu/ssb-prod/bwskotrn.P_ViewTermTran"));
		// Confirm navigation to the transcript view page.
		Assert.assertEquals(driver.getCurrentUrl(), "https://bnrordsp.neu.edu/ssb-prod/bwskotrn.P_ViewTermTran", "Did not navigate to the transcript view page");

		WebElement transcriptLevel = wait.until(ExpectedConditions.elementToBeClickable(By.id("levl_id")));

		// Create a Select object from the <select> element
		Select selectLevel = new Select(transcriptLevel);

		// Select the "Graduate" option
		selectLevel.selectByValue("GR");
		// Check if the "Graduate" level is selected.
		Assert.assertEquals(new Select(driver.findElement(By.id("levl_id"))).getFirstSelectedOption().getAttribute("value"), "GR", "Graduate level not correctly selected");


		TimeUnit.SECONDS.sleep(5);
		WebElement transcriptType = driver.findElement(By.id("type_id"));
		Select selectType = new Select(transcriptType);

		// Select the "Audit Transcript" option
		selectType.selectByValue("AUDI");

		Screenshot.captureAndSaveScreenshot(driver, testName);
		TimeUnit.SECONDS.sleep(5);
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
		submitButton.click();

		Screenshot.captureAndSaveScreenshot(driver, testName);
		wait.until(ExpectedConditions.urlToBe("https://bnrordsp.neu.edu/ssb-prod/bwskotrn.P_ViewTran"));

		// Print the page to PDF
		PrintsPage printsPage = (PrintsPage) driver;
		Path printPage = Paths.get("/Users/vaishnaviadhav/Downloads/SeleniumTestCases/src/test/java/example/download/My_Transcript.pdf");
		Pdf print = printsPage.print(new PrintOptions());
		Files.write(printPage, OutputType.BYTES.convertFromBase64Png(print.getContent()));
		Screenshot.captureAndSaveScreenshot(driver, testName);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}


