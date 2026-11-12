package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import pagesfactory.CommonPage;
import utils.DriverManager;

public class FlightDetailsPage extends CommonPage {


    private final By passengers = By.id("passCount");
    private final By continueButton = By.id("findFlights");

    public FlightDetailsPage(DriverManager driverManager) {
        super(driverManager);
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
