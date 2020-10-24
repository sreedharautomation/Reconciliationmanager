package com.reconciliationmanager.cucumber.steps_Reconciliationmanager;

import TestData.ReconciliationManager.LoadTestData_ReconciliationManager;
import commonObjects.CommonMethods;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReconciliationmanagerRequestWhenFromDateIsGreaterThanToDate extends CommonMethods {

    CommonMethods commonMethods;

    //This method will run after the class execution to reset the RestAssured
    @After
    public void teardown() {
        RestAssured.reset();
    }

    //Send user login request
    @Test
    @Order(1)
    @Given("^Login request successful with the credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public boolean login_request_successful_with_the_credentials_and(String username, String password) {
        try {
            //Request body
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            //Sending request and validates the response code
            response_login = RestAssured.given().log().all().header("Constent-Type", "application/json")
                    .when().body(json.toString()).post(LoadTestData_ReconciliationManager.testData_ReconciliationManager.BASE_URI)
                    .then().assertThat().log().all().statusCode(responseCode_OK).extract().response().asString();
            System.out.println("User Login request successful and response code received: " + responseCode_OK);
            return true;
        } catch (Exception e) {
            System.out.println("User Login request fails");
        }
        return false;
    }

    //Send request to read the JWT token from the login response
    @Test
    @Order(2)
    @Given("^User reads JWT token from the successful Login$")
    public void user_reads_JWT_token_from_the_successful_Login() throws Exception {
        //Calling this method from CommonMethods class
        commonMethods.readJWT();
    }

    // Send the ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime'
    @Test
    @Order(3)
    @When("^User sends the ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime', then the response should return an (\\d+) error code$")
    public boolean user_sends_the_ReconciliationManager_request_with_the_intervalFromDatetime_value_is_greater_than_intervalToDatetime_then_the_response_should_return_an_error_code(int errorStatusCode) {
        try {
            //Request body
            JSONObject json = new JSONObject();
            json.put("topicName", LoadTestData_ReconciliationManager.testData_ReconciliationManager.Topic_name);
            json.put("intervalFromDatetime", LocalDate.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            json.put("intervalToDatetime", LocalDate.now().minusMonths(1).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            //Sending POST request for reconciliationmanager end point with the 'intervalFromDatetime' value is greater than 'intervalToDatetime'
            LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response = RestAssured.given().log().all().header("Authorization", "Bearer " + JWT_accessToken)
                    .when().body(json.toString()).post(LoadTestData_ReconciliationManager.testData_ReconciliationManager.BASE_PATH + LoadTestData_ReconciliationManager.testData_ReconciliationManager.client_uuid)
                    .then().assertThat().log().all().statusCode(400).extract().response().asString();
            System.out.println("User sends ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime', then the expected response code received: " + errorStatusCode);
            return true;
        } catch (Exception e) {
            System.out.println("User sends ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime', then the expected response code received is not:" + errorStatusCode);
        }
        return false;
    }

    //Validating the ReconciliationManager response body
    @Test
    @Order(4)
    @Then("^The response body should return with error message as FROM value is greater than the TO value$")
    public boolean the_response_body_should_return_with_error_message_as_FROM_value_is_greater_than_the_TO_value() {
        try {
            SoftAssertions soft = new SoftAssertions();
            JsonPath js = new JsonPath(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response); //for parsing Json

            //Validating the Response body objects
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("errors"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("message"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("code"));

            //Fetching the Response body
            String errorMessage = js.getString("errors.message");
            String errorCode = js.getString("errors.code");

            //Validating the error message from the response
            System.out.println("errorMessage_IfFromDateGreaterThanToDate: " + errorMessage);
            soft.assertThat(errorMessage).isEqualTo(LoadTestData_ReconciliationManager.testData_ReconciliationManager.errorMessage_IfFromDateGreaterThanToDate);

            //Validating the error code from the response
            System.out.println("errorCode_IfFromDateGreaterThanToDate : " + errorCode);
            soft.assertThat(errorCode).isEqualTo(LoadTestData_ReconciliationManager.testData_ReconciliationManager.errorCode_IfFromDateGreaterThanToDate);

            System.out.println("Test Passed - User ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime', then response body received with expected details: " + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
            return true;
        } catch (Exception e) {
            System.out.println("Test Failed - User ReconciliationManager request with the 'intervalFromDatetime' value is greater than 'intervalToDatetime', then response body received is not expected:" + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
        }
        return false;
    }
}

