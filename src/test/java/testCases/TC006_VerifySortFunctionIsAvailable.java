package testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;


public class TC006_VerifySortFunctionIsAvailable extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifySortFunctionIsAvailable(String username, String password) {
        // Initialize the LoginPage and ProductPage objects
    	
ExtentTest extentTest = ExtentReportManager.getTest();

   	
    	try {
    	
    	LoginPage loginPage = new LoginPage(driver);
        ProductsPage productPage = new ProductsPage(driver);
        extentTest.info("Test for user : " + username);
        // Perform login
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        // Verify login was successful by checking if the product listing page is displayed
        Assert.assertTrue(productPage.isProductTitleDisplayed(), "Product title is not displayed. Login may have failed.");

        // Check for the sort option dropdown
        Assert.assertTrue(productPage.isSortDropdownDisplayed(), "Sort option is not available for "+ username);
        
        extentTest.pass("Sort feature is available in the products page for " + username);
        loginPage.clickLogout();
            	}
    	
    	catch (Exception e) {
            // Log the exception and capture screenshot
            logger.error("Exception encountered when login with username: " + username, e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, username + "_Exception");
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Sort option is not available Exception: " + e.getMessage());
        }
    	
    	finally {
            logger.info("***** Finished TC_006_VerifySortOptionForSuccesfulUser******");
        }
    
    }
}
