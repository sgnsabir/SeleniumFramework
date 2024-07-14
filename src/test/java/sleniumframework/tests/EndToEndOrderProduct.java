package sleniumframework.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import sleniumframework.pageobjects.CartPage;
import sleniumframework.pageobjects.FooterPage;
import sleniumframework.pageobjects.HomeRecommendedItemsPage;
import sleniumframework.pageobjects.PaymentPage;
import sleniumframework.pageobjects.ProductDetailsPage;
import sleniumframework.pageobjects.ProductsPage;
import sleniumframework.pageobjects.SignupLoginPage;
import sleniumframework.testcomponents.BaseTest;

public class EndToEndOrderProduct extends BaseTest {
	SignupLoginPage signUpLogin;
	ProductsPage productPage;
	HomeRecommendedItemsPage recommendedItems;
	ProductDetailsPage productDetails;
	CartPage cart;
	PaymentPage payment;
	FooterPage footer;
	String email = "automationtester0000@gmail.com";
	String filePath = System.getProperty("user.dir") + "\\test_file.pdf";

	//Purchase flow through Product Details
	@Test
	public void productOrderThroughProductDetails() throws InterruptedException {
		String prductsPageTitle = landingPage.products();
		AssertJUnit.assertEquals(prductsPageTitle, "Automation Exercise - All Products");
		productPage = new ProductsPage(driver);
		int producCount = productPage.numberOfProducts();
		AssertJUnit.assertEquals(producCount, 34);
		Double priceInProductsPage = productPage.productPrice(1);
		String productName = productPage.productDetails(1);
		productDetails = new ProductDetailsPage(driver);
		String productNameInProductDetails = productDetails.productNameInProductDetails();
		AssertJUnit.assertEquals(productNameInProductDetails, productName);
		Double priceInProductDetailsPage = productDetails.productPriceInDetails();
		AssertJUnit.assertEquals(priceInProductDetailsPage, priceInProductsPage);
		productDetails.productQuantity("1");
		productDetails.category().contains("Men > Tshirts");
		productDetails.availabilityStatus().contains("In Stock");
		productDetails.conditionStatus().contains("New");
		productDetails.productBrand().contains("H&M");
		productDetails.reviewTitleBar().equalsIgnoreCase("Write Your Review");
		productDetails.nameField("Test Engineer");
		productDetails.emailField(email);
		productDetails.reviewTextArea("Thank you, for helping us to practice. This is surely a great initiative");
		productDetails.submitButton();
		productDetails.thankingMsg().equals("Thank you for your review.");
		productDetails.addToCartButton();
		productDetails.continueShopping();
		productDetails.viewCart();
		cart = new CartPage(driver);
		cart.productName(0).equals(productName);
		cart.productCheckout();
		cart.continueOnCart();
		cart.productCheckout();
		cart.registerLoginPage();
		SignupLoginPage signupLogin = new SignupLoginPage(driver);
		signupLogin.loginEmail(email);
		signupLogin.loginEmailPassword("Test@143");
		signupLogin.loginButton();
		// It suppose to go to Place Order page after logged in.
		landingPage.cartPage();
		cart.productCheckout();
		double actualAmount = cart.actualTotalAmount();
		double expectedAmount = cart.getExpectedTotalPrice();
		AssertJUnit.assertEquals(actualAmount, expectedAmount);
		cart.commentsArea("This is a testing comments");
		cart.placeOrder();
		payment = new PaymentPage(driver);
		payment.nameOnCard("Test Engineer");
		payment.cardNumber("4242424242424242");
		payment.cvcNumber("357");
		payment.expiryMonth("07");
		payment.expiryYear("2000");
		payment.payConfirmOrder();
		payment.orderPlacedText().equals("ORDER PLACED!");
		payment.downloadInvoice();
		payment.continueShopping().equals("Automation Exercise");
	}
	//Test Case 9: Search Product
	@Test
	public void searchProduct() {
	landingPage.products();
	productPage = new ProductsPage(driver);
	Assert.assertTrue(productPage.searchProduct("Men Tshirt"));		
	}
	
