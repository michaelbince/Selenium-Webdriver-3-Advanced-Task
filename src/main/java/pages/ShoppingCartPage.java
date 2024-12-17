package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShoppingCartPage extends BasePage {
    @FindBy(css = "a.sc-product-title")
    private WebElement bookInCart;

    @FindBy(id = "sc-buy-box-ptc-button")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "button[data-a-selector='increment']")
    private List<WebElement> incrementBooksAmountButton;

    @FindBy(css = "div[role='spinbutton']")
    private List<WebElement> booksAmountLabel;

    @FindBy(css = "button[data-a-selector='decrement']")
    private List<WebElement> decrementBooksAmountButton;

    public String getBookInCartTittle(){
        return waitForVisibilityOf(bookInCart).getText();
    }

    public void proceedToCheckoutWithAddedBooks(){
        waitForClickAbilityOf(proceedToCheckoutButton).click();
    }

    public void increaseBooksAmountForBookNumber(int booksAmount, int bookIndex){
        for (int increaseIndex = 0;increaseIndex < booksAmount; increaseIndex++) {
            waitForClickAbilityOf(incrementBooksAmountButton.get(bookIndex)).click();
        }
    }

    public int getBooksAmountForBookNumber(int bookIndex){
        waitForVisibilityOf(booksAmountLabel.get(bookIndex));
        return Integer.parseInt(booksAmountLabel.get(bookIndex).getText());
    }

    public void decrementBooksAmountForBookNumber(int booksAmount, int bookIndex) {
        for (int increaseIndex = 0;increaseIndex < booksAmount; increaseIndex++) {
            waitForClickAbilityOf(decrementBooksAmountButton.get(bookIndex)).click();
        }
    }
}
