package sleniumframework.pageobjects;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import sleniumframework.abstractcomponents.AbstractComponent;

public class PaymentPage extends AbstractComponent {
	private WebDriver driver;

	public PaymentPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[name*='name_on_card']")
	private WebElement nameOnCard;

	@FindBy(css = "[name*='card_number']")
	private WebElement cardNumber;

	@FindBy(css = "[name*='cvc']")
	private WebElement cvcNumber;

	@FindBy(css = "[name*='expiry_month']")
	private WebElement expiryMonth;

	@FindBy(css = "[name*='expiry_year']")
	private WebElement expiryYear;

	@FindBy(id = "submit")
	private WebElement payConfirmOrder;

	@FindBy(css = "h2.title")
	private WebElement orderPlacedText;

	@FindBy(css = "p[style*='garamond']")
	private WebElement congratulationText;

	@FindBy(css = "[href*='download']")
	private WebElement downloadInvoice;

	@FindBy(css = "[data-qa='continue-button']")
	private WebElement continueShopping; // home page

	public void nameOnCard(String name) {
		nameOnCard.sendKeys(name); // Test Engineer
	}

	public void cardNumber(String number) {
		cardNumber.sendKeys(number); // 4242424242424242
	}

	public void cvcNumber(String cvc) {
		cvcNumber.sendKeys(cvc); // 352
	}

	public void expiryMonth(String month) {
		expiryMonth.sendKeys(month); // 07
	}

	public void expiryYear(String year) {
		expiryYear.sendKeys(year); // 2026
	}

	public void payConfirmOrder() {
		payConfirmOrder.click();
	}

	public String orderPlacedText() {
		return orderPlacedText.getText();
	}

	public String congratulationText() {
		return congratulationText.getText(); // unused, only for double checking
	}

	public void downloadInvoice() throws InterruptedException {
		String downloadPath = System.getProperty("user.dir") + "\\downloads";
		downloadInvoice.click();
		File file = new File(downloadPath + "\\invoice.txt");
		Thread.sleep(1000);
		Assert.assertTrue(file.exists());
		if (file.exists()) {
			//System.out.println("file found");
			Assert.assertTrue(file.delete());
		}
	}

	public String continueShopping() {
		continueShopping.click();
		return this.driver.getTitle();
	}

}
