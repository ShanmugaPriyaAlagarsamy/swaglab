package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverviewPage {

    WebDriver driver;

    // Constructor to initialize WebDriver
    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for elements on the Overview page
    By finishButton = By.id("finish");
    By orderConfirmationMessage = By.className("complete-header");

    // Dynamic locator for product on the Overview page by product name
    public By productOnOverview(String productName) {
        return By.xpath("//div[text()='" + productName + "']");
    }

    // Methods to interact with elements

    // Method to verify the product is displayed on the overview page
    public boolean isProductDisplayedOnOverview(String productName) {
        return driver.findElement(productOnOverview(productName)).isDisplayed();
    }

    // Method to click the Finish button to complete the order
    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    // Method to verify if the order confirmation message is displayed
    public boolean isOrderConfirmationDisplayed() {
        return driver.findElement(orderConfirmationMessage).isDisplayed();
    }
}
