package tests.ui;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BookPage;
import pages.SearchComponentPage;
import pages.SearchedResultsPage;
import pages.ShoppingCartPage;

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

    @Test
    public void verifySearchedResultsAreRelatedToTheSearching(){
        searchComponentPage.search("Thinking in Java");
        long numberOfBooksByTitle = searchedResultsPage.numberOfBooksByTitle("Thinking in Java");
        Assert.assertTrue(numberOfBooksByTitle >= 3, "The number should be at least "+ 3 + " but i was " + numberOfBooksByTitle);
    }

    @Test
    public void verifySearchedResultsChangeAfterApplyingFilters(){
        searchComponentPage.search("Thinking in Java");

        List<String> firstSearchedResults = searchedResultsPage.getBookTitles();

        searchedResultsPage.selectEnglishLanguageFilter();

        List<String> secondSearchedResults = searchedResultsPage.getBookTitles();

        Assert.assertFalse(firstSearchedResults.equals(secondSearchedResults), "The book titles should be different");
    }

    @Test
    public void verifyBookIsAddedToTheBasket(){
        searchComponentPage.search("Thinking in Java");
        searchedResultsPage.selectEnglishLanguageFilter();
        searchedResultsPage.clickBookTitleByIndex(1);
        Assert.assertTrue(bookPage.getBookAddedTittle().contains("Thinking in Java"),
                "Book title does not contain the expected book searched: 'Thinking in Java'.");
        bookPage.addBookToCart();
        searchComponentPage.clickCartButton();
        Assert.assertTrue(shoppingCartPage.getBookInCartTittle().contains("Thinking in Java"),
                "Book title does not contain the expected book searched: 'Thinking in Java'.");
        shoppingCartPage.proceedToCheckoutWithAddedBooks();
    }
}
