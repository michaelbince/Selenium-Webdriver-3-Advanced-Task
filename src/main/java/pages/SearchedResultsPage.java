package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

public class SearchedResultsPage extends BasePage {

    @FindBy(css = "div[data-component-type='s-search-result']")
    private List<WebElement> searchResults;

    @FindBy(css = "div[data-component-type='s-search-result'] h2")
    private List<WebElement> bookTitles;

    @FindBy(css = "[id*='p_n_feature_nine_browse-bin/'] input")
    private WebElement englishLanguageCheckbox;

    public long numberOfBooksByTitle(String searchText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));

        return bookTitles.stream()
                .filter(title -> title.getText().toLowerCase().contains(searchText.toLowerCase()))
                .count();
    }

    public List<String> getBookTitles() {
        wait.until(ExpectedConditions.visibilityOfAllElements(bookTitles));

        return bookTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void selectEnglishLanguageFilter(){
        moveToElement(englishLanguageCheckbox);
        selectCheckBox(englishLanguageCheckbox);
    }

    public void clickBookTitleByIndex(int index) {
        if (index < 1 || index > bookTitles.size()) {
            throw new IllegalArgumentException("Index " +
                    index + " is out of bounds. Total books: " +
                    bookTitles.size());
        }
        WebElement bookToClick = bookTitles.get(index - 1);
        scrollToElement(bookToClick);
        waitForClickAbilityOf(bookToClick);
        bookToClick.click();
    }
}
