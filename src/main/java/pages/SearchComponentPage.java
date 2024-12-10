package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchComponentPage extends BasePage {

    @FindBy(id = "searchDropdownBox")
    private WebElement searchDropdown;

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchInput;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;


    @FindBy(id = "nav-search-bar-form")
    private WebElement searchForm;

    @FindBy(id = "nav-cart")
    private WebElement cartButton;

    public void selectCategory(String category) {
        waitForVisibilityOf(searchDropdown).sendKeys(category);
    }

    public void search(String text) {
        waitForVisibilityOf(searchInput).clear();
        searchInput.sendKeys(text);
        clickSearchButton();
    }

    public void clickSearchButton() {
        waitForClickAbilityOf(searchButton).click();
    }

    public void clickCartButton() {
        waitForClickAbilityOf(cartButton).click();
    }
}
