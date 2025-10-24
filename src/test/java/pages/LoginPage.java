package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.TestData.BASE_URL;

public class LoginPage {
  private WebDriver driver;
  private WebDriverWait wait;

  @FindBy(name = "username")
  private WebElement usernameField;

  @FindBy(name = "password")
  private WebElement passwordField;

  @FindBy(css = "button[type='submit']")
  private WebElement loginButton;

  // --- THIS IS THE CORRECTED LINE ---
  @FindBy(css = ".oxd-alert-content-text")
  private WebElement errorMessage;

  @FindBy(css = "h5.oxd-text--h5")
  private WebElement loginPageHeader;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
  }

  public void navigateToLoginPage() {
    driver.get(BASE_URL);
  }

  public void login(String username, String password) {
    // Wait for fields to be visible
    wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    passwordField.sendKeys(password);
    loginButton.click();
  }

  public String getErrorMessage() {
    // This wait will now find the <p> tag and pass
    wait.until(ExpectedConditions.visibilityOf(errorMessage));
    return errorMessage.getText();
  }

  public boolean isLoginPageDisplayed() {
    wait.until(ExpectedConditions.visibilityOf(loginPageHeader));
    return loginPageHeader.isDisplayed() && loginPageHeader.getText().equals("Login");
  }
}
