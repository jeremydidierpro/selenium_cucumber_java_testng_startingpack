package pagesfactory;

import myselenium.MySelenium;
import myselenium.Waiters;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class CommonPage {
    protected final WebDriver driver;
    protected final Waiters wait;
    protected final MySelenium mySelenium;

    public CommonPage(DriverManager driverManager){
        this.driver     = driverManager.getDriver();
        this.wait       = new Waiters(this.driver);
        this.mySelenium = new MySelenium(this.driver);
    }

    /***
     * Centralizes all common actions performed on the website
     * (e.g., saving inner pages, waiting for loaders, refreshing inner pages).
     */

}


