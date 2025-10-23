package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
}