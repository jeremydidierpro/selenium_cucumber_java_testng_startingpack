package steps.flightbookingsteps;

import functions.miscellaneous.RandomMethods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pagesfactory.flightbooking.RegistrationConfirmationPage;
import pagesfactory.flightbooking.RegistrationPage;
import testcontext.CommonInstance;

public class RegistrationSteps {
    private final Logger logger = LogManager.getLogger(RegistrationSteps.class);
    private final RegistrationPage registrationPage;
    private final RegistrationConfirmationPage registrationConfirmationPage;
    private final CommonInstance commonInstance;

    public RegistrationSteps(RegistrationPage registrationPage, RegistrationConfirmationPage registrationConfirmationPage, CommonInstance commonInstance) {
        this.registrationPage                   = registrationPage;
        this.registrationConfirmationPage       = registrationConfirmationPage;
        this.commonInstance                     = commonInstance;

    }


    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        registrationPage.load();
    }

    @When("I enter the default user's details")
    public void i_enter_the_default_user_s_details() {
        String firstName = RandomMethods.firstName();
        String lastName = RandomMethods.lastName();
        String email = RandomMethods.email();
        String password = RandomMethods.string(10);
        registrationPage.enterUserDetails(firstName, lastName);
        registrationPage.enterUserCredentials(email, password);
    }

    @When("I submit the registration")
    public void i_submit_the_registration() {
        registrationPage.submitRegistration();
    }


    @Then("I should see the registration confirmation page")
    public void i_should_see_the_registration_confirmation_page() {
        registrationConfirmationPage.waitPage();
        commonInstance.softly().assertThat(registrationConfirmationPage.getConfirmationMsg()).hasTextEqualTo("Registration Confirmation Page");

    }
}
