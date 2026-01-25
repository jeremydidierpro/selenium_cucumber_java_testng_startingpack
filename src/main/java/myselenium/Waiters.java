package myselenium;


import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Waiters {
    protected WebDriver driver;
    protected WebDriverWait waiter;
    protected final Logger logger;


    public Waiters(WebDriver driver){
        this.driver     = driver;
        this.waiter     = new WebDriverWait(this.driver,Duration.ofSeconds(5));
        logger = LogManager.getLogger(Waiters.class);
    }

    /**
     * Pause the thread for the given number of milliseconds.
     * @param millis the time to wait, in milliseconds
     */
    public void sleep(int millis){
        Uninterruptibles.sleepUninterruptibly(millis, TimeUnit.MILLISECONDS);
    }


    private WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public void visibilityOf(By locator){
        try {
            waiter.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void visibilityOf(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void visibilityOf_NoCatch(By locator) {
        waiter.withTimeout(Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until the element contains the expected text.
     * @param locator the element locator
     * @param text the text expected to be present in the element
     */
    public void elementToContainsText(By locator, String text){
        try {
            waiter.until(ExpectedConditions.textToBePresentInElement(driver.findElement(locator), text));
        }catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    /**
     * Needed for waiting element to have a certain text
     * @param locator element to wait
     * @param text to be present in the element
     * @param timeOutInSeconds timeout
     */
    public void elementToContainsText(By locator, String text, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(locator), text));
        }catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public boolean waitAndVerifyVisibilityOf(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        }catch(NoSuchElementException | TimeoutException e){
            return false;
        }
        return true;
    }

    public WebElement elementToBePresent(By selector){
        try {
            return waiter.until(ExpectedConditions.presenceOfElementLocated(selector));
        }catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public WebElement elementToBePresent(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void allElementsToBePresent(By locator){
        try {
            waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void allElementsToBePresent(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public boolean waitAndVerifyPresenceOfAll(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            return false;
        }
        return true;
    }

    public void untilInvisibilityOf(By locator){
        try {
            waiter.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
        } catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void untilInvisibilityOf(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try{
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
        } catch(NoSuchElementException | StaleElementReferenceException | TimeoutException  e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public WebElement elementToBeClickable(By locator){
        try{
            return waiter.until(ExpectedConditions.elementToBeClickable(locator));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public WebElement elementToBeClickable(By locator, int timeOutInSeconds){
        this.waiter = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        try{
            return waiter.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void elementNotToBeClickable(By locator){
        try{
            waiter.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(driver.findElement(locator))));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void elementToBeSelected(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try{
            wait.until(ExpectedConditions.elementToBeSelected(driver.findElement(locator)));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void elementToBeSelected(By locator) {
        try{
            waiter.until(ExpectedConditions.elementToBeSelected(driver.findElement(locator)));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void elementNotToBeSelected(By locator, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try{
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(driver.findElement(locator))));
        }catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void titleToBe(String expectedTitle){
        try {
            waiter.until(ExpectedConditions.titleIs(expectedTitle));
        } catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void urlToBe(String url){
        try {
            waiter.until(ExpectedConditions.urlToBe(url));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void urlContains(String fractionOfUrl){
        try {
            waiter.until(ExpectedConditions.urlContains(fractionOfUrl));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void urlContains(String fractionOfUrl, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.urlContains(fractionOfUrl));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void numberOfElementsToBe(By locator, int numberOfExpectedElements){
        try {
            waiter.until(ExpectedConditions.numberOfElementsToBe(locator, numberOfExpectedElements));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void numberOfElementsToBe(By locator,int numberOfExpectedElements, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(locator, numberOfExpectedElements));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void numberOfWindowsToBe(int numberOfExpectedWindows){
        try{
            waiter.until(ExpectedConditions.numberOfWindowsToBe(numberOfExpectedWindows));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void numberOfWindowsToBe(int numberOfExpectedWindows, int timeOutInSeconds){
        WebDriverWait wait = getWait(timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfExpectedWindows));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void elementToBeEnabled(By locator){
        try {
            waiter.until(ExpectedConditions.not(ExpectedConditions.attributeContains(driver.findElement(locator),  "disabled", "disabled")));
            sleep(500);
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }


    public void attributeToBePresent(By locator, String attribute, int timeOutInSeconds) {
        logger.info(() -> "****** Waiting for attribute '" + attribute + "' to be present.");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
                    .until(d -> driver.findElement(locator).getAttribute(attribute) != null);
        } catch (TimeoutException e) {
            logger.error(() -> "Attribute '" + attribute + "' was not present after " + timeOutInSeconds + " seconds.", e);
            throw e;
        }
    }

    public void attributeToContainValue(By locator, String attribute, String value, int timeOutInSeconds) {
        WebDriverWait wait = getWait(timeOutInSeconds);
        try{
            wait.until(ExpectedConditions.attributeContains(driver.findElement(locator), attribute, value));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void attributeToContainValue(By locator, String attribute, String value) {
        try{
            waiter.until(ExpectedConditions.attributeContains(driver.findElement(locator), attribute, value));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
        sleep(200);
    }

    public void attributeToBeNotEmpty(By locator, String attribute) {
        try{
            waiter.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(locator), attribute));
        }catch (TimeoutException e){
            logger.error(() -> e);
            throw new TimeoutException(e);
        }
    }

    public void attributeToBeDifferentThan(By locator, String attribute, String value, int timeOutInSeconds) {
        logger.info(() -> "****** Waiting for attribute :: " + attribute + " to be different than :: " + value);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
                    .until(d -> {
                        String attrValue = driver.findElement(locator).getAttribute(attribute);
                        return attrValue != null && !attrValue.isBlank() && !attrValue.equalsIgnoreCase(value);
                    });
        } catch (TimeoutException e) {
            logger.error(() -> "Attribute '" + attribute + "' did not change from '" + value + "' after " + timeOutInSeconds + " seconds.", e);
            throw e;
        }
    }
}