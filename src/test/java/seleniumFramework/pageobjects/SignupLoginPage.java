package seleniumFramework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class SignupLoginPage extends AbstractComponent {
	private WebDriver driver;

	public SignupLoginPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//Find all locators of Home page like Menu Tree execution	
	@FindBy(css = "[data-qa*='login-email']")
	private WebElement loginEmail;
	@FindBy(css = "[data-qa*='login-password']")
	private WebElement loginEmailPassword;
	@FindBy(css = "[data-qa*='login-button']")
	private WebElement loginButton;

	@FindBy(name = "name")
	private WebElement signupName;

	@FindBy(css = "[data-qa*='signup-email']")
	private WebElement signupEmail;
	@FindBy(css = "[data-qa*='signup-button']")
	private WebElement signupButton;

	@FindBy(id = "uniform-id_gender1")
	private WebElement selectMaleGender;
	@FindBy(id = "uniform-id_gender2")
	private WebElement selectFemaleGender;
	@FindBy(id = "password")
	private WebElement signupPassword;

	// Day and Year are covered in function by implementing autoscroll
	@FindBy(id = "days")
	private WebElement daysSelectionField;
	@FindBy(id = "months")
	private WebElement monthsSelectionField;
	@FindBy(id = "years")
	private WebElement yearsSelectionField;

	@FindBy(id = "newsletter")
	private WebElement newsletterCheckbox;
	@FindBy(id = "optin")
	private WebElement offerCheckbox;
	@FindBy(id = "first_name")
	private WebElement firstNameField;
	@FindBy(id = "last_name")
	private WebElement lastNameField;
	@FindBy(id = "company")
	private WebElement companyNameField;
	@FindBy(id = "address1")
	private WebElement address1Field;
	@FindBy(id = "address2")
	private WebElement address2Field;
	@FindBy(id = "country")
	private WebElement countrySelection;
	@FindBy(id = "state")
	private WebElement stateField;
	@FindBy(id = "city")
	private WebElement cityField;
	@FindBy(id = "zipcode")
	private WebElement zipcodeField;
	@FindBy(id = "mobile_number")
	private WebElement mobileNumberField;
	@FindBy(css = "[data-qa='create-account']")
	private WebElement createAccountButton;
	@FindBy(css = "h2[data-qa='account-created'] b")
	private WebElement accountCreatedConfirmation;
	@FindBy(css = "a[data-qa='continue-button']")
	private WebElement continueButton;
	@FindBy(css = "li a b")
	private WebElement userName;
	@FindBy(css = "[href='/logout']")
	private WebElement logout;
	@FindBy(css = "[href='/delete_account']")
	private WebElement deleteAccount;
	@FindBy(css = "h2[data-qa='account-deleted'] b")
	private WebElement accountDeletedConfirmation;	
	@FindBy(css= "p[style*='color']")
	private WebElement invalidLogin;
	@FindBy(css="p[style*='color']")
	private WebElement alreadyExisEmailMsg;
	
	public void daySelectionFromList(int dayDate) {
		WebElement dropDown = this.driver
				.findElement(By.cssSelector("select[id='days'] option[value='" + dayDate + "']"));
		((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", dropDown);
		dropDown.click();
	}

	public void monthSelctionFromList(int monthNumber) {
		monthsSelectionField.click();
		this.driver.findElement(By.cssSelector("select[id='months'] option[value='" + monthNumber + "']")).click();
	}

	public void yearSelectionFromList(int yearDate) {
		WebElement dropDown = this.driver
				.findElement(By.cssSelector("select[id='years'] option[value='" + yearDate + "']"));
		((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", dropDown);
		dropDown.click();
	}

	public void loginEmail(String email) {
		loginEmail.sendKeys(email);
	}

	public void loginEmailPassword(String password) {
		loginEmailPassword.sendKeys(password);
	}

	public void loginButton() {
		loginButton.click();
	}

	public void signupName(String name) {
		signupName.sendKeys(name);
	}

	public void signupEmail(String email) {
		signupEmail.sendKeys(email);
	}

	public void signupButton() {
		signupButton.click();
	}

	public void selectMaleGender() {
		selectMaleGender.click();
	}

	public void selectFemaleGender() {
		selectFemaleGender.click();
	}

	public void signupPassword(String password) {
		signupPassword.sendKeys(password);
	}

	public void newsletterCheckbox() {
		newsletterCheckbox.click();
	}

	public void offerCheckbox() {
		offerCheckbox.click();
	}

	public void firstNameField(String firstName) {
		firstNameField.sendKeys(firstName);
	}

	public void lastNameField(String lastName) {
		lastNameField.sendKeys(lastName);
	}

	public void companyNameField(String companyName) {
		companyNameField.sendKeys(companyName);
	}

	public void address1Field(String address1) {
		address1Field.sendKeys(address1);
	}

	public void address2Field(String address2) {
		address2Field.sendKeys(address2);
	}

	public void countrySelection(String countryName) {
		countrySelection.click();
		this.driver.findElement(By.cssSelector("select[id='country'] option[value='" + countryName + "']")).click();
	}

	public void stateField(String stateName) {
		stateField.sendKeys(stateName);
	}

	public void cityField(String cityName) {
		cityField.sendKeys(cityName);
	}

	public void zipcodeField(String zipCode) {
		zipcodeField.sendKeys(zipCode);
	}

	public void mobileNumberField(String mobileNumber) {
		mobileNumberField.sendKeys(mobileNumber);
	}

	public void createAccountButton() {
		createAccountButton.click();
	}

	public String accountCreatedConfirmation() {
		return accountCreatedConfirmation.getText();
	}

	public String continueButton() {
		continueButton.click();
		return this.driver.getTitle();
	}

	public String logout() {
		logout.click();
		return this.driver.getTitle();
	}

	public String getUsername() {
		return userName.getText();
	}

	public void deleteAccount() {
		deleteAccount.click();
	}

	public String accountDeletedConfirmation() {
		return accountDeletedConfirmation.getText();
	}

	public String invalidLogin() {
		return invalidLogin.getText();
	}
	
	public String alreadyExisEmailMsg() {
		return alreadyExisEmailMsg.getText();
	}

}
