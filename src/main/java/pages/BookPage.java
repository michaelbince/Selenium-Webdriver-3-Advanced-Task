package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookPage extends BasePage {

    @FindBy(css = "input#add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(css = "span#productTitle")
    private WebElement bookAddedTittle;

    public String getBookAddedTittle(){
      return waitForVisibilityOf(bookAddedTittle).getText();
    }

    public void addBookToCart(){
        waitForVisibilityOf(addToCartButton).click();
    }
}
