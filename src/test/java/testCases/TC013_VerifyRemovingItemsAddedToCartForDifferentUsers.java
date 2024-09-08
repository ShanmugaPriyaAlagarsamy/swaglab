package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;

public class TC013_VerifyRemovingItemsAddedToCartForDifferentUsers extends BaseClass {

    @Test(dataProvider = "ImageTestUsers", dataProviderClass = DataProviders.class)
    public void VerifyRemovingItemsAddedToCartForDifferentUsers(String username, String password) {
    	logger.info("***** Starting TC013_VerifyRemoveButtonDisplayedAfterAddingItemsToCartForDifferentUsers for user " + username + " ******");
        ExtentTest extentTest = ExtentReportManager.getTest();

        try {
        	
            // Initialize Page Objects
            LoginPage loginPage = new LoginPage(driver);
            ProductsPage ProductsPage = new ProductsPage(driver);

         // Log into the application
            loginPage.login(username, password);
            extentTest.info("Test User : " + username);
            String expectedUrl = "https://www.saucedemo.com/inventory.html";
            String actualUrl = driver.getCurrentUrl();
            Assert softAssert = null;
			softAssert.assertEquals(actualUrl, expectedUrl, "Login failed! URL mismatch for user: " + username);
			int removeBtnCount = 0;
			
            if (expectedUrl.equals(actualUrl)) {

            // Add all products to the cart
            ProductsPage.addAllProductsToCart();
            ProductsPage.removeAllProductsFromCart();            
            removeBtnCount = ProductsPage.countOfRemoveButtonsDisplayed();
            // Assert that all the items are removed from cart for the current user
            Assert.assertEquals(removeBtnCount, 0, "Unable to remove all items from cart for user "+username);         
            // Log success in Extent Report for this user
            extentTest.pass(username + " is able to remove items added to the cart");
            loginPage.clickLogout();
            driver.navigate().refresh();
            }
            else {
                extentTest.info("Test User :" + username);
                extentTest.fail("Login failed for user  " + username);
                driver.navigate().refresh();
                }

        } catch (Exception e) {
            // Log the exception and capture a screenshot if an error occurs
            logger.error("Exception encountered during 'Verify Remove Button' test for user " + username, e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, "RemoveButton_Exception_" + username);
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report for this user
            extentTest.fail("Error during 'Remove Button' verification for user " + username + ". Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC013_VerifyRemoveButtonDisplayedAfterAddingItemsToCartForDifferentUsers for user " + username + " ******");
        }
    }
}
