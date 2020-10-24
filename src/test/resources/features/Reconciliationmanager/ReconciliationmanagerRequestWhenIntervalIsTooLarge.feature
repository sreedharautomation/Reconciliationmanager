Feature: ReconciliationManager Request send, where the 'intervalFromDatetime' value is older than 6 months

  Response should return with 400 for the request, where the 'intervalFromDatetime' value is older than 6 months.

  Scenario Outline: Successful Login
    Given Login request successful upon with the credentials "<username>" and "<password>"
    Examples: Login
      |username|password|
      |user-sree|12345|

  Scenario: Check that - when user sends the ReconciliationManager Request with the the 'intervalFromDatetime' value is older than 6 months, then the response should return an 400 error code

    Given User reads JWT token from the successful Login response
    When User sends the ReconciliationManager request with the the 'intervalFromDatetime' value is older than six months, then the response should return an 400 error code
    Then The response body should return with error message as Interval too large