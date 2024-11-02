package com.todoLy.api.steps;

import com.todoLy.ApiRequestHandler;
import com.todoLy.api.Context;
import com.todoLy.client.RequestManager;
import com.todoLy.endpoints.Project;
import com.todoLy.utils.PropertiesInfo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

public class BoardStepdefs {
    private ResponseSpecification responseSpec;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private ApiRequestHandler request;
    private Response response;
    private String projectID;
    private Context context;
    private Project projects;
    private String autUserName;
    private String autPassword;
    private String Id;


    public BoardStepdefs(Context context, Project projects) {
        this.context = context;
        this.projects = projects;
    }

    @Given("I set apiRequestHandler with proper credential") //Background
    public void iSetApiRequestHandlerWithProperCredential() {
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

    @When("I create a project with name {string}")
    public void iCreateAProjectWithName(String projectName) {
        response = this.projects.createProject(projectName);
        context.setProperty("createProjectResponse", response.getBody().asPrettyString());
        context.setResponse(response);
        projectID = response.getBody().path("Id").toString();
        System.out.println(String.format("projectID: %s", projectID));
        context.setProperty("projectId", projectID);
    }

    @When("I get a board with {string}")
    public void iGetABoardWith(String boardID) { //Method Get
        request.getQueryParams().remove("name");
        this.projectID = boardID.contains("boardId") ? context.getProperty("boardId") : boardID;
        request.setEndpoint("/boards/".concat(this.projectID));
        response = RequestManager.get(request);
        context.setResponse(response);
    }

    @When("I update board name with {string}")
    public void iUpdateBoardNameWith(String newBoardName) {
        request.setQueryParam("name", newBoardName);
        request.setEndpoint(String.format("/boards/%s", context.getProperty("boardId")));

        var response = RequestManager.put(request);
        context.setResponse(response);
    }

    @When("I delete a board with {string}")
    public void iDileteABoardWith(String boardId) {
        this.projectID = boardId.contains("boardId") ? context.getProperty("boardId") : boardId;
        request.setEndpoint(String.format("/boards/%s", this.projectID));
        var response = RequestManager.delete(request);
        context.setResponse(response);
    }

}
