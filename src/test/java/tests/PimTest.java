package tests;

import base.BaseTest;
import org.junit.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import utils.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PimTest extends BaseTest {

  @Test
  public void testSearchEmployeeByName() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.clickPimLink();

    PimPage pimPage = new PimPage(driver);
    assertTrue("PIM page should be displayed", pimPage.isPimPageDisplayed());

    pimPage.searchForEmployee(TestData.PIM_SEARCH_EMPLOYEE_NAME);

    String actualName = pimPage.getFirstEmployeeNameFromResult();
    assertEquals(
        "Search result should match expected employee name",
        TestData.PIM_EXPECTED_EMPLOYEE_NAME,
        actualName);
  }

  @Test
  public void testSearchEmployeeNoResults() {
    // 1. Login
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.clickPimLink();

    PimPage pimPage = new PimPage(driver);
    assertTrue("PIM page should be displayed", pimPage.isPimPageDisplayed());

    pimPage.searchForEmployee(TestData.NONEXISTENT_EMPLOYEE_NAME);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    assertTrue("No records should be found for non-existent employee", pimPage.isNoRecordsFound());

    System.out.println("Test passed: No records found for non-existent employee");
  }
}
