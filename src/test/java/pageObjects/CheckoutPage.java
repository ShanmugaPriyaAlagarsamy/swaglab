package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    WebDriver driver;

    // Constructor to initialize WebDriver
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for elements on the Checkout Information page
    By firstNameField = By.id("first-name");
    By lastNameField = By.id("last-name");
    By postalCodeField = By.id("postal-code");
    By continueButton = By.xpath("//input[@id='continue']");
    By cancelButton = By.id("cancel");
    By errorMessage = By.cssSelector("h3[data-test='error']");

    // Methods to interact with elements

    // Method to enter the first name
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    // Method to enter the last name
    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    // Method to enter the postal code
    public void enterPostalCode(String postalCode) {
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    // Method to click the Continue button to proceed to the overview page
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    // Method to click the Cancel button to go back to the cart
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    // Method to retrieve the error message when fields are left blank or invalid
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    // Method to fill in checkout information
    public void fillInCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickContinue();
    }
}
