package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateCartApiClient {
    private static final String CREATE_CART_ENDPOINT = "/users/anonymous/carts/";

    public Response createCart() {
        return RestAssured.given()
                .contentType("application/json;charset=UTF-8")
                .header("Accept", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .post(CREATE_CART_ENDPOINT);
    }
}
