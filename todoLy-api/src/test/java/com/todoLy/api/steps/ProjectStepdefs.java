package com.todoLy.api.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
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
  //    private Map<String, String> mapper;
  private ApiRequestHandler request;
  private Response response;
  //    private String projectID;
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

  @Given("I set auth {string} and {string}")
  public void iSetAuthAnd(String user, String pw) {
    request = new ApiRequestHandler();

    responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
          .expectContentType(ContentType.JSON)
          .build();

    request.setBasicAuth(user, pw);

    headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/json");
    request.setHeaders(headers);
  }

  @When("I create a project with:$")
  public void iCreateAProjectWith(String valueJson) throws JsonProcessingException {
//    response = this.projects.projectCreate(valueJson);
    request.setEndpoint("/projects.json");
    request.setBody(valueJson);
    response = RequestManager.post(request);
    context.setProperty("createProjectResponse", response.getBody().asPrettyString());
    context.setResponse(response);
    context.setProperty("projectId", (response.getBody().path("Id").toString()));
    System.out.println(String.format("projectID: %s", context.getProperty("projectId")));
  }

  @When("I create a project with name {string}")
  public void iCreateAProjectWithName(String projectName) {
    response = this.projects.createProject(projectName);
    context.setProperty("createProjectResponse", response.getBody().asPrettyString());
    context.setResponse(response);
    context.setProperty("projectId", (response.getBody().path("Id").toString()));
    System.out.println(String.format("projectID: %s", context.getProperty("projectId")));
  }

  @When("I get a project with {string}")
  public void iGetAProjectWith(String projectID) { //Method Get
    request.getQueryParams().remove("name");
    var projectId = projectID.contains("projectId") ? context.getProperty("projectId") : projectID;
    request.setEndpoint("/projects/".concat(projectId + ".json"));
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
    var projectID = projectId.contains("projectId") ? context.getProperty("projectId") : projectId;
    request.setEndpoint(String.format("/projects/%s", projectID) + ".json");
    var response = RequestManager.delete(request);
    context.setResponse(response);
  }

}
