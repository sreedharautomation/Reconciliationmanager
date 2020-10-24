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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class ReconciliationmanagerRequestWithCorrectDetails extends CommonMethods {

    CommonMethods commonMethods;

    //This method will run after the class execution to reset the RestAssured
    @After
    public void teardown() {
        RestAssured.reset();
    }

    //Send user login request
    @Test
    @Order(1)
    @Given("^User login successful upon with the credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public boolean user_login_successful_upon_with_the_credentials_and(String username, String password) {
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
    @Given("^User reads the JWT access token from the successful Login response$")
    public void user_reads_the_JWT_access_token_from_the_successful_Login_response() throws Exception {
        //Calling this method from CommonMethods class
        commonMethods.readJWT();
    }

    //Send the ReconciliationManager request with the correct details
    @Test
    @Order(3)
    @When("^User sends the ReconciliationManager request with the correct details of - TopicName,IntervalFromDatetime&IntervalToDatetime$")
    public boolean user_sends_the_ReconciliationManager_request_with_the_correct_details_of_TopicName_IntervalFromDatetime_IntervalToDatetime() throws Exception {
        try {
            //Request body
            JSONObject json = new JSONObject();
            json.put("topicName", LoadTestData_ReconciliationManager.testData_ReconciliationManager.Topic_name);
            json.put("intervalFromDatetime", LocalDate.now().minusMonths(1).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            json.put("intervalToDatetime", LocalDate.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            //Sending POST request for reconciliationmanager end point
            LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response = RestAssured.given().log().all().header("Authorization", "Bearer " + JWT_accessToken)
                    .when().body(json.toString()).post(LoadTestData_ReconciliationManager.testData_ReconciliationManager.BASE_PATH + LoadTestData_ReconciliationManager.testData_ReconciliationManager.client_uuid)
                    .then().assertThat().log().all().statusCode(200).extract().response().asString();
            System.out.println("User ReconciliationManager request with the correct details successful and response code received: " + responseCode_OK);
            return true;
        } catch (Exception e) {
            System.out.println("User ReconciliationManager request with the correct details fails and response code received is not:" + responseCode_OK);
        }
        return false;
    }

    //Validating the ReconciliationManager response body
    @Test
    @Order(4)
    @Then("^The response should return an identifier for the request reconciliationDetailRequestId, and total number of messages sent in the given time unit$")
    public boolean the_response_should_return_an_identifier_for_the_request_reconciliationDetailRequestId_and_total_number_of_messages_sent_in_the_given_time_unit() {
        try {
            SoftAssertions soft = new SoftAssertions();
            JsonPath js = new JsonPath(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response); //for parsing Json

            //Validating the Response body objects
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("reconciliationDetailRequestId"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("messageCount"));

            //Fetching the Response body
            String reconciliationDetailRequestId = js.getString("reconciliationDetailRequestId");
            String messageCount = js.getString("messageCount");

            //Validating the generated UUID from the response
            System.out.println("reconciliationDetailRequestId received from Response " + reconciliationDetailRequestId);
            soft.assertThat(reconciliationDetailRequestId).isEqualTo("[UUID]");

            //Validating the 'total number of records in the interval' from the response
            System.out.println("messageCount received from Response " + messageCount);
            soft.assertThat(messageCount).isEqualTo("[total number of records in the interval]");
            System.out.println("User ReconciliationManager request with the correct details successful and response body received with expected details: " + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
            return true;
        } catch (Exception e) {
            System.out.println("User ReconciliationManager request with the correct details fails and response body received is:" + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
        }
        return false;
    }
}
