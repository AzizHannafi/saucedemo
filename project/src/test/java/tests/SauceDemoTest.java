package tests;

import base.BaseTest;
import pages.*;
import utils.TestData;
import org.junit.Test;
import static org.junit.Assert.*;

public class SauceDemoTest extends BaseTest {

  @Test
  public void testSuccessfulLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    // Verify login page is displayed
    assertTrue("Login page should be displayed", loginPage.isLoginPageDisplayed());

    // Perform login
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    // Verify successful login
    ProductsPage productsPage = new ProductsPage(driver);
    assertEquals("Products page title should match", "Products", productsPage.getPageTitle());
    assertTrue("Should have products displayed", productsPage.getProductCount() > 0);
  }

  @Test
  public void testFailedLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    // Perform login with invalid credentials
    loginPage.login(TestData.INVALID_USER, TestData.PASSWORD);

    // Verify error message
    String errorMessage = loginPage.getErrorMessage();
    assertTrue("Error message should contain expected text",
               errorMessage.contains("Username and password do not match"));
  }

  @Test
  public void testCompletePurchaseFlow() {
    // Login
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    // Add product to cart
    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.addProductToCart(TestData.PRODUCT_NAME);
    productsPage.clickCartIcon();

    // Verify cart
    CartPage cartPage = new CartPage(driver);
    assertEquals("Cart page title should match", "Your Cart", cartPage.getPageTitle());
    assertEquals("Should have 1 item in cart", 1, cartPage.getCartItemsCount());
    cartPage.clickCheckout();

    // Fill checkout information
    CheckoutPage checkoutPage = new CheckoutPage(driver);
    checkoutPage.fillCheckoutInformation(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
    checkoutPage.completePurchase();

    // Verify successful purchase
    assertEquals("Success message should match",
                 "Thank you for your order!",
                 checkoutPage.getSuccessMessage());
  }

  @Test
  public void testNavigationBetweenPages() {
    // Login
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    // Navigate to cart and back
    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.clickCartIcon();

    CartPage cartPage = new CartPage(driver);
    cartPage.clickContinueShopping();

    // Verify we're back on products page
    assertEquals("Should be back on products page", "Products", productsPage.getPageTitle());
  }

  @Test
  public void testLogout() {
    // Login
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    // Logout
    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.logout();

    // Verify we're back on login page
    assertTrue("Should be back on login page", loginPage.isLoginPageDisplayed());
  }
}