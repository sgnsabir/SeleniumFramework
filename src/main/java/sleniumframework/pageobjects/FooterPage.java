package sleniumframework.pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sleniumframework.abstractcomponents.AbstractComponent;

public class FooterPage  extends AbstractComponent{
	private WebDriver driver;
	public FooterPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "susbscribe_email")
	private WebElement subscriptionEmail;
	
	@FindBy(id="subscribe")
	private WebElement subscribeButton;
	
	@FindBy(css=".alert-success.alert")
	private WebElement subscriptionConfirmation;
	
	@FindBy(id = "scrollUp")
	private WebElement scrollUpButton;

	public void subscriptionEmail(String email) {
		//((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", subscriptionEmail);
		subscriptionEmail.sendKeys(email);
	}

	public void subscribeButton() {
		subscribeButton.click();
	}
	
	public String subscriptionConfirmation() {
		return subscriptionConfirmation.getText(); //You have been successfully subscribed!
	}
	
	public String classValueScrollUpButtonClick() {
		return scrollUpButton.getCssValue("display");
	}
	
	public void scrollUpButton() {
		scrollUpButton.click();
	}
}
