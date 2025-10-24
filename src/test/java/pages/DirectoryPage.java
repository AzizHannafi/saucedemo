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
import java.util.ArrayList; // Added for getAllResultJobTitles
import java.util.List;     // Added for getAllResultJobTitles

public class DirectoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @FindBy(css = "h5.oxd-text--h5")
    private WebElement pageHeader; // Should contain "Directory"

    @FindBy(xpath = "//label[text()='Employee Name']/../following-sibling::div//input")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//label[text()='Job Title']/../following-sibling::div//div[contains(@class, 'oxd-select-text')]")
    private WebElement jobTitleDropdown;

    @FindBy(css = ".oxd-select-dropdown")
    private WebElement dropdownOptionsContainer;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    // --- Result Card Locators (Corrected) ---
    @FindBy(css = ".orangehrm-directory-card-body p.oxd-text--p:nth-of-type(1)") // This targets 'Administration' now based on HTML. Need to adjust if Name is primary target. Let's assume Name is the header p tag for robustness.
    private WebElement resultCardNameHeader; // Renamed to clarify it's the header

    @FindBy(css = "p.orangehrm-directory-card-header") // Using the header directly for name
    private WebElement resultCardName;

    @FindBy(css = "p.orangehrm-directory-card-subtitle") // Corrected Job Title selector
    private WebElement resultCardJobTitle;

    @FindBy(css = ".orangehrm-horizontal-padding.orangehrm-vertical-padding span.oxd-text")
    private WebElement recordFoundText;
    // --- End Corrected Locators ---

    public DirectoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Checks if the Directory page header is visible and contains the expected text.
     * @return true if the page is displayed, false otherwise.
     */
    public boolean isDirectoryPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeader));
            return pageHeader.getText().contains("Directory");
        } catch (Exception e) {
            System.err.println("Directory page header not found or text mismatch.");
            return false;
        }
    }

    /**
     * Enters a name hint into the search field, selects the autocomplete suggestion,
     * and clicks the search button.
     * @param nameHint The partial name to search for.
     */
    public void searchByName(String nameHint) {
        wait.until(ExpectedConditions.visibilityOf(employeeNameInput)).clear(); // Clear field first
        employeeNameInput.sendKeys(nameHint);

        By suggestionLocator = By.xpath("//div[@role='listbox']//span[contains(text(), '" + nameHint + "')]");
        WebElement suggestionElement = wait.until(ExpectedConditions.elementToBeClickable(suggestionLocator));
        suggestionElement.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    /**
     * Selects a job title from the dropdown and clicks the search button.
     * @param jobTitle The exact job title text to select.
     */
    public void searchByJobTitle(String jobTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        wait.until(ExpectedConditions.visibilityOf(dropdownOptionsContainer));

        By jobTitleOptionLocator = By.xpath("//div[@role='listbox']//span[text()='" + jobTitle + "']");
        WebElement jobTitleOption = wait.until(ExpectedConditions.elementToBeClickable(jobTitleOptionLocator));
        jobTitleOption.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    /**
     * Waits for search results and retrieves the name from the first result card's header.
     * @return The text content of the name element, or an empty string if not found.
     */
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

    /**
     * Waits for search results and retrieves the job title (subtitle) from the first result card.
     * @return The text content of the job title element, or an empty string if not found.
     */
    public String getFirstResultJobTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(recordFoundText));
            // Wait specifically for the corrected job title (subtitle) element
            wait.until(ExpectedConditions.visibilityOf(resultCardJobTitle));
            return resultCardJobTitle.getText();
        } catch (Exception e) {
            System.err.println("Could not find result card job title element after search: " + e.getMessage());
            return "";
        }
    }

    /**
     * Retrieves all job titles from the currently displayed result cards.
     * Waits for the search results to load before attempting retrieval.
     * @return A List of strings containing the job titles, or an empty list if none found or on error.
     */
    public List<String> getAllResultJobTitles() {
        List<String> jobTitles = new ArrayList<>();
        try {
            // Wait for the indicator that records are found
            wait.until(ExpectedConditions.visibilityOf(recordFoundText));

            // Find all elements matching the corrected job title selector
            List<WebElement> jobTitleElements = driver.findElements(By.cssSelector("p.orangehrm-directory-card-subtitle"));

            // Wait for at least one result card's job title to be visible before iterating
            if (!jobTitleElements.isEmpty()) {
                wait.until(ExpectedConditions.visibilityOf(jobTitleElements.get(0)));
            } else {
                System.out.println("No job title elements found in results.");
                return jobTitles; // Return empty list
            }

            // Extract text from each found element
            for (WebElement element : jobTitleElements) {
                jobTitles.add(element.getText());
            }
        } catch (Exception e) {
            System.err.println("Error retrieving job titles from result cards: " + e.getMessage());
        }
        return jobTitles;
    }
}