package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final String GRID_URL = System.getProperty("gridUrl", "http://localhost:4444/wd/hub");

    private DriverManager() {
    }

    private static WebDriver createDriver(String browser) {
        if (isGridEnabled()) {
            return createRemoteDriver(browser);
        } else {
            return createLocalDriver(browser);
        }
    }

    private static boolean isGridEnabled() {
        return GRID_URL != null && !GRID_URL.isEmpty();
    }

    private static WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptions());
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(new EdgeOptions());
            }
            default -> throw new IllegalArgumentException("Unsupported browser " + browser
                    + " Please use 'chrome', 'firefox', or 'edge'.");
        }
    }

    private static WebDriver createRemoteDriver(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.toLowerCase());

        try {
            return new RemoteWebDriver(new URL(GRID_URL), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RemoteWebDriver for browser: " + browser, e);
        }
    }

    public static void setDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = createDriver(browser);
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
        }
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call setDriver() before getDriver().");
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
