package seleniumFramework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class HomeRecommendedItemsPage extends AbstractComponent {
	private WebDriver driver;

	public HomeRecommendedItemsPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//Find all locators of Home page like Menu Tree execution	
	@FindBy(css = ".features_items h2.title")
	private WebElement featureTitle;
	
	@FindBy(css = ".recommended_items h2.title")
	private WebElement recommendedTitle;
	
	@FindBy(css=".recommended_items div h2")
	private List<WebElement> price;
	
	@FindBy(css=".recommended_items p")
	private List<WebElement> products;
	
	@FindBy(css="#recommended-item-carousel .add-to-cart")
	private List<WebElement> addInCart;
	
	@FindBy(css=".close-modal")
	private WebElement continueShopping;
	
	@FindBy(css="p a[href*='view_cart']")
	private WebElement viewCart;

	By productsBy = By.cssSelector(".recommended_items div h2");
	By addToCart = By.cssSelector(".recommended_items .add-to-cart");
	

	public String featureTitle() {
		return featureTitle.getText();
	}	
	
	public String recommendedTitle() {
		return recommendedTitle.getText();
	}	
	
	public void cartByIndexRecommendedItems(int index) {
		//((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", addInCart.get(1));
		addInCart.get(index).click();
	}

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) {
		// WebElement prod = getProductByName(productName);
		getProductByName(productName).findElement(addToCart).click();
	}
	public void continueShopping() {
		continueShopping.click();
	}
	public void viewCart() {
		viewCart.click();
	}

}
