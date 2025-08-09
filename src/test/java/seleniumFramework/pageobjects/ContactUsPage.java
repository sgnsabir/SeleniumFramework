package seleniumFramework.pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class ContactUsPage extends AbstractComponent {
	private WebDriver driver;

	public ContactUsPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//Find all locators of Home page like Menu Tree execution	
	@FindBy(css = ".col-sm-12 .text-center")
	private WebElement contactUsTitle;
	@FindBy(css = ".contact-form .text-center")
	private WebElement getInTouchTitle;
	@FindBy(css = "[data-qa='name']")
	private WebElement nameField;
	@FindBy(css = "[data-qa='email']")
	private WebElement emailField;
	@FindBy(css = "[data-qa='subject']")
	private WebElement subjectField;
	@FindBy(id = "message")
	private WebElement messageTextField;
	@FindBy(name = "upload_file")
	private WebElement chooseFileButton;
	@FindBy(name = "submit")
	private WebElement submitForm;

	@FindBy(css = ".contact-info .text-center")
	private WebElement feedbackForUsText;
	@FindBy(css = "u")
	private WebElement contactEmail; // feedback@automationexercise.com

	@FindBy(css="div.status")
	private WebElement successMessage;
	
	@FindBy(className = "btn-success")
	private WebElement successButton;
	
	public String feedbackForUsText() {
		return feedbackForUsText.getText();
	}
	
	public String contactEmail() {
		return contactEmail.getText();
	}

	public String contactUsTitle() {
		return contactUsTitle.getText(); // CONTACT US
	}

	public String getInTouchTitle() {
		return getInTouchTitle.getText(); // GET IN TOUCH
	}

	public void nameField(String name) {
		nameField.sendKeys(name);
	}

	public void emailField(String email) {
		emailField.sendKeys(email);
	}

	public void subjectField(String subject) {
		subjectField.sendKeys(subject);
	}

	public void messageTextField(String message) {
		messageTextField.sendKeys(message);
	}

	public void uploadFile(String filePath) {
		chooseFileButton.sendKeys(filePath);
	}
	
	public void submitForm() {
		submitForm.click();
	}
	
	public void javascriptAlert() {
		//Alert alert = WebDriver.switchTo().alert();
		Alert alert = this.driver.switchTo().alert();		
		alert.accept();
	}
	
	public String successMessage() {
		return successMessage.getText();
		//Success! Your details have been submitted successfully.
	}
	
	public void successButton() {
		successButton.click();
	}

}
