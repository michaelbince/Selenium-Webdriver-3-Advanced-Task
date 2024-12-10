package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BrowserDataStorage {

    public static void clearCookies(WebDriver driver) {
        driver.manage().deleteAllCookies();
    }

    public static void clearLocalStorage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
    }

    public static void clearSessionStorage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.sessionStorage.clear();");
    }

    public static void clearAllData(WebDriver driver) {
        clearCookies(driver);
        clearLocalStorage(driver);
        clearSessionStorage(driver);
    }
}