	//Test Case 10: Verify Subscription in home page
	@Test
	public void subscriptionValidation() throws InterruptedException {
		landingPage.home();
		footer = new FooterPage(driver);
		footer.subscriptionEmail(email);
		footer.subscribeButton();
		footer.subscriptionConfirmation().equals("You have been successfully subscribed!");
		String beforeClass = footer.classValueScrollUpButtonClick();
		footer.scrollUpButton();
		Thread.sleep(500);
		String afterClass = footer.classValueScrollUpButtonClick();
		Assert.assertNotEquals(beforeClass, afterClass);
	}
	
	//Test Case 12: Add Products in Cart
	@Test
	public void addProductInCart() {
		landingPage.home();
		productPage = new ProductsPage(driver);
		productPage.addProductInCartByIndex(0);
		String productName1 = productPage.productName(0);
		double productPrice1 = productPage.productPrice(0);
		productPage.continueShopping();
		productPage.addProductInCartByIndex(1);
		String productName2 = productPage.productName(1);
		double productPrice2 = productPage.productPrice(1);
		productPage.viewCart();
		cart = new CartPage(driver);
		cart.productName(0).equals(productName1);
		AssertJUnit.assertEquals(cart.productQuantity(0), 1);
		AssertJUnit.assertEquals(cart.productTotalPrice(0), productPrice1);
		cart.productName(1).equals(productName2);
		AssertJUnit.assertEquals(cart.productQuantity(1), 1);
		AssertJUnit.assertEquals(cart.productTotalPrice(1), productPrice2);		
	}
	
	//Test Case 13: Verify Product quantity in Cart
	@Test
	public void validateProductQuantityInCart(){
		productPage = new ProductsPage(driver);
		productPage.productDetails(1);
		productDetails = new ProductDetailsPage(driver);
		productDetails.productQuantity("3");
		productDetails.addToCartButton();
		productDetails.viewCart();
		cart = new CartPage(driver);
		AssertJUnit.assertEquals(cart.productQuantity(0), 3);
	}
	
	//Test Case 14: Place Order: Register while Checkout
	@Test
	public void registerWhileCheckout() throws InterruptedException {
		validateProductQuantityInCart();
		//cart = new CartPage(driver);
		cart.productCheckout();
		cart.registerLoginPage();
		signUpLogin = new SignupLoginPage(driver);
		signUpLogin.signupName("Test Engineer");
		signUpLogin.signupEmail("automationtester0002@gmail.com");
		signUpLogin.signupButton();
		signUpLogin.selectMaleGender();
		signUpLogin.signupPassword("Test@143");
		signUpLogin.daySelectionFromList(13);
		signUpLogin.monthSelctionFromList(9);
		signUpLogin.yearSelectionFromList(1987);
		signUpLogin.newsletterCheckbox();
		signUpLogin.offerCheckbox();
		signUpLogin.firstNameField("Test");
		signUpLogin.lastNameField("Engineer");
		signUpLogin.companyNameField("Test Automation Lab");
		signUpLogin.address1Field("Fjellveien 33");
		signUpLogin.address2Field("Narvik 8505");
		signUpLogin.countrySelection("New Zealand");
		signUpLogin.stateField("Nordland");
		signUpLogin.cityField("Narvik");
		signUpLogin.zipcodeField("8515");
		signUpLogin.mobileNumberField("12345678");
		signUpLogin.createAccountButton();	
		String actualText = signUpLogin.accountCreatedConfirmation();
		AssertJUnit.assertEquals(actualText, "ACCOUNT CREATED!");
		signUpLogin.continueButton();
		landingPage.cartPage();
		cart.productCheckout();
		AssertJUnit.assertEquals(cart.customerName(), "Mr. Test Engineer"); //can be validating address details
		double actualAmount = cart.actualTotalAmount();
		double expectedAmount = cart.getExpectedTotalPrice();
		AssertJUnit.assertEquals(actualAmount, expectedAmount);
		cart.commentsArea("This is a testing comments");
		cart.placeOrder();
		payment = new PaymentPage(driver);
		payment.nameOnCard("Test Engineer");
		payment.cardNumber("4242424242424242");
		payment.cvcNumber("357");
		payment.expiryMonth("07");
		payment.expiryYear("2000");
		payment.payConfirmOrder();
		payment.orderPlacedText().equals("ORDER PLACED!");
		payment.downloadInvoice();
		payment.continueShopping().equals("Automation Exercise");
		signUpLogin.deleteAccount();
		signUpLogin.accountDeletedConfirmation();
		signUpLogin.continueButton().equals("Automation Exercise");		
	}
	
