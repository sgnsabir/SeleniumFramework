package sleniumframework.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import sleniumframework.pageobjects.ContactUsPage;
import sleniumframework.testcomponents.BaseTest;

public class EndToEndTest extends BaseTest {
	String filePath = System.getProperty("user.dir") + "\\test_file.pdf";
	String email = "automationtester0001@gmail.com";
	String email2 = "automationtester0000@gmail.com";
	String password = "Test@143";
	String username = "Test Engineer";

	@Test(priority = 17)
	public void registerPage() {
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		driver.findElement(By.name("name")).sendKeys(username);
		driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(email);
		driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
		driver.findElement(By.id("id_gender1")).click();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("days")).click();
		driver.findElement(By.cssSelector("select[id='days'] option[value='13']")).click();
		driver.findElement(By.id("months")).click();
		driver.findElement(By.cssSelector("select[id='months'] option[value='9']")).click();
		// year select from list
		WebElement dropDown = driver.findElement(By.cssSelector("select[id='years'] option[value='1987']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropDown);
		dropDown.click();

		WebElement address = driver.findElement(By.id("address1"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", address);
		driver.findElement(By.id("newsletter")).click();
		driver.findElement(By.id("optin")).click();
		driver.findElement(By.id("first_name")).sendKeys("Test");
		driver.findElement(By.id("last_name")).sendKeys("Engineer");
		driver.findElement(By.id("company")).sendKeys("Self Motivation");
		address.sendKeys("Fjellveien 33 Narvik, Norway");
		WebElement createAccount = driver.findElement(By.cssSelector("[data-qa='create-account']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createAccount);
		driver.findElement(By.id("country")).click();
		driver.findElement(By.cssSelector("select[id='country'] option[value='New Zealand']")).click();
		driver.findElement(By.id("state")).sendKeys("Auckland");
		driver.findElement(By.id("city")).sendKeys("Narvik");
		driver.findElement(By.id("zipcode")).sendKeys("8515");
		driver.findElement(By.id("mobile_number")).sendKeys("46469944");
		createAccount.click();
		String actualText = driver.findElement(By.cssSelector("h2[data-qa='account-created'] b")).getText();
		AssertJUnit.assertEquals(actualText, "ACCOUNT CREATED!");
		driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();

		driver.findElement(By.cssSelector("[href*='delete_account']")).click();
		String confirmText = driver.findElement(By.cssSelector("h2[data-qa='account-deleted'] b")).getText();
		AssertJUnit.assertEquals(confirmText, "ACCOUNT DELETED!");
		driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();
	}

	@Test(priority = 18)
	public void validLogin() {
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email2);
		driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
		driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
		String actualName = driver.findElement(By.cssSelector("li a b")).getText();
		AssertJUnit.assertEquals(actualName, username);
		driver.findElement(By.cssSelector("[href='/logout']")).click();
		driver.findElement(By.cssSelector(".login-form h2")).isDisplayed();
	}

	@Test(priority = 19)
	public void invalidLogin() {
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email);
		driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys("123432");
		driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
		String errorText = driver.findElement(By.cssSelector("p[style*='color']")).getText();
		AssertJUnit.assertEquals(errorText, "Your email or password is incorrect!");
	}

	@Test(priority = 20)
	public void logoutUser() {
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		driver.findElement(By.cssSelector(".login-form h2")).isDisplayed();
		driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email2);
		driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
		driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
		driver.findElement(By.cssSelector("[href='/logout']")).click();
		driver.findElement(By.cssSelector(".login-form h2")).isDisplayed();
	}

	@Test(priority = 21)
	public void registerWithExistingEmail() {
		driver.findElement(By.cssSelector("a[href*='login']")).click();
		driver.findElement(By.name("name")).sendKeys(username);
		driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(email2);
		driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
		String actualError = driver.findElement(By.cssSelector("p[style*='color']")).getText();
		AssertJUnit.assertEquals(actualError, "Email Address already exist!");
	}
	@Test(priority = 22)
	public void contactUsForm() {
		String contactUsTitle = landingPage.contactUs();
		AssertJUnit.assertEquals(contactUsTitle, "Automation Exercise - Contact Us");
		ContactUsPage contactUs = new ContactUsPage(driver);
		String contactusText = contactUs.contactUsTitle();
		AssertJUnit.assertEquals(contactusText, "CONTACT US");
		String getInTouch = contactUs.getInTouchTitle();
		AssertJUnit.assertEquals(getInTouch, "GET IN TOUCH");
		String feedbackTitle = contactUs.feedbackForUsText();
		AssertJUnit.assertEquals(feedbackTitle, "FEEDBACK FOR US");
		String feedbackEmail = contactUs.contactEmail();
		AssertJUnit.assertEquals(feedbackEmail, "feedback@automationexercise.com");
		contactUs.nameField("Test Engineer");
		contactUs.emailField(email);
		contactUs.subjectField("Thank you for your help!");
		contactUs.messageTextField("Feedback to say thank you, 'Choose File' button is linked in whole div area, unable to validate file uploaded or not.");
		contactUs.uploadFile(filePath);
		contactUs.submitForm();
		contactUs.javascriptAlert();
		String successMsg = contactUs.successMessage();
		AssertJUnit.assertEquals(successMsg, "Success! Your details have been submitted successfully.");
		contactUs.successButton();
	}

}
