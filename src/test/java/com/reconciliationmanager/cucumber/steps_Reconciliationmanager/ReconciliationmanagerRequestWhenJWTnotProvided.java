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

public class ReconciliationmanagerRequestWhenJWTnotProvided extends CommonMethods {

    CommonMethods commonMethods;
    //This method will run after the class execution to reset the RestAssured
    @After
    public void teardown() {
        RestAssured.reset();
    }

    //Send user login request
    @Test
    @Order(1)
    @Given("^Login successful with the credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public boolean login_successful_with_the_credentials_and(String username, String password) {
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
    @Given("^User fetch the JWT access token from the successful Login response$")
    public void user_fetch_the_JWT_access_token_from_the_successful_Login_response() throws Exception {
        //Calling this method from CommonMethods class
        commonMethods.readJWT();

    }

    //Send the ReconciliationManager request with out JWT token
    @Test
    @Order(3)
    @When("^User sends the ReconciliationManager request with out JWT token, then the response should return unauthorised error (\\d+)$")
    public boolean user_sends_the_ReconciliationManager_request_with_out_JWT_token_then_the_response_should_return_unauthorised_error(int statusCode) {
        try {
            //Request body
            JSONObject json = new JSONObject();
            json.put("topicName", LoadTestData_ReconciliationManager.testData_ReconciliationManager.Topic_name);
            json.put("intervalFromDatetime", LocalDate.now().minusMonths(1).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            json.put("intervalToDatetime", LocalDate.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            //Sending POST request for reconciliationmanager end point with out JWT
            LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response = RestAssured.given().log().all().header("Authorization", "Bearer ")
                    .when().body(json.toString()).post(LoadTestData_ReconciliationManager.testData_ReconciliationManager.BASE_PATH + LoadTestData_ReconciliationManager.testData_ReconciliationManager.client_uuid)
                    .then().assertThat().log().all().statusCode(401).extract().response().asString();
            System.out.println("User ReconciliationManager request with out JWT token, then the response code received: " + statusCode);
            return true;
        } catch (Exception e) {
            System.out.println("User ReconciliationManager request with out JWT token, then the response code received is not expected one:" + statusCode);
        }
        return false;
    }

    //Validating the ReconciliationManager response body
    @Test
    @Order(4)
    @Then("^The response body should return an says Unauthorized$")
    public boolean the_response_body_should_return_an_says_Unauthorized() {
        try {
            SoftAssertions soft = new SoftAssertions();
            JsonPath js = new JsonPath(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response); //for parsing Json

            //Validating the Response body objects
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("errors"));
            soft.assertThat(LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response.contains("message"));

            //Fetching the Response body
            String errorMessage = js.getString("errors.message");

            //Validating the error message from the response
            System.out.println("error_Unauthorized message is : " + errorMessage);
            soft.assertThat(errorMessage).isEqualTo(LoadTestData_ReconciliationManager.testData_ReconciliationManager.error_Unauthorized);

            System.out.println("Test Passed - User ReconciliationManager request with out JWT token, then response body received with expected details: " + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
            return true;
        } catch (Exception e) {
            System.out.println("Test Failed - User ReconciliationManager request with out JWT token, then response body received is not expected:" + LoadTestData_ReconciliationManager.testData_ReconciliationManager.reconciliationManager_response);
        }
        return false;
    }
}