package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
  private WebElement recordFoundText; // e.g., "(1) Record Found"

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
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    PageFactory.initElements(driver, this);
  }

  public boolean isPimPageDisplayed() {
    wait.until(ExpectedConditions.visibilityOf(pageHeader));
    return pageHeader.getText().equals("PIM");
  }

  public void searchForEmployee(String nameHint) {
    employeeNameInput.sendKeys(nameHint);

    By suggestionLocator =
        By.xpath("//div[@role='listbox']//span[contains(text(), '" + nameHint + "')]");
    WebElement suggestionElement =
        wait.until(ExpectedConditions.elementToBeClickable(suggestionLocator));
    suggestionElement.click();

    searchButton.click();
  }

  public String getFirstEmployeeNameFromResult() {
    wait.until(ExpectedConditions.visibilityOf(recordFoundText));


    wait.until(ExpectedConditions.visibilityOf(firstResultFirstNameCellContent));

    String firstName = firstResultFirstNameCellContent.getText();
    String lastName =
        firstResultLastNameCellContent.getText();

    return (firstName + " " + lastName).trim();
  }
}
