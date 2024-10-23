package com.todoLy.endpoints;

import com.todoLy.ApiRequestHandler;
import com.todoLy.client.RequestManager;
import com.todoLy.utils.PropertiesInfo;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Boards {
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private ApiRequestHandler request;
    public Boards(){
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/son");

        queryParams = new HashMap<String, String>();
        queryParams.put("key", PropertiesInfo.getInstance().getApiKey());
        queryParams.put("token", PropertiesInfo.getInstance().getApiToken());
        request = new ApiRequestHandler();
        request.setHeaders(headers);
        request.setQueryParam(queryParams);
    }
    public Response createBoard(String boardName) {
        request.setQueryParam("name", boardName);
        request.setEndpoint("/boards/");
        return RequestManager.post(request);
    }
}
