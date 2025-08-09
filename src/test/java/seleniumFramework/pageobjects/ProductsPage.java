package seleniumFramework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class ProductsPage extends AbstractComponent {
	public ProductsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

//HomeRecommendedItemsPage
	@FindBy(id = "sale_image")
	private WebElement saleImage;

	@FindBy(id = "search_product")
	private WebElement searchProductField;

	@FindBy(id = "submit_search")
	private WebElement searchButton;

	@FindBy(css = ".features_items h2.title")
	private WebElement featureTitle;
	
	@FindBy(css = ".productinfo p")
	private List<WebElement> products;
	
	@FindBy(css=".productinfo h2")
	List<WebElement> productPrice;
	
	@FindBy(css=".productinfo .add-to-cart")
	List<WebElement> addToCartButton;

	@FindBy(className = "close-modal")
	private WebElement continueShopping;

	@FindBy(css = "p a[href*='view_cart']")
	private WebElement viewCart;

	@FindBy(css="[href*='product_details']")
	private List<WebElement> productDetails;
	
	By productsBy = By.cssSelector(".productinfo p");
	By addToCart = By.cssSelector(".features_items .add-to-cart");
	
	public int numberOfProducts() {
		return products.size();
	}
	
	public Double productPrice(int index) {
		String priceText = productPrice.get(index).getText();
		Double price = Double.parseDouble(priceText.split(" ")[1]);
		return price;
	} 
	
	public void addProductInCartByIndex(int i) {		
		addToCartButton.get(i).click();
	}
	
	public String productName(int index) {
		return products.get(index).getText();
	}
	
	public String productDetails(int index) {
		//This function is to validate Product Details, after validating products page will be called.
		String productName = productName(index);
		productDetails.get(index).click();
		return productName;
	}

	public Boolean saleImage() {
		return saleImage.isDisplayed();
	}
	
	public Boolean searchProduct(String productName) { // assertTrue
		searchProductField.sendKeys(productName);
		searchButton.click();
		waitForElementToAppear(productsBy);
		Boolean searchResult = false;
		for (WebElement product : products) {
			searchResult = product.getText().contains(productName);
		}
		return searchResult;
	}

	public String featureTitle() {
		return featureTitle.getText();
	}

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream().filter(product -> product.getText().contains(productName))
				.findFirst().orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) {
		getProductByName(productName).findElement(addToCart).click();
	}

	public void continueShopping() {
		continueShopping.click();
	}

	public void viewCart() {
		viewCart.click();
	}

}
