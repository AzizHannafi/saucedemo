package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utils.TestData.BASE_URL;

public class LoginPage {
  private WebDriver driver;

  @FindBy(id = "user-name")
  private WebElement usernameField;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(id = "login-button")
  private WebElement loginButton;

  @FindBy(css = ".error-message-container")
  private WebElement errorMessage;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void navigateToLoginPage() {
    driver.get(BASE_URL);
  }

  public void login(String username, String password) {
    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    loginButton.click();
  }

  public String getErrorMessage() {
    return errorMessage.getText();
  }

  public boolean isLoginPageDisplayed() {
    return loginButton.isDisplayed();
  }
}
