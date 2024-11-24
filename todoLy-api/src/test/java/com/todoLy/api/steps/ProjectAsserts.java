package com.todoLy.api.steps;

import com.todoLy.api.Context;
import com.todoLy.utils.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;

import java.io.InputStream;

public class ProjectAsserts {
    private Context context;

    public ProjectAsserts(Context context) {
        this.context = context;
    }

    @Then("I should see field {string} with value {string}")
    public void iShouldSeeFieldWithValue(String field, String value) {
//        String actualResult = JsonPath.getResult(context.getProperty("createBoardResponse"), String.format("$.%s", field));
        String getResponse = context.getResponse().getBody().asPrettyString();
        System.out.println("---------------getResponse Result");
        System.out.println(getResponse);
        String actualResult = JsonPath.getResult(context.getResponse().getBody().asPrettyString(), String.format("$.%s", field));
        System.out.println(String.format("New project name: %s", actualResult));
        Assert.assertEquals(actualResult, value, String.format("Project name does not match with expected value: %s", value));
    }

    @And("I validate createProject response schema")
    public void iValidateCreateProjectResponseSchema() {
        InputStream createProjectJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/createProjectSchema.json");
        context.getResponse()
                .then()
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createProjectJsonSchema))
                .extract().response();
    }

    @Then("I should see response body as {string}")
    public void iShouldSeeResponseBodyAsValue(String respnseBody) {
        Assert.assertEquals(context.getResponse().getBody().asPrettyString(), respnseBody);
    }

    @Then("I validate that status code of response is {int}")
    public void iValidateThatStatusCodeOfResponseIs(int statusCode) {
        int actualStatusCode = context.getResponse().statusCode();
//        Assert.assertEquals(context.getResponse().statusCode(), statusCode);
        Assert.assertEquals(actualStatusCode, statusCode);
        System.out.println(String.format("Status code is: %d", actualStatusCode));
    }
}
