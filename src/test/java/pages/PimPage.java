package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PimPage {
  private WebDriver driver;
  private WebDriverWait wait;

  @FindBy(css = "h6.oxd-text--h6")
  private WebElement pageHeader; // "PIM"

  @FindBy(xpath = "//label[text()='Employee Name']/../following-sibling::div//input")
  private WebElement employeeNameInput;

  @FindBy(css = "div[role='listbox'] span")
  private WebElement autocompleteSuggestion;

  @FindBy(css = "button[type='submit']")
  private WebElement searchButton;

  @FindBy(css = ".orangehrm-horizontal-padding.orangehrm-vertical-padding span.oxd-text")
  private WebElement recordFoundText;

  @FindBy(
      xpath =
          "//div[contains(@class, 'oxd-table-body')]//div[contains(@class, 'oxd-table-row')][1]/div[contains(@class, 'oxd-table-cell')][3]//div")
  private WebElement firstResultFirstNameCellContent;

  @FindBy(
      xpath =
          "//div[contains(@class, 'oxd-table-body')]//div[contains(@class, 'oxd-table-row')][1]/div[contains(@class, 'oxd-table-cell')][4]//div")
  private WebElement firstResultLastNameCellContent;

  public PimPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    PageFactory.initElements(driver, this);
  }

  public boolean isPimPageDisplayed() {
    wait.until(ExpectedConditions.visibilityOf(pageHeader));
    return pageHeader.getText().equals("PIM");
  }

  public void searchForEmployee(String nameHint) {
    try {
      wait.until(ExpectedConditions.elementToBeClickable(employeeNameInput));
      employeeNameInput.clear();
      employeeNameInput.sendKeys(nameHint);

      Thread.sleep(2000);

      By suggestionLocator = By.xpath("//div[@role='listbox']//span[contains(text(), '" + nameHint + "')]");
      List<WebElement> suggestions = driver.findElements(suggestionLocator);

      if (!suggestions.isEmpty() && suggestions.get(0).isDisplayed()) {
        suggestions.get(0).click();
      } else {
        System.out.println("No autocomplete suggestions found for: " + nameHint + ". Continuing with search...");

        try {
          employeeNameInput.sendKeys(org.openqa.selenium.Keys.ESCAPE);
        } catch (Exception e) {
          // Ignorer si ESC échoue
        }
      }

      wait.until(ExpectedConditions.elementToBeClickable(searchButton));
      searchButton.click();

      waitForLoadingToDisappear();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Search interrupted", e);
    } catch (Exception e) {
      System.out.println("Error during search, attempting to continue: " + e.getMessage());
      try {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForLoadingToDisappear();
      } catch (Exception ex) {
        throw new RuntimeException("Failed to search for employee: " + nameHint, ex);
      }
    }
  }

  public String getFirstEmployeeNameFromResult() {
    try {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-loading-spinner")));

      wait.until(ExpectedConditions.textToBePresentInElement(recordFoundText, "Record"));

      wait.until(ExpectedConditions.visibilityOf(firstResultFirstNameCellContent));
      wait.until(ExpectedConditions.visibilityOf(firstResultLastNameCellContent));

      Thread.sleep(500);

      String firstName = firstResultFirstNameCellContent.getText().trim();
      String lastName = firstResultLastNameCellContent.getText().trim();

      return (firstName + " " + lastName).trim();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Thread interrupted while getting employee name", e);
    } catch (Exception e) {
      throw new RuntimeException("Failed to get employee name from results", e);
    }
  }

  public boolean isNoRecordsFound() {
    try {
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".oxd-loading-spinner")));

      By[] noRecordsSelectors = {
              By.xpath("//span[contains(text(), 'No Records Found')]"),
              By.xpath("//div[contains(text(), 'No Records Found')]"),
              By.cssSelector(".oxd-text.oxd-text--span"),
              By.xpath("//div[contains(@class, 'orangehrm-horizontal-padding')]//span[contains(@class, 'oxd-text')]")
      };

      for (By selector : noRecordsSelectors) {
        try {
          WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
          if (element.isDisplayed() &&
                  (element.getText().contains("No Records") ||
                          element.getText().contains("No records") ||
                          element.getText().contains("Aucun enregistrement"))) {
            return true;
          }
        } catch (Exception e) {
          // Continuer avec le sélecteur suivant
        }
      }
      return false;

    } catch (Exception e) {
      return false;
    }
  }
  private void waitForLoadingToDisappear() {
    try {
      By loadingSpinner = By.cssSelector(".oxd-loading-spinner, .oxd-loading-spinner-container");
      wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
      Thread.sleep(1000);
    } catch (Exception e) {
      // Ignorer si pas de spinner
    }
  }
}
