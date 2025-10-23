package tests;

import base.BaseTest;
import org.junit.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utils.TestData;

import static org.junit.Assert.assertTrue;

public class ProductFilterTest extends BaseTest {

  @Test
  public void testFilterProductsByNameAToZ() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.filterBy(TestData.FILTER_A_TO_Z);

    assertTrue("Products should be sorted by name A to Z", productsPage.isSortedByNameAToZ());
  }

  @Test
  public void testFilterProductsByNameZToA() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.filterBy(TestData.FILTER_Z_TO_A);

    assertTrue("Products should be sorted by name Z to A", productsPage.isSortedByNameZToA());
  }

  @Test
  public void testFilterProductsByPriceLowToHigh() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.filterBy(TestData.FILTER_LOW_TO_HIGH);

    assertTrue(
        "Products should be sorted by price low to high", productsPage.isSortedByPriceLowToHigh());
  }

  @Test
  public void testFilterProductsByPriceHighToLow() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);
    productsPage.filterBy(TestData.FILTER_HIGH_TO_LOW);

    assertTrue(
        "Products should be sorted by price high to low", productsPage.isSortedByPriceHighToLow());
  }

  @Test
  public void testMultipleFilterChanges() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.navigateToLoginPage();
    loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);

    ProductsPage productsPage = new ProductsPage(driver);

    productsPage.filterBy(TestData.FILTER_LOW_TO_HIGH);
    assertTrue(
        "Products should be sorted by price low to high", productsPage.isSortedByPriceLowToHigh());

    productsPage.filterBy(TestData.FILTER_HIGH_TO_LOW);
    assertTrue(
        "Products should be sorted by price high to low", productsPage.isSortedByPriceHighToLow());

    productsPage.filterBy(TestData.FILTER_A_TO_Z);
    assertTrue("Products should be sorted by name A to Z", productsPage.isSortedByNameAToZ());

    productsPage.filterBy(TestData.FILTER_Z_TO_A);
    assertTrue("Products should be sorted by name Z to A", productsPage.isSortedByNameZToA());
  }
}
