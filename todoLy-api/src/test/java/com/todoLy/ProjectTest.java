package com.todoLy;

import com.todoLy.client.RequestManager;
import com.todoLy.utils.JsonPath;
import com.todoLy.utils.PropertiesInfo;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProjectTest {
  //  private RequestSpecification requestSpec;
  private ResponseSpecification responseSpec;
  private String autUserName;
  private String autPassword;
  private Map<String, String> headers;
  private Map<String, Object> projectObject;
  private Map<String, String> queryParams;
  private String projectID;
  private ApiRequestHandler request;

  @BeforeClass
  public void setUp() {
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

  @Test(priority = 1)
  public void testCreateProjectSchemaValidation() {
    InputStream createProjectJsonSchema = getClass().getClassLoader()
          .getResourceAsStream("schemas/createProjectSchema.json");

    String projectName = "Schema Project from JavaTest";
    request.setEndpoint("/projects.json");
    request.setProjectObject(projectName, 3);

    System.out.println("Request Body: " + request.getBody());

    var response = RequestManager.post(request);
    System.out.println("Response Body POST-(CREATE): " + response.getBody().asPrettyString());

    // validation Create Schema project
    response.then()
          .spec(responseSpec).and()
          .assertThat()
          .body(JsonSchemaValidator.matchesJsonSchema(createProjectJsonSchema))
          .extract().response();

    // Response status 200
    Assert.assertEquals(response.statusCode(), 200);

    // Verify Content
    String content = response.getBody().jsonPath().getString("Content");
    Assert.assertEquals(content, projectName, "El nombre del proyecto no coincide.");

    // Get Project ID and store in projectID
    projectID = response.getBody().jsonPath().getString("Id");
    System.out.println("Project ID: " + projectID);
  }

  @Test(priority = 2)
  public void UpdateProject() {
    //Path Schema
    InputStream updateProjectJsonSchema = getClass().getClassLoader()
          .getResourceAsStream("schemas/updateProjectSchema.json");
    // Use projectID Update project
    String updatedProjectName = "Project Updated from java";

    // endpoint config used projectID update project
    request.setEndpoint("/projects/" + projectID + ".json");
    request.setProjectObject(updatedProjectName, 12);

    System.out.println("Request Body for PUT-(UPDATE): " + request.getBody());

    var response = RequestManager.put(request);
    System.out.println("Response Body for PUT-(UPDATE): " + response.getBody().asPrettyString());

    // validation Create Schema project
    response.then()
          .spec(responseSpec).and()
          .assertThat()
          .body(JsonSchemaValidator.matchesJsonSchema(updateProjectJsonSchema))
          .extract().response();

    // Verify status code update
    Assert.assertEquals(response.statusCode(), 200);

    // Verify name update successfully
    String nameContent = JsonPath.getResult(response.getBody().asPrettyString(), "$.Content");
    System.out.println(String.format("Project Updated from java: %s", nameContent));
    Assert.assertEquals(nameContent, updatedProjectName);

    String content = response.getBody().jsonPath().getString("Content");
    Assert.assertEquals(content, updatedProjectName, "El nombre del proyecto no se actualizó correctamente.");
  }

  @Test(priority = 3)
  public void getProjectTest() {
    // Aquí puedes usar projectID para obtener el proyecto
    String updatedProjectName = "Project Updated from java";

    // Configura el endpoint para obtener el proyecto usando projectID
    request.setEndpoint("/projects/" + projectID + ".json");

    System.out.println("Request Body for GET: " + request.getBody());

    var response = RequestManager.get(request);
    System.out.println("Response Body for GET: " + response.getBody().asPrettyString());

    // Verificar el código de estado de la respuesta de actualización
    Assert.assertEquals(response.statusCode(), 200);

    // Verificar que el nombre se haya obtenido correctamente
    String content = response.getBody().jsonPath().getString("Content");
    Assert.assertEquals(content, updatedProjectName, "El nombre del proyecto no se obtubo correctamente.");

//    //Given
//    InputStream getProjectJsonSchema = getClass().getClassLoader()
//          .getResourceAsStream("schemas/getProjectSchema.json");
//    queryParams.remove("name");
//    request.setEndpoint(String.format("/projects/%s", projectID));
//    var response = RequestManager.get(request);
//    //When
//    response
//          .then()
//          .spec(responseSpec)
//          .and()
//          .assertThat()
//          .body(JsonSchemaValidator.matchesJsonSchema(getProjectJsonSchema))
//          .extract().response();
//    System.out.println(response.getBody().asPrettyString());
//    //Then
//    String name = JsonPath.getResult(response.getBody().asPrettyString(), "$.name");
//    Assert.assertEquals(name, "API refactory Update");
  }

  @Test(priority = 4)
  public void deleteProjectTest() {
    // Aquí puedes usar projectID para eliminar el proyecto
    String updatedProjectName = "Project Updated from java";

    // Configura el endpoint para obtener el proyecto usando projectID
    request.setEndpoint("/projects/" + projectID + ".json");

    System.out.println("Request Body for DELETE: " + request.getBody());

    var response = RequestManager.delete(request);
    System.out.println("Response Body for DELETE: " + response.getBody().asPrettyString());

    // Verificar el código de estado de la respuesta de actualización
    Assert.assertEquals(response.statusCode(), 200);

    // Verificar que el nombre se haya obtenido correctamente
    String content = response.getBody().jsonPath().getString("Content");
    Assert.assertEquals(content, updatedProjectName, "El nombre del proyecto no se puede eliminar correctamente.");

//    InputStream deleteProjectJsonSchema = getClass().getClassLoader()
//          .getResourceAsStream("schemas/deleteProjectSchema.json");
//    request.setEndpoint(String.format("/projects/%s", projectID));
//    var response = RequestManager.delete(request)
//          .then()
//          .spec(responseSpec).and()
//          .assertThat()
//          .body(JsonSchemaValidator.matchesJsonSchema(deleteProjectJsonSchema))
//          .extract().response();
//
//    System.out.println("Project Delete");
//    System.out.println(response.getBody().asPrettyString());
  }

}
