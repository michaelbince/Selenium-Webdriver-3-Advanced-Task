package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        PageFactory.initElements(driver, this);
    }

    public WebElement waitForVisibilityOf(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickAbilityOf(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForTextAndReturnElement(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        return element;
    }

    public FluentWait<WebElement> getFluentWait(WebElement element){
        return new FluentWait<>(element)
                .withTimeout(Duration.ofSeconds(FLUENT_WAIT_TIMEOUT_IN_SECONDS))
                .pollingEvery(Duration.ofMillis(FLUENT_WAIT_POLLING_IN_MILLISECONDS))
                .ignoring(NoSuchElementException.class);
    }


}
