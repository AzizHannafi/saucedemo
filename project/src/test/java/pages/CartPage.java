package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CartPage {

  private WebDriver driver;

  @FindBy(className = "title")
  private WebElement pageTitle;

  @FindBy(className = "cart_item")
  private List<WebElement> cartItems;

  @FindBy(id = "checkout")
  private WebElement checkoutButton;

  @FindBy(id = "continue-shopping")
  private WebElement continueShoppingButton;

  public CartPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public String getPageTitle() {
    return pageTitle.getText();
  }

  public int getCartItemsCount() {
    return cartItems.size();
  }

  public void clickCheckout() {
    checkoutButton.click();
  }

  public void clickContinueShopping() {
    continueShoppingButton.click();
  }
}