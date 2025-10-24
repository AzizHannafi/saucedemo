package tests;

import base.BaseTest;
import org.junit.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest extends BaseTest {

  @Test
  public void testSuccessfulLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    assertTrue("Login page should be displayed", loginPage.isLoginPageDisplayed());
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    DashboardPage dashboardPage = new DashboardPage(driver);
    assertTrue("Dashboard page should be displayed", dashboardPage.isDashboardPageDisplayed());
  }

  @Test
  public void testFailedLogin() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);
    String errorMessage = loginPage.getErrorMessage();
    assertEquals("Error message should match", "Invalid credentials", errorMessage);
  }

  @Test
  public void testLogout() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.logout();

    assertTrue("Should be back on login page", loginPage.isLoginPageDisplayed());
  }

  @Test
  public void testLoginWithEmptyUsername() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    // Tentative de login avec username vide
    loginPage.login(TestData.EMPTY_USERNAME, TestData.VALID_PASSWORD);

    // Vérifier qu'on reste sur la page de login
    assertTrue("Login page should still be displayed", loginPage.isLoginPageDisplayed());

    // Vérifier le message de validation de champ avec la nouvelle méthode
    String validationMessage = loginPage.getFieldValidationMessage();
    assertTrue("Field validation message should be displayed",
            !validationMessage.isEmpty());

    // Log supplémentaire pour debug
    System.out.println("Validation message: " + validationMessage);
  }

  @Test
  public void testLoginWithEmptyPassword() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();

    // Tentative de login avec password vide
    loginPage.login(TestData.VALID_USERNAME, TestData.EMPTY_PASSWORD);

    // Vérifier qu'on reste sur la page de login
    assertTrue("Login page should still be displayed", loginPage.isLoginPageDisplayed());

    // Vérifier le message de validation
    String validationMessage = loginPage.getFieldValidationMessage();
    assertTrue("Field validation message should be displayed",
            !validationMessage.isEmpty());
  }
}
