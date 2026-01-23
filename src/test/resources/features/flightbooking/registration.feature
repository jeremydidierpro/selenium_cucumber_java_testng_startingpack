Feature: Register in the flight booking application


  @SMOKE_TEST
  Scenario: Register with a default user
    Given I am on the registration page
    When I enter the default user's details
    And I submit the registration
    Then I should see the registration confirmation page


