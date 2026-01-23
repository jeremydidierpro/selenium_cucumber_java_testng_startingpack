package api;

import exceptions.InvalidApiRequestException;
import functions.excel.ExcelManager;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Secrets;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;


public class RequestsFactory {
    protected static ExcelManager loginsFiles;
    static {
        loginsFiles = new ExcelManager("logins.xlsx","logins",1);
        String authLogin = Secrets.apiUser();
        String authPasswd = Secrets.apiPassword();
        if (authLogin != null && !authLogin.trim().isEmpty()) {
            loginsFiles.write("login", authLogin);
            loginsFiles.write("password", authPasswd);
        }
    }
    private static final String HEADER = "headers";
    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final Logger logger = LogManager.getLogger(RequestsFactory.class);

    private RequestsFactory(){
    }


    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a GET call, verifies the token and extracts the response
     */
    public static Response get(Map<String, Object> request, int expectedStatusCode){
        defaultParser = Parser.JSON;
        String url = request.get("url").toString();
        Map<String, Object> headers = (HashMap<String, Object>) request.get(HEADER);
        headers.replace(AUTHORIZATION,loginsFiles.read(ACCESS_TOKEN));
        logger.info(() -> "****** Trying to GET resources with API REQUEST : "+ url);
        // Collecting the request object
        Response response = given()
                                .headers(headers).
                            when()
                                .get(url).
                            then()
                                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401 || response.getStatusCode()==403 ){
                Auth.setToken();
                headers.replace(AUTHORIZATION, loginsFiles.read(ACCESS_TOKEN));
                response = given()
                                .headers(headers).
                            when()
                                .get(url).
                        then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with GET request : "+ getErrorMessage(expectedStatusCode, url, finalResponse));
                throw new InvalidApiRequestException("GET " + getErrorMessage(expectedStatusCode, url, response));
            }
        }
        return response;
    }


    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a POST call, verifies the token and extracts the response
     */
    public static Response post(Map<String, Object> request, int expectedStatusCode){
        defaultParser = Parser.JSON;
        String url = request.get("url").toString();
        HashMap<String, Object> headers = (HashMap<String, Object>) request.get(HEADER);
        headers.replace(AUTHORIZATION,loginsFiles.read(ACCESS_TOKEN));
        String body = request.get("body").toString();
        logger.info(() -> "****** Trying to POST resources with API REQUEST : "+ url);
        // Collecting the request object
        Response response = given()
                .headers(headers)
                .body(body).
                        when()
                .post(url).
                        then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401){
                Auth.setToken();
                headers.replace(AUTHORIZATION, loginsFiles.read(ACCESS_TOKEN));
                response = given()
                        .headers(headers)
                        .body(body).
                                when()
                        .post(url).
                                then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with POST request : "+ getErrorMessage(expectedStatusCode, url, finalResponse));
                logger.info(() -> "****** The body of the failed request was : "+ body);
                throw new InvalidApiRequestException("POST " + getErrorMessage(expectedStatusCode, url, response));
            }
        }
        return response;
    }

    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a with a file to a body call, verifies the token and extracts the response
     */
    public static Response postFile(Map<String, Object> request, int expectedStatusCode, String pathToFile){
        defaultParser = Parser.JSON;
        String url = request.get("url").toString();
        HashMap<String, Object> headers = (HashMap<String, Object>) request.get(HEADER);
        headers.replace(AUTHORIZATION,loginsFiles.read(ACCESS_TOKEN));
        headers.put("Content-Type", "multipart/form-data");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        logger.info(() -> "****** Trying to POST resources with API REQUEST : "+ url);
        // Collecting the request object
        Response response = given()
                .headers(headers)
                .multiPart("attachment", new File(pathToFile)).
                        when()
                .post(url).
                        then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401){
                Auth.setToken();
                headers.replace(AUTHORIZATION, loginsFiles.read(ACCESS_TOKEN));
                response = given()
                        .headers(headers)
                        .multiPart("attachment", new File(pathToFile)).
                                when()
                        .post(url).
                                then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with POST request : "+ getErrorMessage(expectedStatusCode, url, finalResponse));
                throw new InvalidApiRequestException("POST " + getErrorMessage(expectedStatusCode, url, response));
            }
        }
        return response;
    }

    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a DELETE call, verifies the token and extracts the response
     */
    public static Response delete(Map<String, Object> request, int expectedStatusCode){
        defaultParser = Parser.JSON;
        String url = request.get("url").toString();
        HashMap<String, Object> headers = (HashMap<String, Object>) request.get(HEADER);
        headers.replace(AUTHORIZATION,loginsFiles.read(ACCESS_TOKEN));
        logger.info(() -> "****** Trying to DELETE resources with API REQUEST : "+ url);
        // Collecting the request object
        Response response = given()
                .headers(headers).
                        when()
                .delete(url).
                        then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401){
                Auth.setToken();
                headers.replace(AUTHORIZATION, loginsFiles.read(ACCESS_TOKEN));
                response = given()
                        .headers(headers).
                                when()
                        .delete(url).
                                then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with DELETE request : "+ getErrorMessage(expectedStatusCode, url, finalResponse));
                throw new InvalidApiRequestException("DELETE " + getErrorMessage(expectedStatusCode, url, response));
            }
        }
        return response;
    }

    private static String getErrorMessage(int expectedStatusCode, String url, Response response) {
        return url + " Expected status was " + expectedStatusCode + " but actual status is " + response.getStatusCode();
    }

}
