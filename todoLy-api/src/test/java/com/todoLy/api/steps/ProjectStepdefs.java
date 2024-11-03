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

public class ProjectStepdefs {
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


    public ProjectStepdefs(Context context, Project projects) {
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

    @When("I get a project with {string}")
    public void iGetAProjectWith(String projectID) { //Method Get
        request.getQueryParams().remove("name");
        this.projectID = projectID.contains("projectId") ? context.getProperty("projectId") : projectID;
        request.setEndpoint("/projects/".concat(this.projectID + ".json"));
        response = RequestManager.get(request);
        context.setResponse(response);
    }

    @When("I update project name with {string}")
    public void iUpdateProjectNameWith(String newProjectName) {
        request.setProjectObject(newProjectName, 12);
        request.setEndpoint(String.format("/projects/%s", context.getProperty("projectId") + ".json"));

        var response = RequestManager.put(request);
        context.setResponse(response);
    }

    @When("I delete a project with {string}")
    public void iDeleteAProjectWith(String projectId) {
        this.projectID = projectId.contains("projectId") ? context.getProperty("projectId") : projectId;
        request.setEndpoint(String.format("/projects/%s", this.projectID) + ".json");
        var response = RequestManager.delete(request);
        context.setResponse(response);
    }

}
