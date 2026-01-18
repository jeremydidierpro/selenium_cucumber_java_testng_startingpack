package api.object_repository;

import api.RequestsFactory;
import io.restassured.response.Response;
import java.util.HashMap;

public class Accounts {
    protected static String baseUri;
    protected static HashMap<String, Object> headers;
    private static final String HEADER = "headers";

    private Accounts(){
    }


    public static void prepareRequest(){
        headers = new HashMap<>();
        baseUri = "";
        headers.put("Content-Type","application/json");
        headers.put("Authorization", "Authorisation not yet implemented");
    }


    public static Response getAccountDetails(String accountEmail){
        prepareRequest();
        String endPoint = baseUri+accountEmail;
        HashMap<String, Object> request = new HashMap<>();
        request.put("url", endPoint);
        request.put(HEADER,headers);
        return RequestsFactory.get(request,200);
    }
}


