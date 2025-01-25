package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class KruidvatHomePage extends BasePage {

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "a[href='/cart'] div.minicart__basket")
    private WebElement cartIcon;


    public void goToCart(){
        waitForClickAbilityOf(acceptCookiesButton).click();
        waitForVisibilityOf(cartIcon);
        forceClick(cartIcon);
    }
}
