package api;

import exceptions.InvalidApiRequestException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import static io.restassured.RestAssured.given;


public class RequestsFactory {
    private static final String AUTHORIZATION = "Authorization";
    private static final Logger logger = LogManager.getLogger(RequestsFactory.class);

    private RequestsFactory(){
    }


    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a GET call, verifies the token and extracts the response
     */
    public static Response get(RequestSpecification reqSpec, String endPoint, int expectedStatusCode){
        logger.info(() -> "****** Trying to GET resources with an API REQUEST.");
        // Building the request object
        Response response = given(reqSpec)
                .header(AUTHORIZATION,TokenContext.getAccessToken())
                .when()
                .get(endPoint)
                .then()
                .extract().response();

        // Verifying the response status code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401 || response.getStatusCode()==403 ){
                refreshToken();
                response = given(reqSpec)
                        .header(AUTHORIZATION, TokenContext.getAccessToken())
                        .when()
                        .get(endPoint)
                        .then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with the GET request : "+ getErrorMessage(expectedStatusCode, finalResponse));
                throw new InvalidApiRequestException("GET " + getErrorMessage(expectedStatusCode, response));
            }
        }
        return response;
    }


    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a POST call, verifies the token and extracts the response
     */
    public static Response post(RequestSpecification reqSpec, String endPoint, int expectedStatusCode){
        logger.info(() -> "****** Trying to POST resources with an API REQUEST.");
        // Collecting the request object
        Response response = given(reqSpec)
                .header(AUTHORIZATION,TokenContext.getAccessToken())
                .when()
                .post(endPoint)
                .then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401 || response.getStatusCode()==403){
                refreshToken();
                response = given(reqSpec)
                        .header(AUTHORIZATION, TokenContext.getAccessToken())
                        .when()
                        .post(endPoint)
                        .then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with the POST request : "+ getErrorMessage(expectedStatusCode, finalResponse));
                throw new InvalidApiRequestException("POST " + getErrorMessage(expectedStatusCode, response));
            }
        }
        return response;
    }

    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a POST call with a file in the body, verifies the token and extracts the response
     */
    public static Response postFile(RequestSpecification reqSpec, String endPoint, int expectedStatusCode, String pathToFile){
        logger.info(() -> "****** Trying to POST resources with an API REQUEST.");
        // Collecting the request object
        Response response = given(reqSpec)
                .header(AUTHORIZATION,TokenContext.getAccessToken())
                .header("Content-Type", "multipart/form-data")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .multiPart("attachment", new File(pathToFile))
                .when()
                .post(endPoint)
                .then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401 || response.getStatusCode()==403){
                refreshToken();
                response = given(reqSpec)
                        .header(AUTHORIZATION, TokenContext.getAccessToken())
                        .header("Content-Type", "multipart/form-data")
                        .header("Connection", "keep-alive")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .multiPart("attachment", new File(pathToFile))
                        .when()
                        .post(endPoint)
                        .then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with the POST request : "+ getErrorMessage(expectedStatusCode,finalResponse));
                throw new InvalidApiRequestException("POST " + getErrorMessage(expectedStatusCode, response));
            }
        }
        return response;
    }

    /**
     * This method, by providing the request, the endPoint and the expected status code, makes a DELETE call, verifies the token and extracts the response
     */
    public static Response delete(RequestSpecification reqSpec, String endPoint,  int expectedStatusCode){
        logger.info(() -> "****** Trying to DELETE resources with an API REQUEST.");
        // Collecting the request object
        Response response = given(reqSpec)
                .header(AUTHORIZATION,TokenContext.getAccessToken())
                .when()
                .delete(endPoint)
                .then()
                .extract().response();

        // Verifying the response code and renewing the access token if needed
        if(response.getStatusCode() != expectedStatusCode ){
            if(response.getStatusCode()==401 || response.getStatusCode()==403){
                refreshToken();
                response = given(reqSpec)
                        .header(AUTHORIZATION, TokenContext.getAccessToken())
                        .when()
                        .delete(endPoint)
                        .then()
                        .log().ifError()
                        .statusCode(expectedStatusCode)
                        .extract().response();
            }else{
                Response finalResponse = response;
                logger.error(()-> "****** Something went wrong with the DELETE request : "+ getErrorMessage(expectedStatusCode, finalResponse));
                throw new InvalidApiRequestException("DELETE " + getErrorMessage(expectedStatusCode, response));
            }
        }
        return response;
    }

    private static void refreshToken(){
        String tokenBefore = TokenContext.getAccessToken();
        TokenContext.refreshLock().lock();
        try {
            String tokenNow = TokenContext.getAccessToken();
            boolean alreadyRefreshed = tokenNow != null && !tokenNow.equals(tokenBefore);
            if (!alreadyRefreshed) {
                TokenContext.setAccessToken(Auth.setToken()); // updates TokenContext internally
            }
        } finally {
            TokenContext.refreshLock().unlock();
        }
    }

    private static String getErrorMessage(int expectedStatusCode, Response response) {
        return "Expected status code was " + expectedStatusCode + " but the actual status is " + response.getStatusCode();
    }

}
