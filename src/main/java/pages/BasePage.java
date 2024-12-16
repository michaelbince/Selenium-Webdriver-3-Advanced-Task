package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;
import java.util.NoSuchElementException;

public abstract class BasePage {
    private static final long DEFAULT_WAIT_IN_SECONDS = 10;
    private static final long FLUENT_WAIT_TIMEOUT_IN_SECONDS = 15;
    private static final long FLUENT_WAIT_POLLING_IN_MILLISECONDS = 500;

    protected final WebDriver driver;
    protected final WebDriverWait wait;



    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_IN_SECONDS));
        refresh();
    }

    public void refresh() {
        PageFactory.initElements(driver, this);
    }

    public WebElement waitForVisibilityOf(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickAbilityOf(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public FluentWait<WebElement> getFluentWait(WebElement element){
        return new FluentWait<>(element)
                .withTimeout(Duration.ofSeconds(FLUENT_WAIT_TIMEOUT_IN_SECONDS))
                .pollingEvery(Duration.ofMillis(FLUENT_WAIT_POLLING_IN_MILLISECONDS))
                .ignoring(NoSuchElementException.class);
    }

    public void selectCheckBox(WebElement checkbox){
        if (!checkbox.isSelected()){
            forceClick(checkbox);
        }
    }

    public void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void clickElementAfterRefresh(WebElement element) {
        Actions actions = new Actions(driver);
        refresh();
        wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.visibilityOf(element)
        ));
        actions.moveToElement(element).click().perform();
    }

    public void clickElementWhenFound(WebElement element) {
        Actions actions = new Actions(driver);

        try {
            actions.moveToElement(element).click().perform();
        } catch (StaleElementReferenceException e) {
            clickElementAfterRefresh(element);
        }
        catch (NoSuchElementException e) {
            clickElementAfterRefresh(element);
        }

    }

    public void scrollToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.scrollToElement(element).perform();
    }

    public  void forceClick(WebElement element) {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed clicking element using JavaScriptExecutor: " + e.getMessage(), e);
        }
    }

}
