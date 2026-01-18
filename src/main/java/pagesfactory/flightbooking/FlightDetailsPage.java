package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import pagesfactory.CommonPage;
import utils.DriverFactory;

public class FlightDetailsPage extends CommonPage {


    private final By passengers = By.id("passCount");
    private final By continueButton = By.id("findFlights");

    public FlightDetailsPage(DriverFactory driverFactory) {
        super(driverFactory);
    }

    public void waitPage(){
        wait.visibilityOf(passengers);
    }

     public void selectNumberOfPassengers(String noOfPassengers){
         waitPage();
         mySelenium.selectByValue(passengers,noOfPassengers);
     }

     public void goToBookFlightPage(){
        mySelenium.click(continueButton);
     }


}
