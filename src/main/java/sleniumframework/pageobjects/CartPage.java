package sleniumframework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import sleniumframework.abstractcomponents.AbstractComponent;

public class CartPage  extends AbstractComponent{
	private WebDriver driver;
	public CartPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[href*='product_details']")
	private List<WebElement> productName;

	@FindBy(css = ".check_out")
	private WebElement productCheckout;	
	
	@FindBy(css="p a[href*='login']")
	private WebElement registerLoginPage;
	
	@FindBy(css=".close-checkout-modal")
	private WebElement continueOnCart;
	
	@FindBy(css=".form-control")
	private WebElement commentsArea;
	
	@FindBy(css="[href*='payment']")
	private WebElement placeOrder;	

	@FindBy(css="[href*='product_details']")
	private List<WebElement> cartProducts;
	
	@FindBy(className="cart_price")
	private List<WebElement> productPrices;
	
	@FindBy(css="button.disabled")
	private List<WebElement> productQuantity;
	
	@FindBy(css=".cart_total_price")
	private List<WebElement> finalTotal;
	
	@FindBy(className = "cart_quantity_delete")
	private List<WebElement> removeFromCartButton;
	
	
	@FindBy(css="#address_delivery .address_firstname")
	private WebElement name;
	
	public void removeFromCart(int index) {
		removeFromCartButton.get(index).click();
	}
	
	public int cartProductCount() {
		return productName.size();
	}
	
	public String customerName() {
		return name.getText();
	}
	
	By productsBy = By.cssSelector("[href*='product_details']");
	
	public String productName(int index) {
		return productName.get(index).getText();
	}
	
	public Boolean verifyProducrDisplayed(String productName) {
		waitForElementToAppear(productsBy);
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public int productQuantity(int index) {
		return Integer.parseInt(productQuantity.get(index).getText());
	}
	
	public double productTotalPrice(int index) {
		double total = Double.parseDouble(finalTotal.get(index).getText().split(" ")[1]);
		double quantity = productQuantity(index);
		return total * quantity;
	}
	
	public double actualTotalAmount() {
		int i = finalTotal.size() - 1;
		String amountText = finalTotal.get(i).getText();
		double actualTotal = Double.parseDouble(amountText.split(" ")[1]);
		return actualTotal;
	}

	public double getExpectedTotalPrice() {
		double totalPrice = 0;
		for(int i=0;i<productPrices.size(); i++) {
			String priceString = productPrices.get(i).getText();
			double price = Double.parseDouble(priceString.split(" ")[1]);
			double quantity = productQuantity(i);
			price = price * quantity;
			totalPrice += price;
		}
		return totalPrice;
	}
	public void productCheckout() {
		productCheckout.click();
	}
	
	public void registerLoginPage() {
		registerLoginPage.click();
	}
	
	public void continueOnCart() {
		continueOnCart.click();
	}
	
	public void commentsArea(String myComment) {
		commentsArea.sendKeys(myComment);
	}
	
	public void placeOrder() {
		placeOrder.click();
	}

}
