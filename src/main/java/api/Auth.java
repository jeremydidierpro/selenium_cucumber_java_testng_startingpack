package api;

import functions.excel.ExcelManager;

import utils.Config;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Auth{

    private Auth(){
    }

    /**
     * Thanks to this method we get the access token from auth and then put it into our loginFiles
     * @param loginsFiles is the excel file where to put the login details
     */
    public static void setToken(ExcelManager loginsFiles){
        // Taking our AD logins from loginsFiles
        String login = loginsFiles.read("login");
        String password = loginsFiles.read("password");
        // Creating the request body
        Map<String,String> body = new HashMap<>();
        body.put("login", login);
        body.put("password", password);
        // Calling request to extracting access token
        String accessToken = given()
                .baseUri("")
                .pathParam("environment", Config.environment())
                .contentType("application/json")
                .body(body.toString()).
        when()
                .post("environments/{environment}/account_tokens/").
        then()
                .log().ifError()
                .statusCode(200)
                .extract().path("accessToken");
        // Writing access token in the loginsFile
        loginsFiles.write("accessToken", "bearer "+accessToken);
    }


}
