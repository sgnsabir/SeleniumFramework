package sleniumframework.testcomponents;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Allure;

public class AllureListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = new ChromeDriver();
		try {
		Allure.addAttachment(UUID.randomUUID().toString(), new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
