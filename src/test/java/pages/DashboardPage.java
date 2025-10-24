package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "h6.oxd-text--h6")
    private WebElement pageTitle; // "Dashboard"

    @FindBy(css = "a[href*='/web/index.php/pim/']")
    private WebElement pimLink;

    @FindBy(css = "i.oxd-userdropdown-icon")
    private WebElement userDropdown;

    @FindBy(css = "a[href*='/web/index.php/auth/logout']")
    private WebElement logoutLink;

    @FindBy(css = "a[href*='/web/index.php/directory/']")
    private WebElement directoryLink;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isDashboardPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.getText().equals("Dashboard");
    }

    public void clickPimLink() {
        wait.until(ExpectedConditions.elementToBeClickable(pimLink)).click();
    }

    public void logout() {
        userDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public void clickDirectoryLink() {
        wait.until(ExpectedConditions.elementToBeClickable(directoryLink)).click();
    }
}
