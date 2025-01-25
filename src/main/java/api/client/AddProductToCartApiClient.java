package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddProductToCartApiClient {
    private static final String CART_ENTRY_ENDPOINT = "/users/anonymous/carts/{cartId}/entries?lang=nl";

    public Response addProductToCart(String cartId, Object requestBody) {
        return RestAssured.given()
                .contentType("application/json;charset=UTF-8")
                .header("Accept", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .pathParam("cartId", cartId)
                .body(requestBody)
                .post(CART_ENTRY_ENDPOINT);
    }
}
