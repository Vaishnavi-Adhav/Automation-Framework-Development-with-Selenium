package example.screenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class Screenshot {

    public static void captureAndSaveScreenshot(WebDriver driver, String testName) {
        // Convert WebDriver object to TakesScreenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;

        // Capture screenshot as File
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Define the destination file path
        String fileName = generateScreenshotFileName(testName);
        File destinationFile = new File(getScreenshotsDirectoryPath() + File.separator + fileName);

        try {
            // Copy the captured screenshot to the destination file
            FileUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Screenshot taken: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateScreenshotFileName(String testName) {
        // Use a timestamp to make the filename unique
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "screenshot_" + testName + "_" + timestamp + ".png";
    }

    private static String getScreenshotsDirectoryPath() {
        // Define the directory path for storing screenshots
        return "/Users/vaishnaviadhav/Downloads/SeleniumTestCases/src/test/java/example/screenshot/screenshots";
    }


}
