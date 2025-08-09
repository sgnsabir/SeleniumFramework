package seleniumFramework.tests;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import seleniumFramework.pageobjects.SignupLoginPage;
import seleniumFramework.utils.BaseTest;

public class EndToEndLoginSignup extends BaseTest {
	private SignupLoginPage signupLogin;
	String email = "automationtester0001@gmail.com";
	String password = "Test@143";
	String username = "Test Engineer";
	//Test Case 1: Register User
	@Test(priority = 2)
	public void signupFlow() {
		String signuptitle = landingPage.signupLogin();
		AssertJUnit.assertEquals(signuptitle, "Automation Exercise - Signup / Login");
		signupLogin = new SignupLoginPage(driver);
		signupLogin.signupName(username);
		signupLogin.signupEmail(email);
		signupLogin.signupButton();
		signupLogin.selectMaleGender();
		signupLogin.signupPassword(password);
		signupLogin.daySelectionFromList(13);
		signupLogin.monthSelctionFromList(9);
		signupLogin.yearSelectionFromList(1987);
		signupLogin.newsletterCheckbox();
		signupLogin.offerCheckbox();
		signupLogin.firstNameField("Test");
		signupLogin.lastNameField("Engineer");
		signupLogin.companyNameField("Test Automation Lab");
		signupLogin.address1Field("Fjellveien 33");
		signupLogin.address2Field("Narvik 8505");
		signupLogin.countrySelection("New Zealand");
		signupLogin.stateField("Nordland");
		signupLogin.cityField("Narvik");
		signupLogin.zipcodeField("8515");
		signupLogin.mobileNumberField("12345678");
		signupLogin.createAccountButton();	
		String actualText = signupLogin.accountCreatedConfirmation();
		AssertJUnit.assertEquals(actualText, "ACCOUNT CREATED!");
		signupLogin.continueButton();
		//Test Case 4: Logout User
		String pageTitle = signupLogin.logout();
		AssertJUnit.assertEquals(pageTitle, "Automation Exercise - Signup / Login");		
		//Assert.assertEquals(confirmText, "ACCOUNT DELETED!"); -> at last
	}
	//Test Case 2: Login User with correct email and password
	@Test(priority = 3)
	public void validLogin() {
		landingPage.signupLogin();
		signupLogin = new SignupLoginPage(driver);
		signupLogin.loginEmail(email);
		signupLogin.loginEmailPassword(password);
		signupLogin.loginButton();
		String actualName = signupLogin.getUsername();
		AssertJUnit.assertEquals(actualName, username);
		signupLogin.logout();
	}
	//Test Case 3: Login User with incorrect email and password
	@Test(priority = 4)
	public void invalidLogin() {
		landingPage.signupLogin();
		signupLogin = new SignupLoginPage(driver);
		signupLogin.loginEmail(email);
		signupLogin.loginEmailPassword("invalidPassword");
		signupLogin.loginButton();
		String errorText = signupLogin.invalidLogin();
		AssertJUnit.assertEquals(errorText, "Your email or password is incorrect!");
	}
	//Test Case 5: Register User with existing email
	@Test(priority = 5)
	public void registerWithExistingEmail() {
		landingPage.signupLogin();
		signupLogin = new SignupLoginPage(driver);
		signupLogin.signupName(username);
		signupLogin.signupEmail("automationtester0000@gmail.com");
		signupLogin.signupButton();
		String actualError = signupLogin.alreadyExisEmailMsg();
		AssertJUnit.assertEquals(actualError, "Email Address already exist!");		
	}
	//Delete Account
	@Test(priority = 6)
	public void deleteAccount() {
		landingPage.signupLogin();
		signupLogin = new SignupLoginPage(driver);
		signupLogin.loginEmail(email);
		signupLogin.loginEmailPassword(password);
		signupLogin.loginButton();
		signupLogin.deleteAccount();
		String deleteConfirmMsg = signupLogin.accountDeletedConfirmation();
		AssertJUnit.assertEquals(deleteConfirmMsg, "ACCOUNT DELETED!");		
	}
	
	
}
