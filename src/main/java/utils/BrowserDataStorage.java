package utils;

import org.openqa.selenium.Cookie;
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

    public static void addToSessionStorage(WebDriver driver, String key, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.sessionStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public static String getFromSessionStorage(WebDriver driver, String key) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return window.sessionStorage.getItem(arguments[0]);", key);
    }

    public static void addCookie(WebDriver driver, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
    }

}
