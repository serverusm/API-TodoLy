package com.trello.api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, String> context = new HashMap<>();
    private Response response;
    public void setProperty(String key, String value) {
        context.put(key, value);
    }

    public String getProperty(String key) {
        return context.get(key);
    }

    public void setResponse(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }
}
