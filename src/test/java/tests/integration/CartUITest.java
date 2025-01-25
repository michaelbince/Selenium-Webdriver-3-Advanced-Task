package tests.integration;

import api.client.AddProductToCartApiClient;
import api.client.CreateCartApiClient;
import api.models.ProductEntryRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.KruidvatHomePage;
import utils.BrowserDataStorage;
import utils.CartTestDataProvider;

import java.util.Map;


public class CartUITest extends IntegrationBaseTest {

    private CreateCartApiClient createCartApiClient;
    private AddProductToCartApiClient addProductToCartApiClient;

    private KruidvatHomePage kruidvatHomePage;

    @BeforeMethod
    public void setUp() {
        createCartApiClient = new CreateCartApiClient();
        addProductToCartApiClient = new AddProductToCartApiClient();
        kruidvatHomePage = new KruidvatHomePage();
    }


    @Test(dataProvider = "addProductTestDataToVerifyFromUI", dataProviderClass = CartTestDataProvider.class, description = "Add a product to the cart and verify the response details.")
    public void testAddProductToCart(String productCode, String productName, int quantity, int expectedStatusCode, Map<String, Object> responseAssertions) {
        Response createCartResponse = createCartApiClient.createCart();
        String cartId = createCartResponse.jsonPath().getString("guid");
        Assert.assertNotNull(cartId, "Cart ID (guid) should not be null");

        ProductEntryRequest requestBody = new ProductEntryRequest();
        ProductEntryRequest.Product product = new ProductEntryRequest.Product();
        product.setCode(productCode);
        requestBody.setProduct(product);
        requestBody.setQuantity(quantity);

        Response addProductResponse = addProductToCartApiClient.addProductToCart(cartId, requestBody);
        Assert.assertEquals(addProductResponse.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);

        driver.navigate().to(baseURL);
        BrowserDataStorage.clearAllData(driver);
        BrowserDataStorage.addCookie(driver, "kvn-car", cartId);
        driver.navigate().refresh();

        kruidvatHomePage.goToCart();

        String actualProductName = addProductResponse.jsonPath().getString("entry.product.name");
        Assert.assertEquals(actualProductName, productName, "Expected product code does not match");


    }
}
