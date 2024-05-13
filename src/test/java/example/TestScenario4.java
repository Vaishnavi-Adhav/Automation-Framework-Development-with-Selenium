package example;

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
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static example.TestScenario1.driverPath;

public class TestScenario4 {
	
	private String testName = "Scenario_4";
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}
	
	@Test(priority = 3)
	public void classroomGuide() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		driver.get("https://onesearch.library.northeastern.edu/discovery/search?vid=01NEU_INST:NU&lang=en");
		// Assert that the URL contains the expected path, indicating successful navigation
		Assert.assertTrue(driver.getCurrentUrl().contains("onesearch.library.northeastern.edu/discovery/search"), "Navigation to the OneSearch page was not successful.");


		TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);
        
		WebElement digitalRepoLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a/span[text()='digital repository service']")));
		digitalRepoLink.click();

		for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
		}

		Screenshot.captureAndSaveScreenshot(driver, testName);
		WebElement datasetsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/datasets']")));
		TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);
		datasetsLink.click();

		TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);
		
		WebElement datasetLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/files/neu:ww72br59n']")));
		datasetLink.click();

		TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);
		
		WebElement zipFileLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Zip File']")));
		zipFileLink.click();
		
		TimeUnit.SECONDS.sleep(30);
		Screenshot.captureAndSaveScreenshot(driver, testName);
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
