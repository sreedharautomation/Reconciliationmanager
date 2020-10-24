Feature: ReconciliationManager Request With the Correct details, but JWT token not provided

  User should return an unauthorised error, when ReconciliationManager Request with out JWT token.

  Scenario Outline: Successful Login
    Given Login successful with the credentials "<username>" and "<password>"
    Examples: Login
      |username|password|
      |user-sree|12345|

  Scenario: Check that - when user sends the ReconciliationManager Request with out JWT token, then the response should return unauthorised error 401

    Given User fetch the JWT access token from the successful Login response
    When User sends the ReconciliationManager request with out JWT token, then the response should return unauthorised error 401
    Then The response body should return an says Unauthorized