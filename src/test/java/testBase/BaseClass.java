package testBase;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;  //Log4j
	public Properties p;
	
	@BeforeClass
	@Parameters("browser")
	public void setup(String br) throws IOException {
		
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		
		switch (br.toLowerCase()) { 
		    case "chrome":
		        driver = new ChromeDriver(); 
		        break;
		    case "edge":
		        driver = new EdgeDriver();
		        break; 
		    case "firefox":
		        driver = new FirefoxDriver(); 
		        break; 
		    default: 
		        System.out.println("No matching browser"); 
		        return; 
		}
		  
		driver.get(p.getProperty("appURL"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();		
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	// Updated captureScreen method to take screenshots in case of failures
	public String captureScreen(WebDriver driver, String testName) throws IOException {
	  
	  String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	  
	  TakesScreenshot takesScreenshot = (TakesScreenshot) driver; 
	  File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	  
	  String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp + ".png"; 
	  File targetFile = new File(targetFilePath);
	  
	  FileUtils.copyFile(sourceFile, targetFile);  // Save screenshot to file
	  
	  return targetFilePath;  // Return the file path of the screenshot
	}
}
