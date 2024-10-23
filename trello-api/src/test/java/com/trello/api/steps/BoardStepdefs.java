package com.trello.api.steps;

import com.trello.ApiRequestHandler;
import com.trello.api.Context;
import com.trello.client.RequestManager;
import com.trello.endpoints.Boards;
import com.trello.utils.JsonPath;
import com.trello.utils.PropertiesInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BoardStepdefs {
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private ApiRequestHandler request;
    private Response response;
    private String boardID;
    private Context context;
    private Boards boards;

    public BoardStepdefs(Context context, Boards boards) {
        this.context = context;
        this.boards = boards;
    }

    @Given("I set apiRequestHandler with proper credential") //Background
    public void iSetApiRequestHandlerWithProperCredential() {
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/son");

        queryParams = new HashMap<String, String>();
        queryParams.put("key", PropertiesInfo.getInstance().getApiKey());
        queryParams.put("token", PropertiesInfo.getInstance().getApiToken());
        request = new ApiRequestHandler();
        request.setHeaders(headers);
        request.setQueryParam(queryParams);
    }

    @When("I create a board with name {string}")
    public void iCreateABoardWithName(String boardName) {
        response = this.boards.createBoard(boardName);
        context.setProperty("createBoardResponse", response.getBody().asPrettyString());
        context.setResponse(response);
        boardID = response.getBody().path("id");
        System.out.println(String.format("boardID: %s", boardID));
        context.setProperty("boardId", boardID);
    }

    @When("I get a board with {string}")
    public void iGetABoardWith(String boardID) { //Method Get
        request.getQueryParams().remove("name");
        this.boardID = boardID.contains("boardId") ? context.getProperty("boardId") : boardID;
        request.setEndpoint("/boards/".concat(this.boardID));
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
        this.boardID = boardId.contains("boardId") ? context.getProperty("boardId") : boardId;
        request.setEndpoint(String.format("/boards/%s", this.boardID));
        var response = RequestManager.delete(request);
        context.setResponse(response);
    }

}
