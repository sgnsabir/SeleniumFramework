package seleniumFramework.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	private WebDriver driver;
	public LandingPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
//header bar
	@FindBy(css = "a img")
	private WebElement pageLogo;
	
	@FindBy(css = "i.fa-home")
	private WebElement homeIcon;
	
	@FindBy(css = "[href*='/products']")
	private WebElement products;
	
	@FindBy(css = "[href*='/view_cart']")
	private WebElement cart;
	
	@FindBy(css = "[href*='/login']")
	private WebElement signupLogin;
	
	@FindBy(css = "[href*='/test_cases']")
	private WebElement testCases;
	
	@FindBy(css = "[href*='api_list']")
	private WebElement apiTesting;
	
	@FindBy(css = "[href*='youtube']")
	private WebElement videoTutorial;
	
	@FindBy(css = "[href*='/contact_us']")
	private WebElement contactUs;

	@FindBy(css="p.fc-button-label")
	private WebElement consentButton;
	
	@FindBy(css=".features_items .title")
	private WebElement bodyHeaderTitle;
	
	//left side bar
	@FindBy(css = "[href*='Women']")
	private WebElement womenCategory;
	@FindBy(css = "#Women [href*='1']")
	private WebElement womenDressCategory;
	@FindBy(css = "#Women [href*='2']")
	private WebElement womenTopsCategory;
	@FindBy(css = "#Women [href*='7']")
	private WebElement 	womenSareeCategory;

	@FindBy(css = "[href*='Men']")
	private WebElement menCategory;
	@FindBy(css = "#Men [href*='3']")
	private WebElement menTshirtCategory;
	@FindBy(css = "#Men [href*='6']")
	private WebElement menJeansCategory;	

	@FindBy(css = "[href*='Kids']")
	private WebElement kidsCategory;
	@FindBy(css = "#Kids [href*='4']")
	private WebElement kidsDressCategory;
	@FindBy(css = "#Kids [href*='5']")
	private WebElement kidsTopsCategory;	
	
	@FindBy(css = ".brands-name [href*='Polo']")
	private WebElement poloBrand;	
	
	@FindBy(css = ".brands-name [href*='H&M']")
	private WebElement hnmBrand;	
	
	@FindBy(css = ".brands-name [href*='Madame']")
	private WebElement madameBrand;	
	
	@FindBy(css = ".brands-name [href*='Harbour']")
	private WebElement mastHarbourBrand;	
	
	@FindBy(css = ".brands-name [href*='Babyhug']")
	private WebElement	babyhugBrand;
	
	@FindBy(css = ".brands-name [href*='Allen']")
	private WebElement	allenSollyJrBrand;
	
	@FindBy(css = ".brands-name [href*='Kookie']")
	private WebElement	kookieKidsBrand;
	
	@FindBy(css = ".brands-name [href*='Biba']")
	private WebElement	bibaBrand;
	
	@FindBy(css = ".left-sidebar h2")
	private List<WebElement> leftSideMenuTitle;

	@FindBy(css="[src*='picture']")
	private List<WebElement> productImage;
	
	@FindBy(css = ".recommended_items h2.title")
	private WebElement recommendedTitle;
	
	public Boolean productVisibility() {
		return productImage.get(0).isDisplayed();
	}
	
	public String recommendedTitle() {
		return recommendedTitle.getText();
	}
	
	public String bodyHeaderTitle() {
		return bodyHeaderTitle.getText();
	}
	
	public String categorySideBarTitle() {
		return leftSideMenuTitle.get(0).getText();
	}
	
	public String brandsSideBarTitle() {
		return leftSideMenuTitle.get(1).getText();
	}
	
	public String pageLogo() {		
		pageLogo.click();
		return this.driver.getTitle(); //Automation Exercise
		//driver.navigate().back();
	}
	public String home() {
		homeIcon.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	public String products() {
		products.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	public String cartPage() {
		cart.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	public String signupLogin() {
		signupLogin.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	
	public String testCases() {
		testCases.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	public String aPITesting() {
		apiTesting.click();
		return this.driver.getTitle(); //Automation Exercise
	}
	public String videoTutorials() {
		videoTutorial.click();
		String title = this.driver.getTitle(); //Automation Exercise
		this.driver.navigate().back();
		return title;
	}
	public String contactUs() {
		contactUs.click();
		return this.driver.getTitle(); //Automation Exercise
	}

	public void womenCategory() {
		womenCategory.click();
	}
	public String womenDressCategory() {
		womenDressCategory.click();		
		return this.driver.getTitle();
	}
	public String womenTopsCategory() {
		womenTopsCategory.click();
		return this.driver.getTitle();
	}
	public String womenSareeCategory() {
		womenSareeCategory.click();
		return this.driver.getTitle();
	}
	public void menCategory() {
		menCategory.click();
	}
	public String menTshirtCategory() {
		menTshirtCategory.click();
		return this.driver.getTitle();
	}
	public String menJeansCategory() {
		menJeansCategory.click();
		return this.driver.getTitle();
	}
	public void kidsCategory() {
		kidsCategory.click();
	}
	public String kidsDressCategory() {
		kidsDressCategory.click();
		return this.driver.getTitle();
	}
	public String kidsTopsCategory() {
		kidsTopsCategory.click();
		return this.driver.getTitle();
	}
	public String poloBrand() {
		poloBrand.click();
		return this.driver.getTitle();
	}
	public String hnmBrand() {
		hnmBrand.click();
		return this.driver.getTitle();
	}
	public String madameBrand() {
		madameBrand.click();
		return this.driver.getTitle();
	}
	public String mastHarbourBrand() {
		mastHarbourBrand.click();
		return this.driver.getTitle();
	}
	public String babyhugBrand() {
		babyhugBrand.click();
		return this.driver.getTitle();
	}
	public String allenSollyJrBrand() {
		allenSollyJrBrand.click();
		return this.driver.getTitle();
	}
	public String kookieKidsBrand() {
		kookieKidsBrand.click();
		return this.driver.getTitle();
	}
	public String bibaBrand() {
		bibaBrand.click();
		return this.driver.getTitle();
	}

}
