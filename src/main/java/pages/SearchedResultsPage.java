package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchedResultsPage extends BasePage {

    @FindBy(css = "div[data-component-type='s-search-result']")
    private List<WebElement> searchResults;

    @FindBy(css = "div[data-component-type='s-search-result'] h2")
    private List<WebElement> bookTitles;

    @FindBy(css = "[id*='p_n_feature_nine_browse-bin/'] input")
    private WebElement englishLanguageCheckbox;

    @FindBy(css = "li[id*='p_n_feature_twenty_browse-bin/'] input")
    private WebElement kindleUnlimitedEligibleCheckbox;

    @FindBy(css = "li[id='p_n_feature_browse-bin/2656022011']")
    private WebElement paperbackCheckbox;

    @FindBy(css = "div.puisg-row [data-cy='price-recipe'] a.a-size-base")
    private List<WebElement> formatOfBooks;

    @FindBy(css = "div.puisg-row  a.a-text-bold")
    private List<WebElement> generalFormatOfBooks;

    @FindBy(css = "a.s-navigation-clear-link")
    private List<WebElement> clearFilterLinks;

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
        clickElementWhenFound(englishLanguageCheckbox);
    }

    public void selectKindleFilter(){
        clickElementWhenFound(kindleUnlimitedEligibleCheckbox);
    }

    public void selectPaperbackFilter(){
        clickElementWhenFound(paperbackCheckbox);
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

    public List<String> getMainFormatOfBooks() {
        wait.until(ExpectedConditions.visibilityOfAllElements(formatOfBooks));

        return formatOfBooks.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isAllBooksOfFormat(String format) {
        List<String> BooksFormat = getMainFormatOfBooks();
        if (format == null) {
            throw new IllegalArgumentException("Format cannot be null");
        }
        return BooksFormat.stream()
                .allMatch(currentFormat -> currentFormat.contains(format));
    }

    public void clearAllFilters() {
        for (WebElement filterLink : clearFilterLinks) {
            try {
                moveToElement(filterLink);
                selectCheckBox(filterLink);
            } catch (Exception e) {
                System.out.println("Failed to click the filter link: " + e.getMessage());
            }
        }
    }

    public List<String[]> getPairsOfBookFormatsForEachOne() {
        wait.until(ExpectedConditions.visibilityOfAllElements(generalFormatOfBooks));
        List<String[]> textPairs = new ArrayList<>();

        for (int index = 0; index < generalFormatOfBooks.size(); index += 2) {
            int nextBook = index + 1;
            String firstBook = generalFormatOfBooks.get(index).getText();
            String secondBook = (nextBook < generalFormatOfBooks.size()) ? generalFormatOfBooks.get(nextBook).getText() : null;
            textPairs.add(new String[]{firstBook, secondBook});
        }

        return textPairs;
    }

    public boolean isAllBooksOfFormatForTheTwoAvailable(String format) {
        List<String[]> BooksFormat = getPairsOfBookFormatsForEachOne();
        if (format == null) {
            throw new IllegalArgumentException("Format cannot be null");
        }
        return BooksFormat.stream()
                .allMatch(currentFormat -> currentFormat[0].contains(format) || currentFormat[1].contains(format));
    }


}
