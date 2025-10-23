package tests;

import base.BaseTest;
import org.junit.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utils.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest extends BaseTest {
  @Test
  public void testSuccessfulLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    assertTrue("Login page should be displayed", loginPage.isLoginPageDisplayed());
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    assertEquals("Products page title should match", "Products", productsPage.getPageTitle());
    assertTrue("Should have products displayed", productsPage.getProductCount() > 0);
  }

  @Test
  public void testFailedLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    loginPage.login(TestData.INVALID_USER, TestData.PASSWORD);
    String errorMessage = loginPage.getErrorMessage();
    assertTrue(
        "Error message should contain expected text",
        errorMessage.contains("Username and password do not match"));
  }

  @Test
  public void testLockedOutUser() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    // Perform login with locked out user
    loginPage.login(TestData.LOCKED_USER, TestData.PASSWORD);

    // Verify error message for locked out user
    String errorMessage = loginPage.getErrorMessage();
    assertTrue(
        "Error message should indicate user is locked out",
        errorMessage.contains("Sorry, this user has been locked out"));

    // Verify we're still on login page
    assertTrue("Should remain on login page", loginPage.isLoginPageDisplayed());
  }

  @Test
  public void testLogout() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.logout();

    assertTrue("Should be back on login page", loginPage.isLoginPageDisplayed());
  }
}
