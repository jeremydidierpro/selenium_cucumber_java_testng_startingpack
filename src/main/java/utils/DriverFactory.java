package utils;

import exceptions.NotFoundValueException;
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
    private WebDriver driver;
    private final Logger logger = LogManager.getLogger(DriverFactory.class);

    /**
     * This method verifies the provided browser, the provided host and if the remote is activated. Then set the right browser
     */
    public void setUpDriver() {
        if (Config.remote()) {
            this.logger.info(() -> "Trying to set up the %s remote driver.".formatted(Config.browser()));
            setupRemoteWebDriver();
        } else {
            this.logger.info(() -> "Trying to set up the %s driver.".formatted(Config.browser()));
            setupLocalWebDriver();
        }
        driver.manage().deleteAllCookies();
        setDriverSize();
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * This method set up the remote webdriver
     */
    public void setupRemoteWebDriver() {
        Capabilities capabilities;
        switch (Config.browser()) {
            case "chrome" -> capabilities = new ChromeOptions();
            case "edge" -> capabilities = new EdgeOptions();
            case "firefox" -> capabilities = new FirefoxOptions();
            default -> throw new NotFoundValueException("Unsupported browser : %s".formatted(Config.browser()));
        }
        String remoteUrl = "http://%s:%s".formatted(Config.host(), Config.port());
        try {
            driver = new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (MalformedURLException e) {
            logger.error(() -> "Incorrect remote url: %s".formatted(remoteUrl));
            throw new RuntimeException(e);
        }
    }

    /**
     * This method set up the webDriver
     */
    public void setupLocalWebDriver() {
        switch (Config.browser().toLowerCase()) {
            case "chrome" -> driver = new ChromeDriver();
            case "firefox" -> driver = new FirefoxDriver();
            case "edge" -> driver = new EdgeDriver();
            case "chrome_headless" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox_headless" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "edge_headless"-> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(edgeOptions);
            }
            default -> throw new NotFoundValueException("Unsupported browser: %s".formatted(Config.browser()));
        }
    }

    /**
     * This method set up the default size of the browser.
     */
    public void setDriverSize() {
        //We have to set a size to chrome_headless because the maximise method is not working on it.
        if (Config.browser().equalsIgnoreCase("chrome_headless")) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }
    }

}



