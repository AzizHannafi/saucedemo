package tests;

import base.BaseTest;
import org.junit.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.TestData;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest extends BaseTest {

  @Test
  public void testCompletePurchaseFlow() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.addProductToCart(TestData.PRODUCT_NAME);
    productsPage.clickCartIcon();

    CartPage cartPage = new CartPage(driver);
    assertEquals("Cart page title should match", "Your Cart", cartPage.getPageTitle());
    assertEquals("Should have 1 item in cart", 1, cartPage.getCartItemsCount());
    cartPage.clickCheckout();

    CheckoutPage checkoutPage = new CheckoutPage(driver);
    checkoutPage.fillCheckoutInformation(
        TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
    checkoutPage.completePurchase();

    assertEquals(
        "Success message should match",
        "Thank you for your order!",
        checkoutPage.getSuccessMessage());
  }

  @Test
  public void testNavigationBetweenPages() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.clickCartIcon();

    CartPage cartPage = new CartPage(driver);
    cartPage.clickContinueShopping();

    assertEquals("Should be back on products page", "Products", productsPage.getPageTitle());
  }
}
