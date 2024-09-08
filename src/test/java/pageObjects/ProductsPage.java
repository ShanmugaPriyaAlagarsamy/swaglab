package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductsPage {
	
		 WebDriver driver;

	    // Constructor
	    public ProductsPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    // Page Elements
	    By productTitle = By.xpath("//span[@class='title']"); 
	    By sortDropdown = By.xpath("//select[@class='product_sort_container']");
	    By productTitles = By.xpath("//div[@class='inventory_item_name']");
	    By productPrices = By.className("inventory_item_price");
	    By productImages = By.cssSelector(".inventory_item_img img");
	    By addToCartButtons = By.cssSelector(".btn_inventory"); 
	    By cartItemCount = By.xpath("//span[@class='shopping_cart_badge']");
	    By removeButtons = By.cssSelector(".btn_secondary");
	    By cartButton = By.className("shopping_cart_link");
	    

	    // Page Actions
	    public boolean isProductTitleDisplayed() {
	        return driver.findElement(productTitle).isDisplayed();
	    }

	    public boolean isSortDropdownDisplayed() {
	        return driver.findElement(sortDropdown).isDisplayed();
	    }

	    public void selectSortOption(String value) {
	        WebElement dropdown = driver.findElement(sortDropdown);
	        dropdown.click(); // Click to open the dropdown
	        WebElement option = driver.findElement(By.xpath("//option[@value='" + value + "']"));
	        option.click(); // Select the option
	    }
	    
	    public List<String> getProductTitles() {
	        List<WebElement> productElements = driver.findElements(productTitles);
	        return productElements.stream()
	                              .map(WebElement::getText)
	                              .collect(Collectors.toList());
	    }
	    
	    // Method to get product prices as a list of doubles
	    public List<Double> getProductPrices() {
	        List<WebElement> priceElements = driver.findElements(productPrices);
	        return priceElements.stream()
	                            .map(e -> Double.parseDouble(e.getText().replace("$", "")))  // Remove dollar sign and convert to Double
	                            .collect(Collectors.toList());
	    }
	    
	    // Method to retrieve image URLs for all products
	    public List<String> getProductImageUrls() {
	        List<WebElement> imageElements = driver.findElements(productImages);

	        // Extract the 'src' attribute from each image WebElement
	        return imageElements.stream()
	                            .map(e -> e.getAttribute("src"))  // Get the 'src' attribute of the image
	                            .collect(Collectors.toList());    // Collect as List of Strings (URLs)
	    }
	    // Method to add all products to the cart
	    public void addAllProductsToCart() {
	        List<WebElement> buttons = driver.findElements(addToCartButtons);
	        for (WebElement button : buttons) {
	            button.click();  // Click 'Add to Cart' button for each product
	        }
	    }
	     // Method to get the number of items in the cart
	     public int getCartItemCount() {
	            WebElement cartCountElement = driver.findElement(cartItemCount);
	            return Integer.parseInt(cartCountElement.getText());
	        }
	  // Method to check if 'Remove' buttons are displayed after adding products to cart
	     public int countOfRemoveButtonsDisplayed() {
	         List<WebElement> removeBtns = driver.findElements(removeButtons);
	         return removeBtns.size(); // Return false if any of the Remove buttons are not displayed
	             
	         }
	         
	     public void removeAllProductsFromCart() {
		        List<WebElement> removeBtns = driver.findElements(removeButtons);
		        for (WebElement button : removeBtns) {
		            button.click();  // Click 'Remove' button for each product
		        }
		    }
	  // Example of dynamic locator for product add button
	     public By addProductButton(String productName) {
	         return By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']");
	     }
	  // Method to add a product to the cart by product name
	     public void addProductToCart(String productName) {
	         driver.findElement(addProductButton(productName)).click();
	     }
	  // Method to navigate to the cart
	     public void goToCart() {
	         driver.findElement(cartButton).click();
	     }
	    
	    
	    
	    
	}

	
	
	
	
	
	


