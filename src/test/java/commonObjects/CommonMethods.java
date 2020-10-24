package commonObjects;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.junit.Assert;

public class CommonMethods {

    public String response_login;
    public String JWT_accessToken;
    final String BASE_URI = "https://www.10xbanking.com";
    public int responseCode_OK = 200;

    public boolean userLogin(String username, String password) throws Exception {
        try {
            //Request body
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            //Sending request and validates the response code
            response_login = RestAssured.given().log().all().header("Constent-Type", "application/json")
                    .when().body(json.toString()).post(BASE_URI)
                    .then().assertThat().log().all().statusCode(responseCode_OK).extract().response().asString();
            System.out.println("User Login request successful and response code received: " + responseCode_OK);
            return true;
        } catch (Exception e) {
            System.out.println("User Login request fails");
        }
        return false;
    }

    public boolean readJWT() throws Exception {
        try {
            //Validating whether response from the Login request does contains the JWT token
            Assert.assertTrue(response_login.contains("access_token"));
            JsonPath js = new JsonPath(response_login); //for parsing Json
            //Fetching the JWT_accessToken from the Login response
            JWT_accessToken = js.getString("access_token");
            System.out.println("JWT access token from the response is: " + JWT_accessToken);
        } catch (Exception e) {
            System.out.println("User Login response does not contains the JWT access token");
        }
        return false;
    }
}

