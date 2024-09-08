package testCases;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;

public class TC005_VerifyErrorMessageWhenLoginWithBlankUsernameAndPassword extends BaseClass {

    @Test(dataProvider = "LoginDataWithBlankUsernameAndValidPassword", dataProviderClass = DataProviders.class)
    public void loginTestWithBlankUsernameAndValidPassword(String username, String password) throws InterruptedException {
    	
    	ExtentTest extentTest = ExtentReportManager.getTest();
    	
    	try {

    	LoginPage loginPage = new LoginPage(driver);

        // Enter login credentials
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        
        String errorMessage = loginPage.getErrorMessage();
        
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
        
       // Assert.assertTrue(errorMessage.contains("Username is required"), "Error message for blank username and password is not as expected.");
        
        extentTest.pass("Valid Error Message " + errorMessage + " is dislayed when login with" +  
  			  "blank username and with valid password" + password);
        
    	}
    	
    	catch (Exception e) {
            // Log the exception and capture screenshot
            logger.error("Exception encountered during login test for username: " + username, e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, username + "_Exception");
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Error message verification failed. Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC_005_LoginTestWithBlankUsernameAndValidPassword ******");
        }
}
}
