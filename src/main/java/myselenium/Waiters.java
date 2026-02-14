package myselenium;


import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Waiters {
    protected WebDriver driver;
    protected final Logger logger;
    protected int defaultTimeout = 5;


    public Waiters(WebDriver driver){
        this.driver     = driver;
        logger = LogManager.getLogger(Waiters.class);
    }

    /**
     * Pause the thread for the given number of milliseconds.
     * @param millis the time to wait, in milliseconds
     */
    public void sleep(int millis){
        Uninterruptibles.sleepUninterruptibly(millis, TimeUnit.MILLISECONDS);
    }


    private WebDriverWait wait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public void visibilityOf(By locator){
        try {
            wait(defaultTimeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting element to be visible. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public void visibilityOf(By locator, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting element to be visible. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public boolean isVisible(By locator,int seconds) {
        try {
            wait(seconds).until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            );
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Wait until the element contains the expected text.
     * @param locator the element locator
     * @param text the text expected to be present in the element
     */
    public void elementToContainsText(By locator, String text){
        try {
            wait(defaultTimeout).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        }catch(TimeoutException  e){
            logger.error("Timeout waiting element to contain text={}. Locator={}, timeout={}s",
                    text, locator, defaultTimeout, e);
            throw e;
        }
    }

    /**
     * Needed for waiting element to have a certain text
     * @param locator element to wait
     * @param text to be present in the element
     * @param timeOutInSeconds timeout
     */
    public void elementToContainsText(By locator, String text, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        }catch(TimeoutException  e){
            logger.error("Timeout waiting element to contain text={}. Locator={}, timeout={}s",
                    text, locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public boolean waitAndVerifyVisibilityOf(By locator, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch(NoSuchElementException | TimeoutException e){
            return false;
        }
        return true;
    }

    public WebElement elementToBePresent(By locator){
        try {
            return wait(defaultTimeout).until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(TimeoutException e){
            logger.error("Timeout waiting element to be present. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public WebElement elementToBePresent(By locator, int timeOutInSeconds){
        try {
            return wait(timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch(TimeoutException e){
            logger.error("Timeout waiting element to be present. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public List<WebElement> allElementsToBePresent(By locator){
        try {
            return wait(defaultTimeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }catch(TimeoutException e){
            logger.error("Timeout waiting for all elements to be present. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public List<WebElement> allElementsToBePresent(By locator, int timeOutInSeconds){
        try {
            return wait(timeOutInSeconds).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch(TimeoutException e){
            logger.error("Timeout waiting for all elements to be present. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public boolean waitAndVerifyPresenceOfAll(By locator, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch(TimeoutException e){
            return false;
        }
        return true;
    }

    public void untilInvisibilityOf(By locator){
        try {
            wait(defaultTimeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch(TimeoutException e){
            logger.error("Timeout waiting for visibility of element. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public void untilInvisibilityOf(By locator, int timeOutInSeconds){
        try{
            wait(timeOutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch(TimeoutException  e){
            logger.error("Timeout waiting for visibility of element. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public WebElement elementToBeClickable(By locator){
        try{
            return wait(defaultTimeout).until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be clickable. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public WebElement elementToBeClickable(By locator, int timeOutInSeconds){
        try{
            return wait(timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be clickable. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public void elementNotToBeClickable(By locator){
        try{
            wait(defaultTimeout).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(locator)));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be not clickable. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public void elementToBeSelected(By locator, int timeOutInSeconds){
        try{
            wait(timeOutInSeconds).until(ExpectedConditions.elementToBeSelected(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be not clickable. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public void elementToBeSelected(By locator) {
        try{
            wait(defaultTimeout).until(ExpectedConditions.elementToBeSelected(locator));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be selected. Locator={}, timeout={}s",
                    locator, defaultTimeout, e);
            throw e;
        }
    }

    public void elementNotToBeSelected(By locator, int timeOutInSeconds){
        try{
            wait(timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(locator)));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the element to be selected. Locator={}, timeout={}s",
                    locator, timeOutInSeconds, e);
            throw e;
        }
    }

    public void titleToBe(String expectedTitle){
        try {
            wait(defaultTimeout).until(ExpectedConditions.titleIs(expectedTitle));
        } catch (TimeoutException e){
            logger.error("Timeout waiting for the title to be present. Title={}. timeout={}s",
                    expectedTitle, defaultTimeout, e);
            throw e;
        }
    }

    public void urlToBe(String url){
        try {
            wait(defaultTimeout).until(ExpectedConditions.urlToBe(url));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the url to be present. Url={}. timeout={}s",
                    url, defaultTimeout, e);
            throw e;
        }
    }

    public void urlContains(String fractionOfUrl){
        try {
            wait(defaultTimeout).until(ExpectedConditions.urlContains(fractionOfUrl));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the url to contains text. Text={}. timeout={}s",
                    fractionOfUrl, defaultTimeout, e);
            throw e;
        }
    }

    public void urlContains(String fractionOfUrl, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.urlContains(fractionOfUrl));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the url to contains text. Text={}. timeout={}s",
                    fractionOfUrl, timeOutInSeconds, e);
            throw e;
        }
    }

    public void numberOfElementsToBe(By locator, int numberOfExpectedElements){
        try {
            wait(defaultTimeout).until(ExpectedConditions.numberOfElementsToBe(locator, numberOfExpectedElements));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the number of element. Locator={}, number={}, timeout={}s",
                    locator, numberOfExpectedElements, defaultTimeout, e);
            throw e;
        }
    }

    public void numberOfElementsToBe(By locator,int numberOfExpectedElements, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.numberOfElementsToBe(locator, numberOfExpectedElements));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the number of element. Locator={}, number={}, timeout={}s",
                    locator, numberOfExpectedElements, timeOutInSeconds, e);
            throw e;
        }
    }

    public void numberOfWindowsToBe(int numberOfExpectedWindows){
        try{
            wait(defaultTimeout).until(ExpectedConditions.numberOfWindowsToBe(numberOfExpectedWindows));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the number of windows. number={}, timeout={}s",
                    numberOfExpectedWindows, defaultTimeout, e);
            throw e;
        }
    }

    public void numberOfWindowsToBe(int numberOfExpectedWindows, int timeOutInSeconds){
        try {
            wait(timeOutInSeconds).until(ExpectedConditions.numberOfWindowsToBe(numberOfExpectedWindows));
        }catch (TimeoutException e){
            logger.error("Timeout waiting for the number of windows. number={}, timeout={}s",
                    numberOfExpectedWindows, timeOutInSeconds, e);
            throw e;
        }
    }


    public void attributeToBePresent(By locator, String attribute, int timeOutInSeconds) {
        logger.info(() -> "****** Waiting for attribute '" + attribute + "' to be present.");
        try {
            wait(timeOutInSeconds)
                    .until(d -> d.findElement(locator).getAttribute(attribute) != null);
        } catch (TimeoutException e) {
            logger.error("Attribute '{}' was not present after {} seconds. Locator={}",
                    attribute, timeOutInSeconds, locator, e);
            throw e;
        }
    }

    public void attributeToContainValue(By locator, String attribute, String value, int timeOutInSeconds) {
        try{
            wait(timeOutInSeconds).until(ExpectedConditions.attributeContains(locator, attribute, value));
        }catch (TimeoutException e){
            logger.error("Attribute '{}' didn't contain value '{}' after {} seconds. Locator={}",
                    attribute,value, timeOutInSeconds, locator, e);
            throw e;
        }
    }

    public void attributeToContainValue(By locator, String attribute, String value) {
        try{
            wait(defaultTimeout).until(ExpectedConditions.attributeContains(locator, attribute, value));
        }catch (TimeoutException e){
            logger.error("Attribute '{}' didn't contain value '{}' after {} seconds. Locator={}",
                    attribute,value, defaultTimeout, locator, e);
            throw e;
        }
        sleep(200);
    }

    public void attributeToBeNotEmpty(By locator, String attribute, int timeoutInSeconds) {
        logger.info(() -> "****** Waiting for attribute '" + attribute + "' to be not empty.");
        try {
            wait(timeoutInSeconds)
                    .until(d -> {
                        String value = d.findElement(locator).getAttribute(attribute);
                        return value != null && !value.isBlank();
                    });

        } catch (TimeoutException e) {
            logger.error("Attribute '{}' was still empty after {} seconds. Locator={}",
                    attribute, timeoutInSeconds, locator, e);
            throw e;
        }
    }

    public void attributeToBeDifferentThan(By locator, String attribute, String value, int timeOutInSeconds) {
        logger.info(() -> "****** Waiting for attribute :: " + attribute + " to be different than :: " + value);
        try {
            wait(timeOutInSeconds)
                    .until(d -> {
                        String attrValue = d.findElement(locator).getAttribute(attribute);
                        return attrValue != null && !attrValue.isBlank() && !attrValue.equalsIgnoreCase(value);
                    });
        } catch (TimeoutException e) {
            logger.error("Attribute '{}' did not change from '{}' after {} seconds. Locator={}",
                    attribute, value, timeOutInSeconds, locator, e);
            throw e;
        }
    }
}