package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pagesfactory.CommonPage;
import utils.DriverManager;

public class FlightConfirmationPage extends CommonPage {



    private final By flightConfirmationNumber = By.xpath("//font[contains(text(), 'Flight Confirmation')]");
    private final By totalPrice = By.xpath("(//font[contains(text(), 'USD')])[2]");
    private final By signOffLink = By.linkText("SIGN-OFF");



    public FlightConfirmationPage(DriverManager driverManager) {
        super(driverManager);
    }

    public void waitPage(){
        wait.visibilityOf(flightConfirmationNumber);
    }

    public WebElement getConfirmationNumber(){
        waitPage();
        return mySelenium.findElement(flightConfirmationNumber);
    }

    public WebElement getTotalPrice(){
        return mySelenium.findElement(totalPrice);
    }

    public void signOff(){
        mySelenium.click(signOffLink);
    }

}
