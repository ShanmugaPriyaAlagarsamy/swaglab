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

public class ExtentReportManager implements ITestListener {
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
    	
    	
    	String className = result.getTestClass().getName();
    	ExtentTest extentTest = extent.createTest(className);
        // Create a new ExtentTest instance for each test method
       // ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);  // Assign the test instance to ThreadLocal
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, result.getName() + " got completed");
        } else {
            System.err.println("ExtentTest instance is null");
        }
    }

    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.FAIL, result.getName() + " got failed");
            extentTest.log(Status.FAIL, result.getThrowable().getMessage());

            try {
                String imgPath = new BaseClass().captureScreen(BaseClass.driver, result.getMethod().getMethodName());
                extentTest.addScreenCaptureFromPath(imgPath);  // Attach screenshot on failure
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("ExtentTest instance is null");
        }
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.SKIP, result.getName() + " was skipped");
            extentTest.log(Status.SKIP, result.getThrowable().getMessage());
        } else {
            System.err.println("ExtentTest instance is null");
        }
    }

    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
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
