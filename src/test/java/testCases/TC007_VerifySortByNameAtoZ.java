package testCases;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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


public class TC007_VerifySortByNameAtoZ extends BaseClass {
	
	

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void verifySortByNameAtoZ(String username,String password) {
		// Initialize the LoginPage and ProductPage objects
		
		logger.info("***** Starting TC_007_VerifySortByAtoZ ******");

		ExtentTest extentTest = ExtentReportManager.getTest();
		
		try {

			LoginPage loginPage = new LoginPage(driver);
			ProductsPage productPage = new ProductsPage(driver);
			extentTest.info("Sort Test for user : " + username);


			// Perform login
			loginPage.enterUsername(username);
			loginPage.enterPassword(password);
			loginPage.clickLogin();

			// Verify login was successful by checking if the product listing page is displayed
			Assert.assertTrue(productPage.isProductTitleDisplayed(), "Product title is not displayed. Login may have failed.");



			productPage.selectSortOption("az"); // Select "Sort by A to Z"

			// Retrieve and verify product titles are sorted A to Z
			List<String> productTitles = productPage.getProductTitles();
			List<Object> sortedTitles = productTitles.stream()
					.sorted()
					.collect(Collectors.toList());

			Assert.assertEquals(productTitles, sortedTitles, "Product titles are not sorted A to Z for user " + username);    

			extentTest.pass("Products are correctly sorted A to Z.");
			loginPage.clickLogout();
		}

		catch (Exception e) {
			// Log the exception and capture screenshot
			logger.error("Exception encountered when login with username: " + username, e);
			try {
				// Capture screenshot if an exception is thrown
				String imgPath = captureScreen(driver, username + "_Exception");
				extentTest.addScreenCaptureFromPath(imgPath);
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}

			// Log failure in Extent Report
			extentTest.fail("Error during 'Sort by A to Z' verification. Exception: " + e.getMessage());
		} 

		finally {
			logger.info("***** Finished TC_007_VerifySortByAtoZ ******");
		}

	}
}
