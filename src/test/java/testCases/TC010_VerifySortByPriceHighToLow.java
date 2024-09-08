package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TC010_VerifySortByPriceHighToLow extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifySortByPriceHighToLow(String username,String password) {
    	
    	logger.info("***** Starting TC010_VerifySortByPriceHighToLow ******");
    	
    	
        ExtentTest extentTest = ExtentReportManager.getTest();
            try {
            // Initialize Page Objects
            LoginPage loginPage = new LoginPage(driver);
            ProductsPage productPage = new ProductsPage(driver);
            extentTest.info("Sort Test for user : " + username);

                     
         // Perform login
         			loginPage.enterUsername(username);
         			loginPage.enterPassword(password);
         			loginPage.clickLogin();

            // Select "Sort by Price: High To Low"
            productPage.selectSortOption("hilo");

            // Retrieve product prices
            List<Double> productPrices = productPage.getProductPrices();

            // Sort prices in ascending order to compare
            List<Double> sortedPrices = productPrices.stream()
                                                     .sorted((p1, p2) -> Double.compare(p2, p1)) // Sort in descending order
                                                     .collect(Collectors.toList());

            // Verify the prices are sorted in ascending order (High To Low)
            Assert.assertEquals(productPrices, sortedPrices, "Products are not sorted by price from High To Low for user " + username);

            // Log success in Extent Report
            extentTest.pass("Products are correctly sorted by price from High To Low for user " + username);
            loginPage.clickLogout();

        } catch (Exception e) {
            // Log the exception and capture a screenshot
            logger.error("Exception encountered during 'Sort by Price High To Low' test", e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, "SortByPriceHighToLow_Exception");
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Error during 'Sort by Price High To Low' verification. Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC_010_VerifySortByPriceHighToLow ******");
        }
    }
}
