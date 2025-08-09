package seleniumFramework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Listeners implements ITestListener, IAnnotationTransformer {
    private ExtentTest test;
    private final ExtentReports extent = ExtentReporterNG.getReportObject();
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        try {
            // Get the test instance and cast to BaseTest
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                BaseTest baseTest = (BaseTest) testInstance;
                WebDriver driver = baseTest.driver;

                if (driver != null) {
                    String filePath = baseTest.getScreenshot(result.getMethod().getMethodName(), driver);
                    if (filePath != null) {
                        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
                    }
                }
            }
        } catch (Exception e) {
            extentTest.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    @Override
    public void onStart(ITestContext context) {
        // Initialization if needed
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(Retry.class);
    }
}