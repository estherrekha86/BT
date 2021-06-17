Feature: Login and Forgot Credentials Journeys

Background:
When user launches the browser
When user enter the BT My Account URL

  @tcCDEOnline_Login_Normal_CDEProfileLogin
  Scenario: User wants to login with Normal profile and check all the validation
    Given User navigates to Hub page
    When User enters username
    When User enters password
    And User clicks on login button
    Then verify dashboard is displayed