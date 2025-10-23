package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductsPage {

  private WebDriver driver;

  @FindBy(className = "title")
  private WebElement pageTitle;

  @FindBy(className = "inventory_item")
  private List<WebElement> productItems;

  @FindBy(css = ".shopping_cart_link")
  private WebElement cartIcon;

  @FindBy(id = "react-burger-menu-btn")
  private WebElement menuButton;

  @FindBy(id = "logout_sidebar_link")
  private WebElement logoutLink;

  @FindBy(className = "product_sort_container")
  private WebElement filterDropdown;

  @FindBy(className = "inventory_item_name")
  private List<WebElement> productNames;

  @FindBy(className = "inventory_item_price")
  private List<WebElement> productPrices;

  public ProductsPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public String getPageTitle() {
    return pageTitle.getText();
  }

  public void addProductToCart(String productName) {
    for (WebElement product : productItems) {
      if (product.getText().contains(productName)) {
        product.findElement(By.cssSelector(".btn_inventory")).click();
        break;
      }
    }
  }

  public void clickCartIcon() {
    cartIcon.click();
  }

  public void logout() {
    menuButton.click();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logoutLink.click();
  }

  public int getProductCount() {
    return productItems.size();
  }

  // Filter methods
  public void filterBy(String filterOption) {
    Select filterSelect = new Select(filterDropdown);
    filterSelect.selectByVisibleText(filterOption);
  }

  // Get product names in current order
  public List<String> getProductNames() {
    return productNames.stream().map(WebElement::getText).toList();
  }

  // Get product prices in current order
  public List<Double> getProductPrices() {
    return productPrices.stream()
        .map(price -> Double.parseDouble(price.getText().replace("$", "")))
        .toList();
  }

  // Check if products are sorted by price low to high
  public boolean isSortedByPriceLowToHigh() {
    List<Double> prices = getProductPrices();
    for (int i = 0; i < prices.size() - 1; i++) {
      if (prices.get(i) > prices.get(i + 1)) {
        return false;
      }
    }
    return true;
  }

  // Check if products are sorted by price high to low
  public boolean isSortedByPriceHighToLow() {
    List<Double> prices = getProductPrices();
    for (int i = 0; i < prices.size() - 1; i++) {
      if (prices.get(i) < prices.get(i + 1)) {
        return false;
      }
    }
    return true;
  }

  // Check if products are sorted by name A to Z
  public boolean isSortedByNameAToZ() {
    List<String> names = getProductNames();
    for (int i = 0; i < names.size() - 1; i++) {
      if (names.get(i).compareTo(names.get(i + 1)) > 0) {
        return false;
      }
    }
    return true;
  }

  // Check if products are sorted by name Z to A
  public boolean isSortedByNameZToA() {
    List<String> names = getProductNames();
    for (int i = 0; i < names.size() - 1; i++) {
      if (names.get(i).compareTo(names.get(i + 1)) < 0) {
        return false;
      }
    }
    return true;
  }
}
