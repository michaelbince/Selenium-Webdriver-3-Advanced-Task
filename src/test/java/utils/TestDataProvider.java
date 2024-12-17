package utils;

import org.testng.annotations.DataProvider;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TestDataProvider {

    private static final String TEST_DATA_FILE = "src/test/java/resources/testdata/book_test_data.json";
    private static final Map<String, Object> testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
    private static final String BOOK1 = "validBook";
    private static final String BOOK_NAME = "bookName";
    private static final String MINIMUM_BOOKS_SHOULD_BE_FOUND = "minimumBooksShouldBeFound";
    private static final String KINDLE_FORMAT = "kindleFormat";
    private static final String PAPERBACK_FORMAT = "paperbackFormat";
    private static final String BOOK_ABOUT = "bookAbout";



    @DataProvider(name = "validBook")
    public static Object[][] provideValidBook() {
        Map<String, String> book1 = (Map<String, String>) testData.get(BOOK1);
        return new Object[][]{
                {book1.get(BOOK_NAME), book1.get(MINIMUM_BOOKS_SHOULD_BE_FOUND)}
        };
    }

    @DataProvider(name = "validBookWithAdditionalOrdering")
    public static Object[][] provideValidBookWithAdditionalOrdering() {
        Map<String, String> book1 = (Map<String, String>) testData.get(BOOK1);
        return new Object[][]{
                {book1.get(BOOK_NAME),
                        book1.get(MINIMUM_BOOKS_SHOULD_BE_FOUND),
                        book1.get(KINDLE_FORMAT),
                        book1.get(PAPERBACK_FORMAT),
                        book1.get(BOOK_ABOUT)}
        };
    }

}
