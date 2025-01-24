package tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

abstract public class APIBaseTest {

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://www.kruidvat.nl/api/v2/kvn";
    }
}
