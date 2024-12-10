package tests.ui;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.BrowserDataStorage;
import utils.DriverManager;

public class BaseTest {
    protected final static String baseURL = "https://www.bookdepository.com/";

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        DriverManager.setDriver(browser);
        driver = DriverManager.getDriver();
        driver.navigate().to(baseURL);
        BrowserDataStorage.clearAllData(driver);
    }

    @AfterMethod
    public void tearDown(){
        DriverManager.quitDriver();
    }
}
