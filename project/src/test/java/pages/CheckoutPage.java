package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

  private WebDriver driver;

  @FindBy(id = "first-name")
  private WebElement firstNameField;

  @FindBy(id = "last-name")
  private WebElement lastNameField;

  @FindBy(id = "postal-code")
  private WebElement postalCodeField;

  @FindBy(id = "continue")
  private WebElement continueButton;

  @FindBy(id = "finish")
  private WebElement finishButton;

  @FindBy(className = "complete-header")
  private WebElement successMessage;

  public CheckoutPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
    firstNameField.sendKeys(firstName);
    lastNameField.sendKeys(lastName);
    postalCodeField.sendKeys(postalCode);
    continueButton.click();
  }

  public void completePurchase() {
    finishButton.click();
  }

  public String getSuccessMessage() {
    return successMessage.getText();
  }
}
