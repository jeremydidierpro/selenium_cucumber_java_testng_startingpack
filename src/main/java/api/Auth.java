package api;


import utils.Config;
import utils.Secrets;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Auth{

    private Auth(){
    }

    /**
     * Thanks to this method, we get the access token from auth and then store it in a string
     */
    public static String setToken(){
        // Retrieving our AD credentials
        String login = Secrets.apiUser();
        String password = Secrets.apiPassword();
        // Creating the request body
        Map<String,String> body = new HashMap<>();
        body.put("login", login);
        body.put("password", password);
        // Calling the request to extract the access token
        String accessToken =  given()
                .baseUri("https://your_base_uri")
                .pathParam("environment", Config.environment())
                .contentType("application/json")
                .body(body).
        when()
                .post("/environments/{environment}/account_tokens/").
        then()
                .log().ifError()
                .statusCode(200)
                .extract().path("accessToken");
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("Auth succeeded but accessToken is empty");
        }
        return "Bearer " + accessToken;
    }

}

