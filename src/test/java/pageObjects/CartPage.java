package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    WebDriver driver;

    // Constructor to initialize WebDriver
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for elements on the Cart page
    By checkoutButton = By.id("checkout");
    
    // Dynamic locator for product in the cart by product name
    public By productInCart(String productName) {
        return By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']");
    }

    // Methods to interact with elements

    // Method to verify the product is displayed in the cart
    public boolean isProductDisplayedInCart(String productName) {
        return driver.findElement(productInCart(productName)).isDisplayed();
    }

    // Method to proceed to the checkout
    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}
