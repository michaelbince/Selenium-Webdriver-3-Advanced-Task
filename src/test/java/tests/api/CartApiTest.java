package tests.api;

import api.client.AddProductToCartApiClient;
import api.client.CreateCartApiClient;
import api.models.ProductEntryRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.CartTestDataProvider;

import java.util.Map;

public class CartApiTest extends APIBaseTest {

    private CreateCartApiClient createCartApiClient;
    private AddProductToCartApiClient addProductToCartApiClient;

    @BeforeMethod
    public void setUp() {
        createCartApiClient = new CreateCartApiClient();
        addProductToCartApiClient = new AddProductToCartApiClient();
    }

    @Test(dataProvider = "createCartSchemaTestData", dataProviderClass = CartTestDataProvider.class, description = "Validate the cart JSON schema after creating a new cart.")
    public void testValidateCartJsonSchema(int expectedStatusCode, String jsonSchema) {
        Response createCartResponse = createCartApiClient.createCart();
        Assert.assertEquals(createCartResponse.getStatusCode(), expectedStatusCode, "Expected status code " + expectedStatusCode);
        createCartResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchema));
        System.out.println("Created Cart Response: " + createCartResponse.asString());
        String cartId = createCartResponse.jsonPath().getString("guid");
        Assert.assertNotNull(cartId, "Cart ID (guid) should not be null");
    }

    @Test(dataProvider = "createCartTestData", dataProviderClass = CartTestDataProvider.class, description = "Create a new cart and verify the HTTP status code.")
    public void testCreateCart(int expectedStatusCode) {
        Response response = createCartApiClient.createCart();
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected HTTP status code " + expectedStatusCode);
    }

    @Test(dataProvider = "addProductTestData", dataProviderClass = CartTestDataProvider.class, description = "Add a product to the cart and verify the response details.")
    public void testAddProductToCart(String productCode, int quantity, int expectedStatusCode, Map<String, Object> responseAssertions) {
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

        int actualQuantity = addProductResponse.jsonPath().getInt("entry.quantity");
        Assert.assertEquals(actualQuantity, quantity, "Expected quantity does not match");

        String actualProductCode = addProductResponse.jsonPath().getString("entry.product.code");
        Assert.assertEquals(actualProductCode, productCode, "Expected product code does not match");

        String contentType = addProductResponse.header("Content-Type");
        Assert.assertTrue(contentType.contains("application/json"), "Expected Content-Type header to contain 'application/json'");
    }
}
