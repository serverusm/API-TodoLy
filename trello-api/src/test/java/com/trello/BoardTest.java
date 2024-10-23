package com.trello;

import com.trello.client.RequestManager;
import com.trello.utils.JsonPath;
import com.trello.utils.PropertiesInfo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BoardTest {
    //    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private String apiKey;
    private String apiToken;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String boardID;
    private ApiRequestHandler request;

    @BeforeClass
    public void setUp() {
        request = new ApiRequestHandler();
        apiKey = PropertiesInfo.getInstance().getApiKey();
        apiToken = PropertiesInfo.getInstance().getApiToken();

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/son");

        queryParams = new HashMap<String, String>();
        queryParams.put("key", apiKey);
        queryParams.put("token", apiToken);

        request.setHeaders(headers);
        request.setQueryParam(queryParams);
    }

    @Test(priority = 1)
    public void testCreateBoardSchemaValidation() {
        //Arrange
        InputStream createBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/createBoardSchema.json");

        String boarName = "API Refacty with ID";

        request.setQueryParam("name", boarName);
        request.setEndpoint("/boards/");
        //Act
//
        var response = RequestManager.post(request);
        response
                .then()
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBoardJsonSchema))
                .extract().response();

        System.out.println(response.getBody().asPrettyString());
        //Assert
        Assert.assertEquals(response.statusCode(), 200);

        boardID = response.getBody().path("id");
        System.out.println(String.format("boardID: %s", boardID));

        String name = JsonPath.getResult(response.getBody().asPrettyString(), "$.name");
        System.out.println(String.format("New board name: %s", name));
        Assert.assertEquals(name, boarName);
    }

    @Test(priority = 2)
    public void UpdateBoard() {
        //AAA
        //Arrange
        InputStream updateBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/updateBoardSchema.json");

        String boarName = "API refactory Update";

        request.setQueryParam("name", boarName);
        request.setEndpoint(String.format("/boards/%s", boardID));
        //Act
        var response = RequestManager.put(request);
        response
                .then()
                .spec(responseSpec)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(updateBoardJsonSchema))
                .extract().response();

        System.out.println(response.getBody().asPrettyString());
        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        String name = JsonPath.getResult(response.getBody().asPrettyString(), "$.name");
        Assert.assertEquals(name, "API refactory Update");
    }

    @Test(priority = 3)
    public void getBoardTest() {
        //Given
        InputStream getBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/getBoardSchema.json");
        queryParams.remove("name");
        request.setEndpoint(String.format("/boards/%s", boardID));
        var response = RequestManager.get(request);
        //When
        response
                .then()
                .spec(responseSpec)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(getBoardJsonSchema))
                .extract().response();
        System.out.println(response.getBody().asPrettyString());
        //Then
        String name = JsonPath.getResult(response.getBody().asPrettyString(), "$.name");
        Assert.assertEquals(name, "API refactory Update");
    }

    @Test(priority = 4)
    public void deleteBoardTest() {
        InputStream deleteBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/deleteBoardSchema.json");
        request.setEndpoint(String.format("/boards/%s", boardID));
        var response = RequestManager.delete(request)
                .then()
                .spec(responseSpec).and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(deleteBoardJsonSchema))
                .extract().response();

        System.out.println("Board Delete");
        System.out.println(response.getBody().asPrettyString());
    }

}
