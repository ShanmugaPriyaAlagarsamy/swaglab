package testCases;

import java.io.IOException;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportManager;
import utilities.DataProviders;

public class TC001_VerifyLoginFunctionalityWithAcceptedUsernamesAndPassword extends BaseClass {

    @Test(dataProvider = "LoginDataWithAcceptedUsernameAndPassword", dataProviderClass = DataProviders.class)
    public void loginTestWithAcceptedUserNamesAndPassword(String username, String password) throws InterruptedException {

        logger.info("***** Starting TC_001_LoginTestWithAcceptedUserNames ******");
        
      ExtentTest extentTest = ExtentReportManager.getTest();

        SoftAssert softAssert = new SoftAssert();  // Initialize SoftAssert
        
        try {
            LoginPage loginPage = new LoginPage(driver);

            // Enter login credentials
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLogin();

            // Verify if login is successful by checking URL
            String expectedUrl = "https://www.saucedemo.com/inventory.html";
            String actualUrl = driver.getCurrentUrl();
            softAssert.assertEquals(actualUrl, expectedUrl, "Login failed! URL mismatch for user: " + username);
            
			
			
			  if (expectedUrl.equals(actualUrl)) {
			  extentTest.pass("Login is successful for: " + username);
			  extentTest.info("Username:" + username);
			  }
			  
			  else {
				extentTest.fail("Login is unsuccessful for: " + username);
			    extentTest.info("Error message " + loginPage.getErrorMessage());
			  }
			 
			             // Logout if login is successful
            loginPage.clickLogout();

        } catch (Exception e) {
            logger.error("Exception encountered during login test for " + username, e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, username + "_Exception");
                ExtentReportManager.getTest().addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // Capture soft assertion failure in case of exception
            softAssert.fail("Test encountered an exception for user: " + username + " - " + e.getMessage());
            
           // extentTest.fail("Login is unsuccessful for: " + username);
           // extentTest.info(username);
           
                        
        }

        // Refresh the page for the next iteration
        driver.navigate().refresh();
        // Short pause to allow page reload before the next iteration
        Thread.sleep(2000);

        // Assert all to mark the test as failed if any soft assertions failed
        softAssert.assertAll();

        logger.info("***** Finished TC_001_LoginTestWithAcceptedUserNames ******");
    }
}
