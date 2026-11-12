package utils;

import exceptions.ValueNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private final Logger logger = LogManager.getLogger(DriverFactory.class);

    /**
     * This method verifies the provided browser, the provided host, and whether remote mode is enabled. Then it sets the correct driver.
     */
    public WebDriver createDriver() {
        WebDriver driver;
        String browser = Config.browser().toLowerCase();
        if (Config.remote()) {
            this.logger.info(() -> "Trying to set up the %s remote driver.".formatted(browser));
            driver = setupRemoteWebDriver(browser);
        } else {
            this.logger.info(() -> "Trying to set up the %s driver.".formatted(browser));
            driver = setupLocalWebDriver(browser);
        }
        driver.manage().deleteAllCookies();
        setDriverSize(driver, browser);
        return driver;
    }

    /**
     * This method sets up the remote WebDriver.
     */
    private WebDriver setupRemoteWebDriver(String browser) {
        Capabilities capabilities;
        switch (browser) {
            case "chrome" -> capabilities = new ChromeOptions();
            case "edge" -> capabilities = new EdgeOptions();
            case "firefox" -> capabilities = new FirefoxOptions();
            default -> throw new ValueNotFoundException("Unsupported browser : %s".formatted(Config.browser()));
        }
        String remoteUrl = "http://%s:%s".formatted(Config.host(), Config.port());
        try {
            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (MalformedURLException e) {
            logger.error(() -> "Incorrect remote url: %s".formatted(remoteUrl));
            throw new RuntimeException(e);
        }
    }

    /**
     * This method sets up the webDriver
     */
    private WebDriver setupLocalWebDriver(String browser) {
        switch (browser) {
            case "chrome" -> {
                return new ChromeDriver();
            }
            case "firefox" -> {
                return new FirefoxDriver();
            }
            case "edge" -> {
                return new EdgeDriver();
            }
            case "chrome_headless" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
                return new ChromeDriver(chromeOptions);
            }
            case "firefox_headless" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                return new FirefoxDriver(firefoxOptions);
            }
            case "edge_headless"-> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");
                return new EdgeDriver(edgeOptions);
            }
            default -> throw new ValueNotFoundException("Unsupported browser: %s".formatted(Config.browser()));
        }
    }

    /**
     * This method sets up the default browser size.
     */
    private void setDriverSize(WebDriver driver, String browser) {
        // We have to set a size for chrome_headless because the maximize method does not work on it.
        if (browser.equals("chrome_headless")) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }
    }

}



