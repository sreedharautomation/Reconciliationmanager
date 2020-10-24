Feature: ReconciliationManager Request send, where the 'intervalFromDatetime' value is equals to 'intervalToDatetime'

  Response should return with 400 for the request, where the 'intervalFromDatetime' value is equals 'intervalToDatetime'

  Scenario Outline: Successful Login
    Given Login request with the credentials "<username>" and "<password>"
    Examples: Login
      |username|password|
      |user-sree|12345|

  Scenario: Check that - when user sends the ReconciliationManager Request with the 'intervalFromDatetime' value is equals to 'intervalToDatetime', then the response should return an 400 error code

    Given User fetches JWT token from the successful Login
    When User sends the ReconciliationManager request with the 'intervalFromDatetime' value is equals to 'intervalToDatetime', then the response should return an 400 error code
    Then The response body should return with error message as TO is equal to the FROM