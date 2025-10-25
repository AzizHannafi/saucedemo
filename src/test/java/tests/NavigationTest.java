package tests;

import base.BaseTest;
import org.junit.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestData;

import static org.junit.Assert.assertTrue;

public class NavigationTest extends BaseTest {

  @Test
  public void testDirectAccessToProtectedPage() {
    driver.get(TestData.BASE_URL + "web/index.php/pim/viewEmployeeList");

    LoginPage loginPage = new LoginPage(driver);
    assertTrue(
        "Should redirect to login page when accessing protected page directly",
        loginPage.isLoginPageDisplayed());
  }

  @Test
  public void testMultipleQuickLogins() {
    LoginPage loginPage = new LoginPage(driver);

    for (int i = 0; i < 3; i++) {
      loginPage.navigateToLoginPage();
      loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

      DashboardPage dashboardPage = new DashboardPage(driver);
      if (dashboardPage.isDashboardPageDisplayed()) {
        dashboardPage.logout();
      }
    }

    loginPage.navigateToLoginPage();
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    DashboardPage finalDashboard = new DashboardPage(driver);
    assertTrue(
        "Should be able to login after multiple quick sessions",
        finalDashboard.isDashboardPageDisplayed());
  }
}
