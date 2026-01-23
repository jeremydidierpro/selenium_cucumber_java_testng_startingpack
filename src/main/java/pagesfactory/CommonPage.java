package pagesfactory;

import myselenium.MySelenium;
import myselenium.Waiters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class CommonPage {
    protected final WebDriver driver;
    protected final Waiters wait;
    protected final MySelenium mySelenium;

    public CommonPage(DriverFactory driverFactory){
        this.driver     = driverFactory.getDriver();
        this.wait       = new Waiters(this.driver);
        this.mySelenium = new MySelenium(this.driver);
    }

    /***
     * Here you can centralize all common actions performed on your specific website
     * (e.g., saving inner pages, waiting for loaders, refreshing inner pages).
     */

}


