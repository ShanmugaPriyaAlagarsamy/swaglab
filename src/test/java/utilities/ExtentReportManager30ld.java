package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager30ld implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report

        sparkReporter.config().setDocumentTitle("Test Automation Report"); // Title of report
        sparkReporter.config().setReportName("Functional Testing"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "SwagLab");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
    }

    public void onTestStart(ITestResult result) {
    	
    	// Get the class name
    	String className = result.getTestClass().getName();
    	
    	// Create a new ExtentTest instance for each test method, including class name
        ExtentTest extentTest = extent.createTest(className);
    	
		/*
		 * // Create a new ExtentTest instance for each test method ExtentTest
		 * ExtentTest extentTest = extent.createTest(className + " _ " + result.getMethod().getMethodName());
		 * extentTest = extent.createTest(result.getMethod().getMethodName());
		 */
        
        test.set(extentTest);  // Assign the test instance to ThreadLocal
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = test.get();
               
        // Log parameters (e.g., username and password)
        Object[] parameters = result.getParameters();
        
        
        // Get test parameters (username, password)
        if (parameters != null && parameters.length == 2) {
        	
        	extentTest.info("Username: " + parameters[0]); // Log username
            extentTest.info("Password: " + parameters[1]); // Log password
        }
        
        extentTest.log(Status.PASS, result.getName() + " got successfully executed" );
        
    }

    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        
        extentTest.log(Status.FAIL, result.getName() + " got failed");
               
		/*
		 * Object[] parameters = result.getParameters(); // Get test parameters
		 * (username, password) if (parameters != null && parameters.length == 2) {
		 * extentTest.info("Username: " + parameters[0]); // Log username
		 * extentTest.info("Password: " + parameters[1]); // Log password }
		 * 
		 * else { // Log that no parameters were provided
		 * extentTest.warning("No parameters were provided for this test case."); }
		 */

        extentTest.log(Status.FAIL, result.getThrowable().getMessage());
        
        try {
            String imgPath = new BaseClass().captureScreen(BaseClass.driver, result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(imgPath);  // Attach screenshot on failure
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        extentTest.log(Status.SKIP, result.getName() + " was skipped");
        extentTest.log(Status.SKIP, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }
        
        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
