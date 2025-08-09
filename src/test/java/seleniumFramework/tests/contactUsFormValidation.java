package seleniumFramework.tests;

import java.awt.AWTException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import seleniumFramework.pageobjects.ContactUsPage;
import seleniumFramework.utils.BaseTest;

public class contactUsFormValidation extends BaseTest{
	
	String email = "automationtester0001@gmail.com";
	String filePath = System.getProperty("user.dir") + "\\test_file.pdf";
	//Test Case 6: Contact Us Form
	@Test(priority = 1)
	public void validateContactUsForm() throws AWTException, InterruptedException {
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
