package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DirectoryPage {

  private WebDriver driver;
  private WebDriverWait wait;
  private Actions actions;

  @FindBy(css = "h5.oxd-text--h5")
  private WebElement pageHeader; // Should contain "Directory"

  @FindBy(xpath = "//label[text()='Employee Name']/../following-sibling::div//input")
  private WebElement employeeNameInput;

  @FindBy(
      xpath =
          "//label[text()='Job Title']/../following-sibling::div//div[contains(@class, 'oxd-select-text')]")
  private WebElement jobTitleDropdown;

  @FindBy(css = ".oxd-select-dropdown")
  private WebElement dropdownOptionsContainer;

  @FindBy(css = "button[type='submit']")
  private WebElement searchButton;

  @FindBy(
      css =
          ".orangehrm-directory-card-body p.oxd-text--p:nth-of-type(1)")
  private WebElement resultCardNameHeader;

  @FindBy(css = "p.orangehrm-directory-card-header")
  private WebElement resultCardName;

  @FindBy(css = "p.orangehrm-directory-card-subtitle")
  private WebElement resultCardJobTitle;

  @FindBy(css = ".orangehrm-horizontal-padding.orangehrm-vertical-padding span.oxd-text")
  private WebElement recordFoundText;


  public DirectoryPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    this.actions = new Actions(driver);
    PageFactory.initElements(driver, this);
  }

  public boolean isDirectoryPageDisplayed() {
    try {
      wait.until(ExpectedConditions.visibilityOf(pageHeader));
      return pageHeader.getText().contains("Directory");
    } catch (Exception e) {
      System.err.println("Directory page header not found or text mismatch.");
      return false;
    }
  }

  public void searchByName(String nameHint) {
    wait.until(ExpectedConditions.visibilityOf(employeeNameInput)).clear(); // Clear field first
    employeeNameInput.sendKeys(nameHint);

    By suggestionLocator =
        By.xpath("//div[@role='listbox']//span[contains(text(), '" + nameHint + "')]");
    WebElement suggestionElement =
        wait.until(ExpectedConditions.elementToBeClickable(suggestionLocator));
    suggestionElement.click();

    wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
  }

  public void searchByJobTitle(String jobTitle) {
    wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
    wait.until(ExpectedConditions.visibilityOf(dropdownOptionsContainer));

    By jobTitleOptionLocator = By.xpath("//div[@role='listbox']//span[text()='" + jobTitle + "']");
    WebElement jobTitleOption =
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleOptionLocator));
    jobTitleOption.click();

    wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
  }

  public String getFirstResultName() {
    try {
      wait.until(ExpectedConditions.visibilityOf(recordFoundText));
      // Wait specifically for the name header element
      wait.until(ExpectedConditions.visibilityOf(resultCardName));
      return resultCardName.getText();
    } catch (Exception e) {
      System.err.println("Could not find result card name element after search: " + e.getMessage());
      return "";
    }
  }

  public String getFirstResultJobTitle() {
    try {
      wait.until(ExpectedConditions.visibilityOf(recordFoundText));
      // Wait specifically for the corrected job title (subtitle) element
      wait.until(ExpectedConditions.visibilityOf(resultCardJobTitle));
      return resultCardJobTitle.getText();
    } catch (Exception e) {
      System.err.println(
          "Could not find result card job title element after search: " + e.getMessage());
      return "";
    }
  }

  public List<String> getAllResultJobTitles() {
    List<String> jobTitles = new ArrayList<>();
    try {
      wait.until(ExpectedConditions.visibilityOf(recordFoundText));

      List<WebElement> jobTitleElements =
          driver.findElements(By.cssSelector("p.orangehrm-directory-card-subtitle"));

      if (!jobTitleElements.isEmpty()) {
        wait.until(ExpectedConditions.visibilityOf(jobTitleElements.get(0)));
      } else {
        System.out.println("No job title elements found in results.");
        return jobTitles;
      }
      for (WebElement element : jobTitleElements) {
        jobTitles.add(element.getText());
      }
    } catch (Exception e) {
      System.err.println("Error retrieving job titles from result cards: " + e.getMessage());
    }
    return jobTitles;
  }
}
