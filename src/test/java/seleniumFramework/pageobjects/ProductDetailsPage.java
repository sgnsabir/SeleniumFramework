package seleniumFramework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class ProductDetailsPage extends AbstractComponent {
	private WebDriver driver;

	public ProductDetailsPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// product details page

	@FindBy(css = "[src*='picture']")
	private WebElement productImage;

	@FindBy(className = "newarrival")
	private WebElement newArrival;

	@FindBy(css = ".product-information h2")
	private WebElement productNameInProductDetails;

	@FindBy(xpath = "//*[@class='product-information'] //p[1]")
	private WebElement category;

	@FindBy(css = "[src*='rating']")
	private WebElement productRating;

	@FindBy(css = ".col-sm-7 span:nth-child(1)")
	private WebElement productPriceInDetails;

	@FindBy(id = "quantity")
	private WebElement quantity;

	@FindBy(className = "cart")
	private WebElement addToCartButton;

	@FindBy(xpath = "//*[@class='product-information'] //p[2]")
	private WebElement availabilityStatus;

	@FindBy(xpath = "//*[@class='product-information'] //p[3]")
	private WebElement conditionStatus;

	@FindBy(xpath = "//*[@class='product-information'] //p[4]")
	private WebElement productBrand;

	@FindBy(css = "[href='#reviews']")
	private WebElement reviewTitleBar;

	@FindBy(id="name")
	private WebElement nameField;

	@FindBy(id="email")
	private WebElement emailField;

	@FindBy(id="review")
	private WebElement reviewTextArea;

	@FindBy(id="button-review")
	private WebElement submitButton;

	@FindBy(css="span[style*='20px']")
	private WebElement thankingMsg;
	
	@FindBy(className="btn-success")
	private WebElement continueShopping;
	
	@FindBy(css="u")
	private WebElement viewCart;

	public Boolean productImage() {
		return productImage.isDisplayed();
	}

	public Boolean newArrival() {
		return newArrival.isDisplayed();
	}

	public String productNameInProductDetails() {
		return productNameInProductDetails.getText();
	}

	public String category() {
		return category.getText();
	}

	public Boolean productRating() {
		return productRating.isDisplayed();
	}

	public Double productPriceInDetails() {
		String priceText = productPriceInDetails.getText();
		Double price = Double.parseDouble(priceText.split(" ")[1]);
		return price;
	}

	public void productQuantity(String Quantity) {
		quantity.clear();
		quantity.sendKeys(Quantity);
	}

	public String availabilityStatus() {
		return availabilityStatus.getText();
	}

	public String conditionStatus() {
		return conditionStatus.getText();
	}

	public String productBrand() {
		return category.getText();
	}

	public String reviewTitleBar() {
		return reviewTitleBar.getText();
	}

	public void nameField(String name) {
		nameField.sendKeys(name);
	}

	public void emailField(String email) {
		emailField.sendKeys(email);
	}

	public void reviewTextArea(String reviewText) {
		reviewTextArea.sendKeys(reviewText);
	}

	public void submitButton() {
		submitButton.click();
	}
	
	public String thankingMsg() {
		//Thank you for your review.
		return thankingMsg.getText();
	}

	public void addToCartButton() {
		addToCartButton.click();
	}
	
	public void continueShopping() {
		continueShopping.click();
		addToCartButton();
	}
	public void viewCart() {
		viewCart.click();
	}
	
}
