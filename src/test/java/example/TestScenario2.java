package example;

import com.opencsv.CSVReader;
import example.common.Login;
import example.screenshot.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static example.TestScenario1.baseUrl;
import static example.TestScenario1.driverPath;

public class TestScenario2 {
	 
	private String testName = "Scenario_2";
	private Login login = new Login();
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
 
	@Test(priority = 1, dataProvider = "todolist")
	public void testCanvas(String title, String date, String time, String details)
			throws InterruptedException, IOException {
		Screenshot.captureAndSaveScreenshot(driver, testName);
		driver.get("https://northeastern.instructure.com/");
		Screenshot.captureAndSaveScreenshot(driver, testName);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		login.login(driver, wait, baseUrl, testName);

		// Open Calendar
		Screenshot.captureAndSaveScreenshot(driver, testName);
		WebElement calendarLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/calendar']")));
		Screenshot.captureAndSaveScreenshot(driver, testName);
		calendarLink.click();

		// Assert current URL contains '/calendar' to ensure we're on the calendar page
		Assert.assertTrue(driver.getCurrentUrl().contains("/calendar"), "Navigation to the calendar page failed.");


		// Click on the Add Button
		Screenshot.captureAndSaveScreenshot(driver, testName);
		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Create New Event']")));
		Screenshot.captureAndSaveScreenshot(driver, testName);
		addButton.click();
 
		// go to My To Do
		Screenshot.captureAndSaveScreenshot(driver, testName);
		WebElement myTodo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='ui-id-5']")));
		Screenshot.captureAndSaveScreenshot(driver, testName);
		myTodo.click();
 
		// title field
		WebElement titleField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='planner_note_title']")));
		titleField.sendKeys(title);
		Screenshot.captureAndSaveScreenshot(driver, testName);
 
		// date field
		WebElement dateField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='planner_note_date']")));
		dateField.clear();
		dateField.sendKeys(date);
		Screenshot.captureAndSaveScreenshot(driver, testName);
 
		// time field
		WebElement timeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='planner_note_time']")));
		timeField.clear();
		timeField.sendKeys(time);
		Screenshot.captureAndSaveScreenshot(driver, testName);
 
		// details field
		WebElement detailsField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='details_textarea']")));
		detailsField.sendKeys(details);
		Screenshot.captureAndSaveScreenshot(driver, testName);

		//  assertion to ensure the details are correctly inputted:
		Assert.assertEquals(detailsField.getAttribute("value"), details, "Details field text does not match expected input.");


		TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);
 
		WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
		submitButton.click();
 
	}
 
	@DataProvider(name = "todolist")
	public String[][] userData() throws IOException {
		String[][] toDoData;
 
		try (CSVReader reader = new CSVReader(new FileReader(
				"/Users/vaishnaviadhav/Downloads/SeleniumTestCases/src/test/java/example/data/todolist.csv"))) {
			List<String[]> data = reader.readAll();
			toDoData = new String[data.size()][];
			for (int i = 0; i < data.size(); i++) {
				toDoData[i] = data.get(i);
			}
			
		}
		
		return toDoData;
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
 
 