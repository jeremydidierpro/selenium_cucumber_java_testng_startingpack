package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final Logger logger = LogManager.getLogger(DriverManager.class);

    public void initializeDriver(DriverFactory driverFactory){
        logger.info(() -> "Initializing the driver for thread: %s".formatted(Thread.currentThread().getName()));
        driver.set(driverFactory.createDriver());
        logger.info(() -> "The driver has been initialized for thread: %s".formatted(Thread.currentThread().getName()));
    }

    public WebDriver getDriver(){
        return driver.get();
    }

    public void quitDriver() {
        WebDriver currentDriver = driver.get();

        try {
            if (currentDriver != null) {
                logger.info(() -> "Closing the driver for thread: %s".formatted(Thread.currentThread().getName()));
                currentDriver.quit();
                logger.info(() -> "The driver has been closed for thread: %s".formatted(Thread.currentThread().getName()));
            } else {
                logger.info(() -> "No driver found to close for thread: %s".formatted(Thread.currentThread().getName()));
            }
        } finally {
            driver.remove();
        }
    }
}
