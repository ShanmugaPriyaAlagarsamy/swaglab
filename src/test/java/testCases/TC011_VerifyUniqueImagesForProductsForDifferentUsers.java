package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TC011_VerifyUniqueImagesForProductsForDifferentUsers extends BaseClass {

    @Test(dataProvider = "ImageTestUsers", dataProviderClass = DataProviders.class)
    public void verifyUniqueImagesForProducts(String username, String password) {
    	
    	logger.info("***** Starting TC011_VerifyUniqueImagesForProductsForDifferentUsers ******");
        ExtentTest extentTest = ExtentReportManager.getTest();
        SoftAssert softAssert = new SoftAssert();  // Initialize SoftAssert
        try {
            // Initialize Page Objects
            LoginPage loginPage = new LoginPage(driver);
            ProductsPage productsPage = new ProductsPage(driver);

            //Perform Login
            loginPage.enterUsername(username);
 			loginPage.enterPassword(password);
 			loginPage.clickLogin();
 			
 			// Verify if login is successful by checking URL
            String expectedUrl = "https://www.saucedemo.com/inventory.html";
            String actualUrl = driver.getCurrentUrl();
            softAssert.assertEquals(actualUrl, expectedUrl, "Login failed for user: " + username);
            if (expectedUrl.equals(actualUrl)) {
            	extentTest.info("Test User : " + username);
            // Retrieve product image URLs
            List<String> productImageUrls = productsPage.getProductImageUrls();

            // Log the number of products
            extentTest.info("Total number of products: " + productImageUrls.size());

            // Convert List to Set to remove duplicates (Sets do not allow duplicates)
            Set<String> uniqueImageUrls = new HashSet<>(productImageUrls);

            // Assert that the number of unique image URLs matches the number of products
            Assert.assertEquals(uniqueImageUrls.size(), productImageUrls.size(),
                    "Duplicate product images found! Some products share the same image for user '" + username +"'");

            // Log success in Extent Report
            extentTest.pass("All products have unique images. Test is successfully executed for user '" + username +"'");
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
            logger.error("Exception encountered during 'Verify Unique Images' test", e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, "VerifyUniqueImages_Exception");
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Error during 'Verify Unique Images' verification. Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC011_VerifyUniqueImagesForProducts ******");
        }
    }
}
