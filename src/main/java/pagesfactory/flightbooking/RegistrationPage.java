package pagesfactory.flightbooking;

import org.openqa.selenium.By;
import pagesfactory.CommonPage;
import utils.DriverFactory;
import utils.Environment;

public class RegistrationPage extends CommonPage {

    private final By firstNameTxt = By.name("firstName");
    private final By lastNameTxt = By.name("lastName");
    private final By userNameField = By.id("email");
    private final By passwordField = By.name("password");
    private final By confirmPasswordField = By.name("confirmPassword");
    private final By submitBtn = By.id("register-btn");



    public RegistrationPage(DriverFactory driverFactory) {
        super(driverFactory);
    }

    public void load(){
        mySelenium.goTo(Environment.baseUrl());
        wait.elementToBePresent(firstNameTxt);
    }

    public void enterUserDetails(String firstname, String lastname){
        enterFirstName(firstname);
        enterLastName(lastname);
    }

    public void enterUserCredentials(String email, String password){
        enterUserName(email);
        enterPassword(password);
        confirmPassword(password);
    }

    public void enterFirstName(String firstName){
        mySelenium.type(firstNameTxt,firstName);
    }

    public void enterLastName(String lastName){
        mySelenium.type(lastNameTxt,lastName);
    }

    public void enterUserName(String email){
        mySelenium.type(userNameField,email);
    }

    public void enterPassword(String password){
        mySelenium.type(passwordField,password);
    }

    public void confirmPassword(String password){
        mySelenium.type(confirmPasswordField,password);
    }

    public void submitRegistration() {
        mySelenium.click(submitBtn);
    }

}
