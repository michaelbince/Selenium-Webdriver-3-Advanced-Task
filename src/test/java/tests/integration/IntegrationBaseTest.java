package tests.integration;

import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.DriverManager;

public class IntegrationBaseTest {
    protected final static String baseURL = "https://www.kruidvat.nl/";

    protected WebDriver driver;

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://www.kruidvat.nl/api/v2/kvn";
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUpUI(@Optional("firefox") String browser){
        DriverManager.setDriver(browser);
        driver = DriverManager.getDriver();
    }

    @AfterMethod
    public void tearDown(){
        DriverManager.quitDriver();
    }
}
