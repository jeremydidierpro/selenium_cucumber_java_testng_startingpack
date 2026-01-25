package steps.hooks;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import functions.sql.DBAccounts;
import functions.sql.DataBaseManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import testcontext.CommonInstance;
import utils.Config;
import utils.DriverFactory;

public class GlobalHooks {
    private final DriverFactory driverFactory;
    private WebDriver driver;
    private CommonInstance commonInstance;
    private final Logger logger = LogManager.getLogger(GlobalHooks.class);

    public GlobalHooks(DriverFactory driverFactory, CommonInstance commonInstance){
        this.driverFactory      = driverFactory;
        this.commonInstance      = commonInstance;
    }

    @Before
    public void testInitialization(Scenario scenario){
        setupDriver();
        Markup markupEnv = MarkupHelper.createLabel("Environment: " + Config.environment(), ExtentColor.GREEN);
        ExtentCucumberAdapter.getCurrentStep().info(markupEnv);
        Markup markupBrowser = MarkupHelper.createLabel("Browser: " + Config.browser(), ExtentColor.GREEN);
        ExtentCucumberAdapter.getCurrentStep().info(markupBrowser);
        logger.info(()-> "** The test '"+ scenario.getName() +"' has just started in "+ Config.environment());
    }


    @After(order = 1)
    public void assertAllAtEnd() {
        commonInstance.softly().assertAll();
    }

    /**
     * Second method run after the test. The goal is to attach a screenshot to the report if the scenario failed, then close the driver.
     * @param scenario Test Case
     */
    @After(order =0)
    public void testClosure(Scenario scenario) {
        try{
            if (scenario.isFailed()) {
                pasteScreenShotInReports(scenario);
            }
        } finally{
            driverFactory.quitDriver();
            DBAccounts.closeDBAccounts();
            logger.info(() -> "** The test '"+ scenario.getName() +"' has finished ");
            logger.info(() -> "The driver has been closed.\n");
        }
    }

    public void setupDriver(){
        driverFactory.setUpDriver();
        this.driver = driverFactory.getDriver();
        logger.info(() -> "The driver is UP.");
    }

    public void pasteScreenShotInReports(Scenario scenario){
        String screenshotName = scenario.getName().replace(" ","_");
        TakesScreenshot newScreen = (TakesScreenshot)driver;
        String scrshot = newScreen.getScreenshotAs(OutputType.BASE64);
        ExtentCucumberAdapter.getCurrentStep().fail(screenshotName+".png", MediaEntityBuilder.createScreenCaptureFromBase64String(scrshot).build());
    }

}
