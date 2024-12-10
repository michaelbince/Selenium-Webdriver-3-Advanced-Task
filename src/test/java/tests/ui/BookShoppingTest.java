package tests.ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BookPage;
import pages.SearchComponentPage;
import pages.SearchedResultsPage;
import pages.ShoppingCartPage;
import utils.TestDataProvider;

import java.util.List;

public class BookShoppingTest extends BaseTest {
    private SearchComponentPage searchComponentPage;
    private SearchedResultsPage searchedResultsPage;
    private BookPage bookPage;
    private ShoppingCartPage shoppingCartPage;


    @BeforeMethod
    public void setUp(){
        searchComponentPage = new SearchComponentPage();
        searchedResultsPage = new SearchedResultsPage();
        bookPage = new BookPage();
        shoppingCartPage = new ShoppingCartPage();
    }

    @Test(description = "verify the searched results are related to the book searched",
            dataProvider = "validBook",
            dataProviderClass = TestDataProvider.class)
    public void verifySearchedResultsAreRelatedToTheSearching(String bookName, int minimumBooksShouldBeFound){
        searchComponentPage.search(bookName);
        long numberOfBooksByTitle = searchedResultsPage.numberOfBooksByTitle(bookName);
        Assert.assertTrue(numberOfBooksByTitle >= minimumBooksShouldBeFound, "The number should be at least "+ minimumBooksShouldBeFound + " but it was " + numberOfBooksByTitle);
    }

    @Test(description = "Verify searched results are different after some filter were applied",
            dataProvider = "validBook",
            dataProviderClass = TestDataProvider.class)
    public void verifySearchedResultsChangeAfterApplyingFilters(String bookName, int minimumBooksShouldBeFound){
        searchComponentPage.search(bookName);

        List<String> firstSearchedResults = searchedResultsPage.getBookTitles();

        searchedResultsPage.selectEnglishLanguageFilter();

        List<String> secondSearchedResults = searchedResultsPage.getBookTitles();

        Assert.assertFalse(firstSearchedResults.equals(secondSearchedResults), "The book titles should be different");
    }

    @Test(description = "Verify book selected was added to the basket",
            dataProvider = "validBook",
            dataProviderClass = TestDataProvider.class)
    public void verifyBookIsAddedToTheBasket(String bookName, int minimumBooksShouldBeFound){
        searchComponentPage.search(bookName);
        searchedResultsPage.selectEnglishLanguageFilter();
        searchedResultsPage.clickBookTitleByIndex(1);

        Assert.assertTrue(bookPage.getBookAddedTittle().contains(bookName),
                "Book title does not contain the expected book searched: "+ bookName);
        bookPage.addBookToCart();

        searchComponentPage.clickCartButton();

        Assert.assertTrue(shoppingCartPage.getBookInCartTittle().contains(bookName),
                "Book title does not contain the expected book searched: "+ bookName);

        shoppingCartPage.proceedToCheckoutWithAddedBooks();
    }
}
