package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import pagesfactory.CommonPage;
import utils.DriverManager;

public class BookFlightPage extends CommonPage {

    private final By firstSubmitBtn = By.id("reserveFlights");
    private final By secondSubmitBtn = By.id("buyFlights");

    public BookFlightPage(DriverManager driverManager) {
        super(driverManager);
    }

    public void clickOnFirstSubmit(){
        mySelenium.click(firstSubmitBtn);
    }

    public void gotToFlightConfirmationPage(){
        mySelenium.click(secondSubmitBtn);
    }



}
