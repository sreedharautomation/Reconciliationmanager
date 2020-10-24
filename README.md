# 10x - ReconciliationManager


## Setup

* Programming Language: JAVA
* IDE: IntelliJ
* Create a Maven project to build the program;
* Add all dependencies in pom.xml regarding some frameworks such as Serenity Cucumber to manage BDD, Rest-assured to call the web-service REST and added a json-schema-validator to validate JSON response format and Reporting;
* BDD requires a feature file to invoke the step definitions:
* Create the scenarios in feature file as per the requirements, so each step in feature file has to match a step definition in class file;
* Following the BDD practices for coding;

## Validation example (source-code)
* Passing a base path by parameter ( from in the tesdt data file - ReconciliationManager) and calling POST request
when().get(LoadTestData_ReconciliationManager.testData_ReconciliationManager.BASE_PATH + LoadTestData_ReconciliationManager.testData_ReconciliationManager.client_uuid) ( i.e - "reconciliationmanager/api/v1/interval-detail-request/{client-uuid}")

* Print in console the response
then().log().all();

* Check the status code, so the expected status is passed by parameter in feature file
then().statusCode(404));

* Check body response - by parsing the JSON
            //Validating the Response body objects
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("errors"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("message"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("code"));

            //Fetching the Response body
            String errorMessage = js.getString("errors.message");
            String errorCode = js.getString("errors.code");

            //Validating the error message from the response
            System.out.println("errorMessage_ifTopicNameIsIncorrect: " + errorMessage);
            soft.assertThat(errorMessage).isEqualTo(LoadTestData_ReconciliationManager.testData_ReconciliationManager.errorMessage_ifTopicNameIsIncorrect);

            //Validating the error code from the response
            System.out.println("errorCode_ifTopicNameIsIncorrect: " + errorCode);
            soft.assertThat(errorCode).isEqualTo(LoadTestData_ReconciliationManager.testData_ReconciliationManager.errorCode_ifTopicNameIsIncorrect);

## Need to add the test data into respective filed of below shown file for test data. 
* testdata/ReconciliationManager/ReconciliationManager.yaml

## To run the Individual tests
* Go to Terminal and run below commands individually for respective test runs: 

- `mvn verify -P ReconciliationManagerRequestWithCorrectTopicNameIntervalFromDatetimeAndIntervalToDatetime_TestRunner`
- `mvn verify -P ReconciliationmanagerRequestWithWrongTopicName_TestRunner`
- `mvn verify -P ReconciliationmanagerRequestWhenIntervalIsTooLarge_TestRunner`
- `mvn verify -P ReconciliationmanagerRequestWhenFromDateIsGreaterThanToDate_TestRunner`
- `mvn verify -P ReconciliationmanagerRequestWhenFromDateIsEqualsToDate_TestRunner`
- `mvn verify -P ReconciliationmanagerRequestWhenJWTnotProvided_TestRunner`

## To run the all End to End tests in by one command
- run the End to End Automation Regression Suite, run `mvn verify -P ReconciliationmanagerRequestTestSuite_TestRunner`

## Test results are getting generated in html using Serenity as shown below path.

* target/site/serenity
