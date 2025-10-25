package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage {
  private WebDriver driver;
  private WebDriverWait wait;

  @FindBy(css = "h6.oxd-text--h6")
  private WebElement pageHeader;

  public AdminPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    PageFactory.initElements(driver, this);
  }

  public boolean isAdminPageDisplayed() {
    try {
      wait.until(ExpectedConditions.visibilityOf(pageHeader));
      return pageHeader.getText().equals("Admin");
    } catch (Exception e) {
      return false;
    }
  }

  public void clickAddButton() {
    WebElement addBtn =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//button[contains(@class, 'oxd-button') and (contains(., 'Add') or contains(., 'Add'))]")));
    addBtn.click();
  }

  public void selectUserRole(String role) {
    WebElement userRoleDropdown =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'oxd-select-wrapper')])[1]")));
    userRoleDropdown.click();

    WebElement roleOption =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='listbox']//span[text()='" + role + "']")));
    roleOption.click();
  }

  public void enterEmployeeName(String employeeName) {
    try {

      WebElement employeeNameInput =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath("//input[@placeholder='Type for hints...']")));

      employeeNameInput.clear();
      employeeNameInput.sendKeys(employeeName);
      Thread.sleep(3000);
      WebElement firstSuggestion =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath(
                      "//div[@role='listbox']//div[contains(@class, 'oxd-autocomplete-option')][1]")));
      firstSuggestion.click();
      Thread.sleep(1000);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted while entering employee name", e);
    } catch (Exception e) {
      System.err.println("Error in enterEmployeeName: " + e.getMessage());
      try {
        WebElement employeeNameInput =
            driver.findElement(By.xpath("//input[@placeholder='Type for hints...']"));
        employeeNameInput.sendKeys(Keys.ESCAPE);
      } catch (Exception ex) {
      }
      throw e;
    }
  }

  public void selectStatus(String status) {
    WebElement statusDropdown =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[contains(@class, 'oxd-select-wrapper')])[2]")));
    statusDropdown.click();

    WebElement statusOption =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='listbox']//span[text()='" + status + "']")));
    statusOption.click();
  }

  public void enterUsername(String username) {
    WebElement usernameInput =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(), 'Username')]/following::input[1]")));
    usernameInput.clear();
    usernameInput.sendKeys(username);
  }

  public void enterPassword(String password) {
    WebElement passwordInput =
        wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='password'])[1]")));
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }

  public void enterConfirmPassword(String password) {
    WebElement confirmPasswordInput =
        wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='password'])[2]")));
    confirmPasswordInput.clear();
    confirmPasswordInput.sendKeys(password);
  }

  public void clickSave() throws InterruptedException {
    try {
      WebElement saveBtn =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath("//button[contains(@class, 'oxd-button') and contains(., 'Save')]")));
      saveBtn.click();
      wait.until(
          ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-loading-spinner")));
      Thread.sleep(5000);

    } catch (Exception e) {
      System.err.println("Error clicking save button: " + e.getMessage());
      throw e;
    }
  }

  public void searchByUsername(String username) {
    try {
      driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
      Thread.sleep(3000);

      WebElement usernameSearchInput =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath("//label[contains(text(), 'Username')]/following::input[1]")));
      usernameSearchInput.clear();
      usernameSearchInput.sendKeys(username);

      clickSearch();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted during search", e);
    }
  }

  public void clickSearch() {
    WebElement searchBtn =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'oxd-button') and contains(., 'Search')]")));
    searchBtn.click();

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public boolean isUserInList(String username) {
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-body")));

      WebElement userElement =
          driver.findElement(
              By.xpath(
                  "//div[contains(@class, 'oxd-table-card')]//div[contains(text(), '"
                      + username
                      + "')]"));
      return userElement.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}
