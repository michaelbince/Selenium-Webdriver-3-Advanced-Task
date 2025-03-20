package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.locators.RelativeLocator;

public class ToasterComponent extends BasePage {

    @FindBy(className = "glow-toaster")
    private WebElement toasterContainer;

    private final WebElement toasterContent = driver.findElement(RelativeLocator.with(By.className("glow-toaster-content")).below(toasterContainer));
    private final WebElement toasterTitle = driver.findElement(RelativeLocator.with(By.id("glow-toaster-title")).below(toasterContainer));
    private final WebElement toasterBody = driver.findElement(RelativeLocator.with(By.id("glow-toaster-body")).below(toasterTitle));
    private final WebElement toasterMessage = driver.findElement(RelativeLocator.with(By.tagName("span")).near(toasterBody));
    private final WebElement toasterError = driver.findElement(RelativeLocator.with(By.className("glow-toaster-error")).below(toasterMessage));
    private final WebElement continueButton = driver.findElement(RelativeLocator.with(By.xpath("//span[contains(@class,'glow-toaster-button-dismiss')]")));
    private final WebElement changeAddressButton = driver.findElement(RelativeLocator.with(By.xpath("//span[contains(@class,'glow-toaster-button-submit')]")));

    public void closeToaster() {
        waitForClickAbilityOf(continueButton);
        continueButton.click();
    }
}
