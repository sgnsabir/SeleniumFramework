package seleniumFramework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.abstractcomponents.AbstractComponent;

public class BannerPage extends AbstractComponent{
	private WebDriver driver;
	public BannerPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
//Find all locators of Home page like Menu Tree execution	
	@FindBy(css = "li[data-slide-to='0']")
	private WebElement carousel1;	
	@FindBy(css="[src*='girl1']")
	private WebElement firstCarouselImage;
	@FindBy(css = "li[data-slide-to='0']")
	private WebElement carousel2;	
	@FindBy(css="[src*='girl2']")
	private WebElement secondCarouselImage;
	@FindBy(css = "li[data-slide-to='0']")
	private WebElement carousel3;
	@FindBy(css="[src*='girl3']")
	private WebElement thirdCarouselImage;
	@FindBy(css= "#slider-carousel .fa-angle-left")
	private WebElement leftArrow;
	@FindBy(css= "#slider-carousel .fa-angle-right")
	private WebElement rightArrow;
	@FindBy(css= ".active h1")
	private WebElement carouselCardTitle;
	@FindBy(css=".active [href*='test_cases']")
	private WebElement testCasesButton;
	@FindBy(css=".active [href*='list']")
	private WebElement apiListButton;
	
	//attribute class=active
	public Boolean firstCarousel() {		
		carousel1.click();
		return firstCarouselImage.isEnabled();
	}
	public Boolean secondCarousel() {
		carousel2.click();
		return secondCarouselImage.isEnabled();
	}
	public Boolean thirdCarousel() {
		carousel3.click();
		return thirdCarouselImage.isEnabled();
	}	
	
	public boolean leftArrowValidation() {
		leftArrow.click();
		return leftArrow.isEnabled();
	}
	
	public boolean rightArrowValidation() {
		rightArrow.click();
		return rightArrow.isEnabled();
	}
	
	public String carouselCardTitle() {
		return carouselCardTitle.getText(); //AutomationExercise
	}	
	public String testCasesButton() {
		testCasesButton.click();
		String title = this.driver.getTitle(); //Automation Exercise
		this.driver.navigate().back();
		return title;
	}	
	public String apiListButton() {
		apiListButton.click();
		String title = this.driver.getTitle(); //Automation Exercise
		this.driver.navigate().back();
		return title;
	}
}
