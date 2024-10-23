package com.trello.api.steps;

import com.trello.api.Context;
import com.trello.utils.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;

import java.io.InputStream;
import java.util.logging.Logger;

public class BoardAsserts {
    private Context context;

    public BoardAsserts(Context context) {
        this.context = context;
    }

    @Then("I should see field {string} with value {string}")
    public void iShouldSeeFieldWithValue(String field, String value) {
//        String actualResult = JsonPath.getResult(context.getProperty("createBoardResponse"), String.format("$.%s", field));
        String getResponse = context.getResponse().getBody().asPrettyString();
        System.out.println("---------------getResponse");
        System.out.println(getResponse);
        String actualResult = JsonPath.getResult(context.getResponse().getBody().asPrettyString(), String.format("$.%s", field));
        System.out.println(String.format("New board name: %s", actualResult));
        Assert.assertEquals(actualResult, value, String.format("board name does not match with expected value: %s", value));
    }

    @And("I validate createBoard response schema")
    public void iValidateCreateBoardResponseSchema() {
        InputStream createBoardJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/createBoardSchema.json");
        context.getResponse()
                .then()
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBoardJsonSchema))
                .extract().response();
    }

    @Then("I should see response body as {string}")
    public void iShouldSeeResponseBodyAsValue(String respnseBody) {
        Assert.assertEquals(context.getResponse().getBody().asPrettyString(), respnseBody);
    }

    @Then("I validate that status code of response is {int}")
    public void iValidateThatStatusCodeOfResponseIs(int statusCode) {
        Assert.assertEquals(context.getResponse().statusCode(), statusCode);
    }
}