	//Test Case 17: Remove Products From Cart
	@Test
	public void removeProductFromCart() throws InterruptedException {
		landingPage.home();
		productPage = new ProductsPage(driver);
		for(int i=0; i<3; i++) {
			productPage.addProductInCartByIndex(i);
			if(i!=2)
			productPage.continueShopping();
			else 
			productPage.viewCart();
		}
		cart = new CartPage(driver);
		int beforeRemove = cart.cartProductCount();
		cart.removeFromCart(1);
		Thread.sleep(500);
		int afterRemove = cart.cartProductCount();
		Assert.assertNotEquals(beforeRemove, afterRemove);		
	}
	
	//Test Case 18: View Category Products
	@Test
	public void viewCategoryProducts() {
		landingPage.home();
		AssertJUnit.assertEquals(landingPage.categorySideBarTitle(), "CATEGORY");
		AssertJUnit.assertEquals(landingPage.brandsSideBarTitle(), "BRANDS");
		landingPage.womenCategory();
		String pageTitle = landingPage.womenDressCategory();
		AssertJUnit.assertEquals(pageTitle, "Automation Exercise - Dress Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "WOMEN - DRESS PRODUCTS");
		landingPage.menCategory();
		AssertJUnit.assertEquals(landingPage.menTshirtCategory(), "Automation Exercise - Tshirts Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "MEN - TSHIRTS PRODUCTS");
		AssertJUnit.assertEquals(landingPage.poloBrand(), "Automation Exercise - Polo Products");
		AssertJUnit.assertEquals(landingPage.bodyHeaderTitle(), "BRAND - POLO PRODUCTS");
	}
	
	//Test Case 19: View & Cart Brand Products
	@Test
	public void viewCartBrandProducts() {
		landingPage.home();
		landingPage.products();
		String pageTitle = landingPage.babyhugBrand();
		AssertJUnit.assertEquals(pageTitle, "Automation Exercise - Babyhug Products");
		AssertJUnit.assertEquals(landingPage.bibaBrand(), "Automation Exercise - Biba Products");
		Assert.assertTrue(landingPage.productVisibility());
	}
	//Test Case 20: Search Products and Verify Cart After Login
	@Test
	public void validateCartProductAfterLogin() {
		landingPage.home();
		landingPage.products();
		productPage = new ProductsPage(driver);
		Assert.assertTrue(productPage.searchProduct("Dress"));
		int searchList = productPage.numberOfProducts();
		for(int i=0; i<searchList; i++) {
			productPage.addProductInCartByIndex(i);
			productPage.continueShopping();
		}
		landingPage.signupLogin();
		signUpLogin = new SignupLoginPage(driver);
		signUpLogin.loginEmail(email);
		signUpLogin.loginEmailPassword("Test@143");
		landingPage.cartPage();
		cart = new CartPage(driver);
		AssertJUnit.assertEquals(cart.cartProductCount(), searchList);		
	}
	/*
	 * Below test cases are already covered in end to end test product order flow
	 * Test Case 24: Download Invoice after purchase order
	 * Test Case 25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality
	 * Test Case 26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality
	 */
	
}
