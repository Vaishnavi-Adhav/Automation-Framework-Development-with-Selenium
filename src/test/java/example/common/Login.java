package example.common;

import com.opencsv.CSVReader;
import example.screenshot.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Login {
    public void login(WebDriver driver, WebDriverWait wait, String baseUrl, String scenarioName) throws IOException {
        String[] data1 = readDataFromCSV("/Users/vaishnaviadhav/Downloads/SeleniumTestCases/src/test/java/example/data/credentials.csv");
        String testName = scenarioName + "-Login";

        Screenshot.captureAndSaveScreenshot(driver, testName);
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116"))); // input email
        emailField.sendKeys(data1[0]);
        Screenshot.captureAndSaveScreenshot(driver, testName);

        Screenshot.captureAndSaveScreenshot(driver, testName);
        WebElement loginButton = driver.findElement(By.id("idSIButton9")); // login button
        loginButton.click();

        Screenshot.captureAndSaveScreenshot(driver, testName);
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0118")));
        passwordField.sendKeys(data1[1]);
        Screenshot.captureAndSaveScreenshot(driver, testName);

        Screenshot.captureAndSaveScreenshot(driver, testName);
        WebElement signInButton = driver.findElement(By.id("idSIButton9"));
        signInButton.click();

        Screenshot.captureAndSaveScreenshot(driver, testName);
        WebElement trustBrowserButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
        trustBrowserButton.click();

        Screenshot.captureAndSaveScreenshot(driver, testName);
        wait.until(ExpectedConditions.urlToBe("https://login.microsoftonline.com/common/federation/OAuth2ClaimsProvider"));
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        continueButton.click();
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        yesButton.click();
        //  Verify the page navigated away from the base URL post-login.
        Assert.assertNotEquals(driver.getCurrentUrl(), baseUrl, "Still on the base URL after attempting to login");
        Screenshot.captureAndSaveScreenshot(driver, testName);

    }

    public static String[] readDataFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> credentials = reader.readAll();

            return credentials.get(0);
        }

    }
}
