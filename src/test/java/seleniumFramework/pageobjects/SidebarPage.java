package seleniumFramework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class SidebarPage  extends AbstractComponent{
	private WebDriver driver;
	public SidebarPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
//Find all locators of Home page like Menu Tree execution	
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

	public String womenCategory() {
		womenCategory.click();
		return this.driver.getTitle();
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
	public String menCategory() {
		menCategory.click();
		return this.driver.getTitle();
	}
	public String menTshirtCategory() {
		menTshirtCategory.click();
		return this.driver.getTitle();
	}
	public String menJeansCategory() {
		menJeansCategory.click();
		return this.driver.getTitle();
	}
	public String kidsCategory() {
		kidsCategory.click();
		return this.driver.getTitle();
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
