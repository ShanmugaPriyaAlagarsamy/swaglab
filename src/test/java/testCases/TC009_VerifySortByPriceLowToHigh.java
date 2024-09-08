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

public class TC009_VerifySortByPriceLowToHigh extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifySortByPriceLowToHigh(String username,String password) {
    	
    	logger.info("***** Starting TC009_VerifySortByPriceLowToHigh ******");
    	
    	
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

            // Select "Sort by Price: Low to High"
            productPage.selectSortOption("lohi");

            // Retrieve product prices
            List<Double> productPrices = productPage.getProductPrices();

            // Sort prices in ascending order to compare
            List<Double> sortedPrices = productPrices.stream()
                                                     .sorted() // Sort in ascending order
                                                     .collect(Collectors.toList());

            // Verify the prices are sorted in ascending order (Low to High)
            
            Assert.assertEquals(productPrices, sortedPrices, "Products are not sorted by price from Low to High for " + username);

            // Log success in Extent Report
            extentTest.pass("Products are correctly sorted by price from Low to High for user " + username);
            
            loginPage.clickLogout();

        } catch (Exception e) {
            // Log the exception and capture a screenshot
            logger.error("Exception encountered during 'Sort by Price Low to High' test", e);
            try {
                // Capture screenshot if an exception is thrown
                String imgPath = captureScreen(driver, "SortByPriceLowToHigh_Exception");
                extentTest.addScreenCaptureFromPath(imgPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Log failure in Extent Report
            extentTest.fail("Error during 'Sort by Price Low to High' verification. Exception: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC_009_VerifySortByPriceLowToHigh ******");
        }
    }
}
