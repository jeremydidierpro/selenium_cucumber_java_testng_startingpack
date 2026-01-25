package api.object_repository;

import api.RequestsFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class Accounts {

    private Accounts(){
    }


    public static RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
            .setBaseUri("https://my_base_uri")
                .setContentType("application/json")
                .build();
    }

    public static Response getAccountDetails(String accountEmail){
        String endPoint = "/users/"+accountEmail;
        return RequestsFactory.get(requestSpecification(),endPoint,200);
    }
}


