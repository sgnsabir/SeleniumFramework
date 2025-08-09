package seleniumFramework.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import org.testng.Assert;

import seleniumFramework.pageobjects.BannerPage;
import seleniumFramework.utils.BaseTest;

public class PageTitleValidation extends BaseTest {
	/*
	 * This class is to validate navigation to all menu item and putting assertion
	 * by its page and/or body title. here I am only putting assertion.
	 */

	String email = "automationtester0001@gmail.com";
	String filePath = System.getProperty("user.dir") + "\\test_file.pdf";

	// Test Case 6: Contact Us Form
	@Test(priority = 23)
	public void headerMenuValidation() {
		AssertJUnit.assertEquals(landingPage.home(), "Automation Exercise");
		AssertJUnit.assertEquals(landingPage.products(), "Automation Exercise - All Products");
		AssertJUnit.assertEquals(landingPage.cartPage(), "Automation Exercise - Checkout");
		AssertJUnit.assertEquals(landingPage.signupLogin(), "Automation Exercise - Signup / Login");
		AssertJUnit.assertEquals(landingPage.testCases(), "Automation Practice Website for UI Testing - Test Cases");
		AssertJUnit.assertEquals(landingPage.aPITesting(), "Automation Practice for API Testing");
		AssertJUnit.assertEquals(landingPage.videoTutorials(), "Before you continue to YouTube"); // not going to actual page
		AssertJUnit.assertEquals(landingPage.contactUs(), "Automation Exercise - Contact Us");
	}

	@Test(priority = 24)
	public void addBannerValidation() {
		landingPage.home();
		BannerPage banner = new BannerPage(driver);
		Assert.assertTrue(banner.firstCarousel());
		Assert.assertTrue(banner.secondCarousel());
		Assert.assertTrue(banner.thirdCarousel());
		AssertJUnit.assertTrue(banner.leftArrowValidation());
		AssertJUnit.assertTrue(banner.rightArrowValidation());
		AssertJUnit.assertEquals("Automation Practice Website for UI Testing - Test Cases", banner.testCasesButton());
		AssertJUnit.assertEquals("Automation Practice for API Testing", banner.apiListButton());
		AssertJUnit.assertEquals("AutomationExercise", banner.carouselCardTitle());
	}

	@Test(priority = 25)
	public void sideMenuValidation() {
		landingPage.home();
		AssertJUnit.assertEquals(landingPage.categorySideBarTitle(), "CATEGORY");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "FEATURES ITEMS");
		AssertJUnit.assertEquals(landingPage.recommendedTitle(), "RECOMMENDED ITEMS");
		landingPage.womenCategory();
		AssertJUnit.assertEquals(landingPage.womenDressCategory(), "Automation Exercise - Dress Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "WOMEN - DRESS PRODUCTS");
		landingPage.womenCategory();
		AssertJUnit.assertEquals(landingPage.womenTopsCategory(), "Automation Exercise - Tops Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "WOMEN - TOPS PRODUCTS");
		landingPage.womenCategory();
		AssertJUnit.assertEquals(landingPage.womenSareeCategory(), "Automation Exercise - Saree Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "WOMEN - SAREE PRODUCTS");
		landingPage.menCategory();
		AssertJUnit.assertEquals(landingPage.menTshirtCategory(), "Automation Exercise - Tshirts Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "MEN - TSHIRTS PRODUCTS");
		landingPage.menCategory();
		AssertJUnit.assertEquals(landingPage.menJeansCategory(), "Automation Exercise - Jeans Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "MEN - JEANS PRODUCTS");
		landingPage.kidsCategory();
		AssertJUnit.assertEquals(landingPage.kidsDressCategory(), "Automation Exercise - Dress Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "KIDS - DRESS PRODUCTS");
		landingPage.kidsCategory();
		AssertJUnit.assertEquals(landingPage.kidsTopsCategory(), "Automation Exercise - Tops & Shirts Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "KIDS - TOPS & SHIRTS PRODUCTS");

		AssertJUnit.assertEquals(landingPage.poloBrand(), "Automation Exercise - Polo Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - POLO PRODUCTS");
		AssertJUnit.assertEquals(landingPage.hnmBrand(), "Automation Exercise - H&M Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - H&M PRODUCTS");
		AssertJUnit.assertEquals(landingPage.madameBrand(), "Automation Exercise - Madame Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - MADAME PRODUCTS");
		AssertJUnit.assertEquals(landingPage.mastHarbourBrand(), "Automation Exercise - Mast & Harbour Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - MAST & HARBOUR PRODUCTS");
		AssertJUnit.assertEquals(landingPage.babyhugBrand(), "Automation Exercise - Babyhug Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - BABYHUG PRODUCTS");
		AssertJUnit.assertEquals(landingPage.allenSollyJrBrand(), "Automation Exercise - Allen Solly Junior Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - ALLEN SOLLY JUNIOR PRODUCTS");
		AssertJUnit.assertEquals(landingPage.kookieKidsBrand(), "Automation Exercise - Kookie Kids Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - KOOKIE KIDS PRODUCTS");
		AssertJUnit.assertEquals(landingPage.bibaBrand(), "Automation Exercise - Biba Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - BIBA PRODUCTS");
	}
}
