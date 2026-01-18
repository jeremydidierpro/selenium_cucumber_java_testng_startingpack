package api.function_repository;

import api.object_repository.Accounts;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class AccountDetails {
    protected final Response details;

    public AccountDetails(String accountEmail){
        this.details = Accounts.getAccountDetails(accountEmail);
    }


    /**
     * Get all the account details
     * @return account details as a Map
     */
    public Map<String, Object> getDetails(){
        return this.details.jsonPath().getMap("");
    }

    /**
     * Return account ID as a String.
     */
    public String getAccountId(){
        return this.details.jsonPath().getString("id");
    }



}
