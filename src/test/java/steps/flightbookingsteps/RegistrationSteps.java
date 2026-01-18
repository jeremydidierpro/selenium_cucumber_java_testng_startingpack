package steps.flightbookingsteps;

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

    @When("I enter my default user's details")
    public void i_enter_my_default_user_s_details() {
        registrationPage.enterUserDetails("jack", "Peterson");
        registrationPage.enterUserCredentials("email@email.com", "Password123");
    }

    @When("I submit the registration")
    public void i_submit_the_registration() {
        registrationPage.submitRegistration();
    }


    @Then("I am redirected registration Confirmation")
    public void i_am_redirected_registration_confirmation() {
        registrationConfirmationPage.waitPage();
        commonInstance.softly().assertThat(registrationConfirmationPage.getConfirmationMsg()).hasTextEqualTo("Registration Confirmation Page");

    }


}
