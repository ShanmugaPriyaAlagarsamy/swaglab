package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.CheckoutPage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import pageObjects.CartPage;
import pageObjects.OverviewPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;

public class TC014_VerifyCheckoutFeatureForDifferentUsers extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifyCheckoutFeatureForDifferentUsers(String username, String password) throws InterruptedException {
        ExtentTest extentTest = ExtentReportManager.getTest();
        SoftAssert softAssert = new SoftAssert();

        try {
            // Initialize Page Objects
            LoginPage loginPage = new LoginPage(driver);
            ProductsPage productsPage = new ProductsPage(driver);
            CartPage cartPage = new CartPage(driver);
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            OverviewPage overviewPage = new OverviewPage(driver);

            // Step 1: Log into the application
            loginPage.login(username, password);
            extentTest.info("Logged in with user: " + username);

            // Step 2: Add a product to the cart
            String productName = "Sauce Labs Backpack"; // Example product
            productsPage.addProductToCart(productName);
            extentTest.info("Added product to cart: " + productName);

            // Step 3: Navigate to the Cart page
            productsPage.goToCart();
            extentTest.info("Navigated to Cart page.");

            // Step 4: Verify product is added in the cart
            softAssert.assertTrue(cartPage.isProductDisplayedInCart(productName), 
                "Product " + productName + " is not displayed in the cart for user " + username);

            // Step 5: Proceed to checkout
            cartPage.clickCheckout();
            extentTest.info("Clicked Checkout button.");

            // Step 6: Enter checkout details
            checkoutPage.fillInCheckoutInfo("John", "Doe", "12345"); // Example details
            extentTest.info("Entered checkout details for user " + username);

            // Step 7: Continue to the overview page
            checkoutPage.clickContinue();
            extentTest.info("Clicked Continue after entering checkout details.");

            // Step 8: Verify product details on the overview page
            softAssert.assertTrue(overviewPage.isProductDisplayedOnOverview(productName), 
                "Product " + productName + " is not displayed on the overview page for user " + username);

            // Step 9: Complete the checkout process
            overviewPage.clickFinish();
            extentTest.info("Clicked Finish to complete the order for user " + username);

            // Step 10: Verify order completion
            softAssert.assertTrue(overviewPage.isOrderConfirmationDisplayed(), 
                "Order confirmation message is not displayed for user " + username);
            extentTest.pass("Order successfully placed for user " + username);

        } catch (Exception e) {
            logger.error("Error encountered during checkout for user " + username, e);
            try {
                // Capture screenshot if an exception occurs
                String imgPath = captureScreen(driver, "CheckoutError_" + username);
                extentTest.fail("Exception during checkout for user " + username + ": " + e.getMessage())
                          .addScreenCaptureFromPath(imgPath);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        } finally {
            softAssert.assertAll();
            logger.info("Finished TC014_VerifyCheckoutFeatureForDifferentUsers for user " + username);
        }
    }
}
