package com.trello.api.hooks;

import com.trello.ApiRequestHandler;
import com.trello.api.Context;
import com.trello.client.RequestManager;
import com.trello.utils.PropertiesInfo;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

public class ApiHooks {
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private ApiRequestHandler request;
    private ResponseSpecification responseSpec;
    private Context context;

    public ApiHooks(Context context) {
        this.context = context;
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/son");

        queryParams = new HashMap<String, String>();
        queryParams.put("key", PropertiesInfo.getInstance().getApiKey());
        queryParams.put("token", PropertiesInfo.getInstance().getApiToken());
        request = new ApiRequestHandler();
        request.setHeaders(headers);
        request.setQueryParam(queryParams);
    }

    @Before()
    public void beforeAllHook() {
        System.out.println("This is the before all hook.");
    }

    @Before("@createBoard")
    public void createBoardHook() {
        var boardName = "API board from hook";
        request.setQueryParam("name", boardName);
        request.setEndpoint("/boards/");

        //Act
        Response response = RequestManager.post(request);
        context.setProperty("createBoardResponse", response.getBody().asPrettyString());
        context.setResponse(response);
        String boardID = response.getBody().path("id");
        context.setProperty("boardId", boardID);
        System.out.println(String.format("boardID: %s", boardID));
    }

    @After("@deleteBoard")
    public void deleteBoardHook() {
        String boardId = context.getProperty("boardId");
        System.out.println(String.format("BoardId %s from hook", boardId));
        request.setEndpoint(String.format("/boards/%s", boardId));
        var response = RequestManager.delete(request)
                .then()
                .spec(responseSpec).extract().response();
        System.out.println(response.getBody().asPrettyString());
    }
}
