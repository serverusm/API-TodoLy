package com.todoLy.api.hooks;

import com.todoLy.ApiRequestHandler;
import com.todoLy.api.Context;
import com.todoLy.client.RequestManager;
import com.todoLy.utils.PropertiesInfo;
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
    private String autUserName;
    private String autPassword;

    public ApiHooks(Context context) {
        this.context = context;
        request = new ApiRequestHandler();
        autUserName = PropertiesInfo.getInstance().getAutUserName();
        autPassword = PropertiesInfo.getInstance().getAutPassword();

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
              .expectContentType(ContentType.JSON)
              .build();

        request.setBasicAuth(autUserName, autPassword);

        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        request.setHeaders(headers);
    }

    @Before()
    public void beforeAllHook() {
        System.out.println("This is the before all hook.");
    }

    @Before("@createProjects")
    public void createProjectHook() {
        String projectName = "Schema Project from JavaTest";
        request.setEndpoint("/projects.json");
        request.setProjectObject(projectName, 3);

        //Act
        Response response = RequestManager.post(request);

        context.setProperty("createBoardResponse", response.getBody().asPrettyString());
        context.setResponse(response);
        String projectID = response.getBody().path("Id");
        context.setProperty("projectId", projectID);
        System.out.println(String.format("projectID: %s", projectID));
    }

    @After("@deleteProjects")
    public void deleteBoardHook() {
        String projectId = context.getProperty("projectId");
        System.out.println(String.format("ProjectId %s from hook", projectId));
        request.setEndpoint(String.format("/projects/%s", projectId + ".json"));
        var response = RequestManager.delete(request)
                .then()
                .spec(responseSpec).extract().response();
        System.out.println(response.getBody().asPrettyString());
    }
}
