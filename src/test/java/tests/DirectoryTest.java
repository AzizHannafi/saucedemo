package tests;

import base.BaseTest;
import org.junit.Test;
import pages.DashboardPage;
import pages.DirectoryPage;
import pages.LoginPage;
import utils.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DirectoryTest extends BaseTest {

    @Test
    public void testSearchDirectoryByName() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickDirectoryLink();

        DirectoryPage directoryPage = new DirectoryPage(driver);
        assertTrue("Directory page should be displayed", directoryPage.isDirectoryPageDisplayed());
        directoryPage.searchByName(TestData.DIRECTORY_SEARCH_NAME_HINT);

        String actualName = directoryPage.getFirstResultName();
        String actualJobTitle = directoryPage.getFirstResultJobTitle();

        assertEquals(
                "Result card name should match expected",
                TestData.DIRECTORY_EXPECTED_NAME,
                actualName);

        assertEquals(
                "Result card job title should match expected",
                TestData.DIRECTORY_EXPECTED_JOB_TITLE,
                actualJobTitle);
    }

    @Test
    public void testSearchDirectoryByJobTitle() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickDirectoryLink();

        DirectoryPage directoryPage = new DirectoryPage(driver);
        assertTrue("Directory page should be displayed", directoryPage.isDirectoryPageDisplayed());
        directoryPage.searchByJobTitle(TestData.DIRECTORY_SEARCH_JOB_TITLE);


        String actualJobTitle = directoryPage.getFirstResultJobTitle();
        String actualName = directoryPage.getFirstResultName(); // Optional check

        assertEquals(
                "First result card job title should match search",
                TestData.DIRECTORY_SEARCH_JOB_TITLE,
                actualJobTitle);
    }
}