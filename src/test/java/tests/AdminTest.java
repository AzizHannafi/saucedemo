package tests;

import base.BaseTest;
import org.junit.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestData;

import static org.junit.Assert.assertTrue;

public class AdminTest extends BaseTest {

    @Test
    public void testAccessAdminPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickAdminLink();

        AdminPage adminPage = new AdminPage(driver);
        assertTrue("Admin page should be displayed", adminPage.isAdminPageDisplayed());
    }

    @Test
    public void testSearchAdminUser() {
        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickAdminLink();

        AdminPage adminPage = new AdminPage(driver);
        assertTrue("Admin page should be displayed", adminPage.isAdminPageDisplayed());

        adminPage.searchByUsername(TestData.ADMIN_SEARCH_USERNAME);

        assertTrue("Admin user should be found in the list",
                adminPage.isUserInList(TestData.ADMIN_SEARCH_USERNAME));
    }

    @Test
    public void testAddNewAdminUser() {
        try {
            // 1. Login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage();
            loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

            Thread.sleep(5000);

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
            Thread.sleep(5000);

            AdminPage adminPage = new AdminPage(driver);

            assertTrue("Should be on Admin page", adminPage.isAdminPageDisplayed());

            adminPage.clickAddButton();
            Thread.sleep(3000);


            System.out.println("Selecting user role...");
            adminPage.selectUserRole("Admin");
            Thread.sleep(2000);

            System.out.println("Entering employee name...");
            adminPage.enterEmployeeName("John");
            Thread.sleep(4000);

            System.out.println("Selecting status...");
            adminPage.selectStatus("Enabled");
            Thread.sleep(2000);

            System.out.println("Entering username...");
            String uniqueUsername = "newadmin" + System.currentTimeMillis();
            adminPage.enterUsername(uniqueUsername);
            Thread.sleep(2000);

            System.out.println("Entering password...");
            adminPage.enterPassword("Admin123!");
            Thread.sleep(2000);

            System.out.println("Confirming password...");
            adminPage.enterConfirmPassword("Admin123!");
            Thread.sleep(2000);

            System.out.println("Clicking save...");
            adminPage.clickSave();

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            throw new RuntimeException("Test failed", e);
        }
    }
}