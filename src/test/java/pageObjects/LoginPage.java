package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	
	//Constructor
	 public LoginPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    // Locators using @FindBy
	    @FindBy(id = "user-name")
	    WebElement username;

	    @FindBy(id = "password")
	    WebElement password;

	    @FindBy(id = "login-button")
	    WebElement loginButton;

	    @FindBy(css = ".error-message-container.error")
	    WebElement errorMessage;

	    @FindBy(id = "react-burger-menu-btn")
	    WebElement menuButton;

	    @FindBy(id = "logout_sidebar_link")
	    WebElement logoutLink;
	    
	    @FindBy(css = "div.error-message-container") 
	    private WebElement errorMessageElement;
	    
	    
//Action methods	   

	    public void enterUsername(String usr) {
	        username.sendKeys(usr);
	    }

	    public void enterPassword(String pwd) {
	        password.sendKeys(pwd);
	    }

	    public void clickLogin() {
	        loginButton.click();
	    }
	    
	    public String getErrorMessage() {
	        return errorMessage.getText();
	    }
	    
	    public void login(String usr, String pwd) {
	    	username.clear();
	    	username.sendKeys(usr);
	    	password.clear();
	    	password.sendKeys(pwd);
	    	loginButton.click();
	    	
	    }

	    public void clickLogout() {
	        menuButton.click(); // Open the side menu
	        logoutLink.click(); // Click the logout link
	    }

	    public WebElement getErrorElement() {
	        return errorMessageElement;
	    }
	}

	
