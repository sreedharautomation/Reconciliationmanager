Feature: ReconciliationManager Request With the Correct details of - TopicName,IntervalFromDatetime&IntervalToDatetime

  User should return an identifier for the request reconciliationDetailRequestId, and total number of messages sent in the given time unit.

  Scenario Outline: Successful Login
    Given User login successful upon with the credentials "<username>" and "<password>"
    Examples: Login
      |username|password|
      |user-sree|12345|

  Scenario: Check that - when user sends the ReconciliationManager Request With the Correct details of - TopicName,IntervalFromDatetime&IntervalToDatetime, then the response should return an identifier for the request reconciliationDetailRequestId, and total number of messages sent in the given time unit

    Given User reads the JWT access token from the successful Login response
    When User sends the ReconciliationManager request with the correct details of - TopicName,IntervalFromDatetime&IntervalToDatetime
    Then The response should return an identifier for the request reconciliationDetailRequestId, and total number of messages sent in the given time unit