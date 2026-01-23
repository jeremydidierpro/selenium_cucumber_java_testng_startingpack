package myselenium;

import exceptions.ElementNotClickableException;
import exceptions.NoTypeableElementException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class MySelenium {
    private WebDriver driver;
    private Waiters waiters;
    private static final int DEFAULT_ATTEMPTS = 10;
    private final Logger logger = LogManager.getLogger(MySelenium.class);



    public MySelenium(WebDriver driver){
        this.driver = driver;
        this.waiters = new Waiters(this.driver);

    }

    public WebElement findElement(By locator){
        logger.log(Level.INFO, () -> String.format("****** Trying to find elenment with locator %s", locator));
        return this.waiters.elementToBePresent(locator);
    }

    public List<WebElement> findElements(By locator){
        logger.log(Level.INFO, () -> String.format("****** Trying to find elenment with locator %s", locator));
        this.waiters.allElementsToBePresent(locator);
        return this.driver.findElements(locator);
    }

    /**
     * Thanks to this method, we try to enter 10 times the value before throwing an error
     * @param locator to send keys in.
     * @param value to send
     */
    public WebElement type(By locator, String value){
        int attempts = 0;
        while (attempts < DEFAULT_ATTEMPTS) {
            try {
                WebElement input = this.waiters.elementToBeClickable(locator);
                input.clear();
                input.sendKeys(value);
                logger.info("Typed value '{}' into element {}", value, locator);
                return input;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                logger.debug("Retry typing into {} (attempt {}/{})", locator, attempts + 1, DEFAULT_ATTEMPTS);
                waiters.sleep(100);
            }
            attempts++;
        }
        throw new NoTypeableElementException("Impossible to type value after " + DEFAULT_ATTEMPTS + " attempts on element " + locator
        );
    }


    /**
     * Thanks to this method, we try to click 10 times before throwing an error
     * @param locator
     */
    public WebElement click(By locator){
        int attempts = 0;
        while (attempts < DEFAULT_ATTEMPTS) {
            try {
                WebElement element = waiters.elementToBeClickable(locator);  // <-- fresh element
                element.click();
                logger.info("Clicked element {}", locator);
                return element;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                logger.debug("Retry click on {} (attempt {}/{})", locator, attempts+1, DEFAULT_ATTEMPTS);
                waiters.sleep(100);
            }
            attempts++;
        }
        throw new ElementNotClickableException("Element not clickable after " + DEFAULT_ATTEMPTS + " attempts on " + locator);
    }

    public void goTo(String url){
        logger.info("Accessing url {}", url);
        this.driver.get(url);
    }

    public String getCurrentUrl(){
        return this.driver.getCurrentUrl();
    }

    public String getTitle(){
        return this.driver.getTitle();
    }

    /**
     * If the checkbox is unselected, select it.
     * @param locator
     */
    public void selectCheckBox(By locator){
        WebElement element = findElement(locator);
        if(!element.isSelected()){
            click(locator);
        }
    }
    /**
     * If the checkbox is selected, unselect it.
     * @param locator
     */
    public void unselectCheckBox(By locator){
        WebElement element = findElement(locator);
        if(element.isSelected()){
            click(locator);
        }
    }
    /**
     * This method will clear the input and enter thr new value
     * @param locator
     * @param value
     */
    public void forceClearingAndType(By locator, String value){
        logger.log(Level.INFO, () -> ("****** Trying to force clear & enter '"+ value + "' in " + locator.toString()));
        WebElement element = findElement(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        type(locator, value);
    }
    /**
     * For some inputs, the value is not text but in the value attribute.
     * To clear, we have to do backspaces
     * @param locator to clear.
     */
    public void forceClear(By locator){
        WebElement element = findElement(locator);
        while (!element.getAttribute("value").isBlank()) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    /**
     * For some inputs, the value is taken into account after pushing TAB button.
     * @param locator to send keys in.
     * @param value to send
     */
    public void typeAndPressTab(By locator, String value){
        WebElement element = type(locator,value);
        element.sendKeys(Keys.TAB);
    }

    /**
     * For some inputs, the value is taken into account after pushing ENTER button.
     * @param locator to send keys in.
     * @param value to send
     */
    public void typeAndPressTEnter(By locator, String value){
        WebElement element = type(locator,value);
        element.sendKeys(Keys.ENTER);
    }

    public void selectByValue(By locator, String value){
        logger.log(Level.INFO, () -> ("****** Trying to select value '"+ value + "' in this select element : " + locator.toString()));
        WebElement element = findElement(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectByVisibleText(By locator, String value){
        logger.log(Level.INFO, () -> ("****** Trying to select text '"+ value + "' in this select element : "+ locator.toString()));
        WebElement element = findElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public void selectByIndex(By locator, int index){
        logger.log(Level.INFO, () -> ("****** Trying to select index '"+ index + "' in the select element : " + locator.toString()));
        WebElement element = findElement(locator);
        Select select = new Select(element);
        select.selectByIndex(index);
    }
    /**
     * By giving the first window, this methods return the second window in order to switch to it.
     * @param firstWindow
     * @return window Id
     */
    public String getSecondWindow(String firstWindow){
        for(String window: driver.getWindowHandles()){
            if(!window.equals(firstWindow)){
                return window;
            }
        }
        return  null;
    }
    /**
     * This method return true if element is present and displayed and false if element is not present
     * @param locator
     * @return boolean
     */
    public boolean isElementDisplayed(By locator){
        try {
            waiters.visibilityOf_NoCatch(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}