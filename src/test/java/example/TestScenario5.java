package example;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import example.common.Login;
import example.screenshot.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static example.TestScenario1.*;

public class TestScenario5 {
	private String testName = "Scenario_5";
    private Login login = new Login();
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

	@Test(priority = 4)
	public void academicCalendar() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		driver.get("https://student.me.northeastern.edu/");

        login.login(driver, wait, baseUrl, testName);

        TimeUnit.SECONDS.sleep(4);
		Screenshot.captureAndSaveScreenshot(driver, testName);

        WebElement resourcesTab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
        resourcesTab.click();
        Screenshot.captureAndSaveScreenshot(driver, testName);
		
		WebElement academicsLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("resource-tab-Academics,_Classes_&_Registration")));
        academicsLink.click();
        Screenshot.captureAndSaveScreenshot(driver, testName);
        
        WebElement academicCalendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Academic Calendar")));
        academicCalendarLink.click();
        Screenshot.captureAndSaveScreenshot(driver, testName);
        
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        
        TimeUnit.SECONDS.sleep(4);
        Screenshot.captureAndSaveScreenshot(driver, testName);
        
        WebElement calendarLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='https://registrar.northeastern.edu/article/academic-calendar/']")));
        calendarLink.click();
        
        TimeUnit.SECONDS.sleep(4);
        Screenshot.captureAndSaveScreenshot(driver, testName);
		
        WebElement selectCalendarIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='trumba.spud.6.iframe']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectCalendarIframe);
        driver.switchTo().frame(selectCalendarIframe);
        
        TimeUnit.SECONDS.sleep(4);
        Screenshot.captureAndSaveScreenshot(driver, testName);
		
        WebElement graduateCheckbox = driver.findElement(By.xpath("//input[@id='mixItem1']"));
        graduateCheckbox.click();
        
        TimeUnit.SECONDS.sleep(4);
        Screenshot.captureAndSaveScreenshot(driver, testName);
        
        driver.switchTo().defaultContent();
     
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
       
        WebElement calendarIframe = driver.findElement(By.xpath("//iframe[@id='trumba.spud.2.iframe']"));

        // assertion to verify the presence and visibility of the iframe
        Assert.assertTrue(calendarIframe.isDisplayed(), "Academic calendar iframe is not visible on the page.");

        driver.switchTo().frame(calendarIframe);
        
        Screenshot.captureAndSaveScreenshot(driver, testName);
        
        WebElement addCalendarButton = driver.findElement(By.xpath("//button//span[contains(text(), 'Add to My Calendar')]"));
        if (addCalendarButton.isDisplayed()) {
            System.out.println("Button 'Add to My Calendar' is present and visible!");
        } else {
            System.out.println("Button 'Add to My Calendar' is present but not visible!");
        }
        
        TimeUnit.SECONDS.sleep(4);
	}

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
