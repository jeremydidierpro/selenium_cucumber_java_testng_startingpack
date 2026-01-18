Feature: Register to the flight booking application


  @SMOKE_TEST
  Scenario: Registration with default user
    Given I am on the registration page
    When I enter my default user's details
    And I submit the registration
    Then I am redirected registration Confirmation


