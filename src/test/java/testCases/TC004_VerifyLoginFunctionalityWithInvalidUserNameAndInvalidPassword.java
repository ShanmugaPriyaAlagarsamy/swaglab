package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;

public class TC004_VerifyLoginFunctionalityWithInvalidUserNameAndInvalidPassword extends BaseClass {
	
	@Test(dataProvider = "LoginDataWithInvalidUsernameAndInvalidPassword", dataProviderClass = DataProviders.class)
	public void testLoginWithInvalidUserNameAndInvalidPassword(String username, String password) throws InterruptedException {

		logger.info("***** Starting TC_004_LoginTestWithInvalidUserNameAndInvalidPassword ******");
		
		 ExtentTest extentTest = ExtentReportManager.getTest();

		SoftAssert softAssert = new SoftAssert();  // Initialize SoftAssert

		try {
			LoginPage loginPage = new LoginPage(driver);

			// Enter login credentials
			loginPage.enterUsername(username);
			loginPage.enterPassword(password);
			loginPage.clickLogin();

			String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
            String actualErrorMessage = loginPage.getErrorMessage();

            softAssert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                    "Error message mismatch for user: " + username);

            // Check that the user is not redirected to the inventory page
            String expectedUrl = "https://www.saucedemo.com/";
            String actualUrl = driver.getCurrentUrl();
            softAssert.assertEquals(actualUrl, expectedUrl, "Unexpected URL for user: " + username);
            
			
			  if (expectedUrl.equals(actualUrl)) {
			  extentTest.pass("Login is unsuccessful for user: " + username +
			  " with Invalid username " + username + " and with Invalid password " +
			  password); }
			 
            

        } catch (Exception e) {
            logger.error("Exception encountered during invalid password test for " + username, e);
            // Capture soft assertion failure in case of exception
            softAssert.fail("Test encountered an exception for user: " + username + " - " + e.getMessage());
        }



		// Refresh the page for next iteration
		driver.navigate().refresh();
		Thread.sleep(2000);  // Short pause to allow page reload before the next iteration

		// Assert all to mark the test as failed if any soft assertions failed
		softAssert.assertAll();

		logger.info("***** Finished TC_004_LoginTestWithInvalidUserNameAndInvalidPassword******");
	}

}
