Feature: ReconciliationManager Request with the wrong details provided for - TopicName

  Response should return with 404 for the request, ReconciliationManager Request with the wrong details provided for - TopicName.

  Scenario Outline: Successful Login
    Given Login successful upon with the credentials "<username>" and "<password>"
    Examples: Login
      |username|password|
      |user-sree|12345|

  Scenario: Check that - when user sends the ReconciliationManager Request with the wrong TopicName, then the response should return an 404 error code

    Given User reads JWT access token from the successful Login response
    When User sends the ReconciliationManager request with the wrong TopicName, then the response code should return 404
    Then The response body should return with error message as Topic not supported