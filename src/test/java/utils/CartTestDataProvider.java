package utils;

import org.testng.annotations.DataProvider;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CartTestDataProvider {

    private static final String TEST_DATA_FILE = "src/test/resources/testdata/cart_test_data.json";
    private static final Map<String, Object> testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
    private static final String CART_TESTS = "cartTests";
    private static final String CREATE_CART = "createCart";
    private static final String ADD_PRODUCT = "addProduct";

    @DataProvider(name = "createCartSchemaTestData")
    public static Object[][] provideCreateCartSchemaTestData() {
        Map<String, Object> cartTests = (Map<String, Object>) testData.get(CART_TESTS);
        Map<String, Object> createCartData = (Map<String, Object>) cartTests.get(CREATE_CART);

        return new Object[][]{
                {
                        Integer.parseInt(createCartData.get("expectedStatusCode").toString()),
                        createCartData.get("jsonSchema").toString()
                }
        };
    }

    @DataProvider(name = "createCartTestData")
    public static Object[][] provideCreateCartTestData() {
        Map<String, Object> cartTests = (Map<String, Object>) testData.get(CART_TESTS);
        Map<String, Object> createCartData = (Map<String, Object>) cartTests.get(CREATE_CART);

        return new Object[][]{
                {
                        Integer.parseInt(createCartData.get("expectedStatusCode").toString())
                }
        };
    }

    @DataProvider(name = "addProductTestData")
    public static Object[][] provideAddProductTestData() {
        Map<String, Object> cartTests = (Map<String, Object>) testData.get(CART_TESTS);
        Map<String, Object> addProductData = (Map<String, Object>) cartTests.get(ADD_PRODUCT);

        return new Object[][]{
                {
                        addProductData.get("productCode").toString(),
                        Integer.parseInt(addProductData.get("quantity").toString()),
                        Integer.parseInt(addProductData.get("expectedStatusCode").toString()),
                        addProductData.get("responseAssertions") != null ? (Map<String, Object>) addProductData.get("responseAssertions") : null
                }
        };
    }
}
