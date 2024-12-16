package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends BasePage {
    @FindBy(css= "[name='signIn'] h1")
    private WebElement signInTitle;

    public Boolean IsSignInPageTitleDisplayed(){
        return waitForVisibilityOf(signInTitle).isDisplayed();
    }
}
