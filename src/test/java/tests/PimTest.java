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
    // 1. Login
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

    // 2. Petite pause après login
    try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

    // 3. Navigate to PIM page
    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.clickPimLink();

    // 4. Attendre que PIM charge
    PimPage pimPage = new PimPage(driver);
    assertTrue("PIM page should be displayed", pimPage.isPimPageDisplayed());

    // 5. Search for employee
    pimPage.searchForEmployee(TestData.PIM_SEARCH_EMPLOYEE_NAME);

    // 6. Verify search result
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

    // 2. Petite pause après login
    try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

    // 3. Navigate to PIM page
    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.clickPimLink();

    // 4. Attendre que PIM charge
    PimPage pimPage = new PimPage(driver);
    assertTrue("PIM page should be displayed", pimPage.isPimPageDisplayed());

    // 5. Search for non-existent employee
    pimPage.searchForEmployee(TestData.NONEXISTENT_EMPLOYEE_NAME);

    // 6. Attendre un peu pour les résultats
    try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

    // 7. Verify no results found
    assertTrue("No records should be found for non-existent employee",
            pimPage.isNoRecordsFound());

    System.out.println("Test passed: No records found for non-existent employee");
  }
}
