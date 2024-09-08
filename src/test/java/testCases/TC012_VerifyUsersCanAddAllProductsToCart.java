package testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;

import com.aventstack.extentreports.ExtentTest;

public class TC012_VerifyUsersCanAddAllProductsToCart extends BaseClass {

    @Test(dataProvider = "ImageTestUsers", dataProviderClass = DataProviders.class)
    public void verifyUsersCanAddAllProductsToCart(String username, String password) {
        ExtentTest extentTest = ExtentReportManager.getTest();

        try {
            // Initialize Page Objects
            LoginPage loginPage = new LoginPage(driver);
            ProductsPage productsPage = new ProductsPage(driver);

            // Log into the application
            loginPage.login(username, password);
            extentTest.info("Test User : " + username);
            String expectedUrl = "https://www.saucedemo.com/inventory.html";
            String actualUrl = driver.getCurrentUrl();
            Assert softAssert = null;
			softAssert.assertEquals(actualUrl, expectedUrl, "Login failed! URL mismatch for user: " + username);
            if (expectedUrl.equals(actualUrl)) {
            // Add all products to the cart
            productsPage.addAllProductsToCart();

            // Get the total number of products added to the cart
            int cartItemCount = productsPage.getCartItemCount();

            // Log the number of products added to the cart
            extentTest.info("Total number of products added to the cart by user " + username + ": " + cartItemCount);

            // Assert that the cart item count matches the total number of products on the page
            int expectedProductCount = driver.findElements(By.className("inventory_item")).size();
            Assert.assertEquals(cartItemCount, expectedProductCount, 
                "The number of products added to the cart does not match the total number of products.");

            // Log success in Extent Report
            extentTest.pass("User " + username + " successfully added all products to the cart.");
            loginPage.clickLogout();
            driver.navigate().refresh();
            }
            else {
                extentTest.info("Test User :" + username);
                extentTest.fail("Login failed for user  " + username);
                driver.navigate().refresh();
                }

        } catch (Exception e) {
            // Log the exception and capture a screenshot
            logger.error("Exception encountered during 'Verify Add to Cart' test for user " + username, e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, "AddToCart_Exception_" + username);
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Error during 'Add to Cart' verification for user " + username + ". Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC012_VerifyUsersCanAddAllProductsToCart for user " + username + " ******");
        }
    }
}
