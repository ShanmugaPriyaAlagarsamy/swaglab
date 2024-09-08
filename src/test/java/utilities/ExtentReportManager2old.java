package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager2old implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report

        sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
        sparkReporter.config().setReportName("opencart Functional Testing"); // name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getTestClass().getName());
        extentTest.assignCategory(result.getMethod().getGroups()); // to display groups in report
        
        Object[] parameters = result.getParameters(); // Get test parameters (username, password)
        if (parameters != null && parameters.length == 2) {
            extentTest.info("Username: " + parameters[0]); // Log username
            extentTest.info("Password: " + parameters[1]); // Log password
        }
        
        extentTest.log(Status.PASS, result.getName() + " got successfully executed");
        test.set(extentTest);
    }

    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getTestClass().getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        
        extentTest.log(Status.FAIL, result.getName() + " got failed");
        extentTest.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(BaseClass.driver, result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        test.set(extentTest);
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getTestClass().getName());
        extentTest.assignCategory(result.getMethod().getGroups());
        extentTest.log(Status.SKIP, result.getName() + " got skipped");
        extentTest.log(Status.INFO, result.getThrowable().getMessage());
        test.set(extentTest);
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Uncomment and configure email sending code if needed
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report....");
            email.addTo("pavankumar.busyqa@gmail.com"); // Receiver
            email.attach(url, "extent report", "please check report...");
            email.send(); // Send the email
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
