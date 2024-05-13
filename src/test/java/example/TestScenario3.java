package example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import example.screenshot.Screenshot;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static example.TestScenario1.driverPath;

public class TestScenario3 {
	private String testName = "Scenario_3";
    private WebDriver driver;

     @BeforeMethod
     public void setUp() {
         // Create an instance of the Firefox profile
         FirefoxProfile profile = new ProfilesIni().getProfile("default");

         // Set preferences to avoid file download dialog
         profile.setPreference("browser.download.folderList", 2);
         profile.setPreference("browser.download.manager.showWhenStarting", false);
         profile.setPreference("browser.download.dir", "~/Downloads");
         profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf"); // MIME type

         // Additional preferences to handle different scenarios
         profile.setPreference("browser.helperApps.neverAsk.openFile", "application/pdf"); // MIME type
         profile.setPreference("pdfjs.disabled", true);  // Disable the built-in PDF viewer
         profile.setPreference("browser.download.useDownloadDir", true);

         // Create FirefoxOptions instance and assign the profile
         FirefoxOptions options = new FirefoxOptions();
         options.setProfile(profile);

         // Pass the options to the WebDriver
         System.setProperty("webdriver.gecko.driver", driverPath);
         driver = new FirefoxDriver(options);
         driver.manage().window().maximize();
     }

    @Test(priority = 2)
    public void testClassroomPDFDownload() throws InterruptedException, AWTException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        driver.get("https://service.northeastern.edu/tech?id=classrooms");

        // Assertion for navigated to the classroom service page
        Screenshot.captureAndSaveScreenshot(driver, testName);
        Assert.assertTrue(driver.getCurrentUrl().contains("service.northeastern.edu/tech?id=classrooms"), "Did not navigate to the classroom service page");


        Screenshot.captureAndSaveScreenshot(driver, testName);
        TimeUnit.SECONDS.sleep(4);
        WebElement classroomLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"x77ea03d9972dd1d8beddb4221153afa6\"]/div/div[2]/span/div/div/div[5]/div/div/div/a")));
        classroomLink.click();

        // Select "NUFlex Auto and Manual Classroom" link beside Quick Guide pdf
        Screenshot.captureAndSaveScreenshot(driver, testName);
        TimeUnit.SECONDS.sleep(4);
        WebElement quickGuideLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"x51d2fa949721d518beddb4221153af23\"]/div/div[2]/span/table[1]/tbody/tr[1]/td[2]/a")));
        quickGuideLink.click();
        Assert.assertTrue(quickGuideLink.isDisplayed(), "Quick Guide link is not visible");

        Screenshot.captureAndSaveScreenshot(driver, testName);
        TimeUnit.SECONDS.sleep(2);
     }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
