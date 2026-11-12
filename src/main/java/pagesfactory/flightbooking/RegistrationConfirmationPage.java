package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pagesfactory.CommonPage;
import utils.DriverManager;

public class RegistrationConfirmationPage extends CommonPage {
    private final By signinLink = By.partialLinkText("sign-in");
    private final By confirmationMsg = By.cssSelector("#register-confirm h1");
    private final By flightLink = By.id("flight-link");



    public RegistrationConfirmationPage(DriverManager driverManager) {
        super(driverManager);
    }

    public void waitPage(){
        wait.visibilityOf(signinLink);
    }

    public WebElement getConfirmationMsg(){
        return mySelenium.findElement(confirmationMsg);
    }

    public void goToFlightDetailsPage(){
        waitPage();
        mySelenium.click(flightLink);
    }

}
