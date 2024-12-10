package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {
    @FindBy(css = "a.sc-product-title")
    private WebElement bookInCart;

    @FindBy(id = "sc-buy-box-ptc-button")
    private WebElement proceedToCheckoutButton;

    public String getBookInCartTittle(){
        return waitForVisibilityOf(bookInCart).getText();
    }

    public void proceedToCheckoutWithAddedBooks(){
        waitForClickAbilityOf(proceedToCheckoutButton).click();
    }
}
