package api;

import functions.excel.ExcelManager;

import utils.Config;
import utils.Secrets;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Auth{

    private Auth(){
    }

    /**
     * Thanks to this method we get the access token from auth and then put it into our loginFiles
     */
    public static void setToken(){
        // Taking our AD logins from loginsFiles
        String login = Secrets.apiUser();
        String password = Secrets.apiPassword();
        // Creating the request body
        Map<String,String> body = new HashMap<>();
        body.put("login", login);
        body.put("password", password);
        // Calling request to extract access token
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
        // Writing access token in any file that fit your specific project
        //loginsFiles.write("accessToken", "bearer "+accessToken);
    }


}
