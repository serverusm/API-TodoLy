package com.trello.api.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CalculadoraStepdefs {
    private int number1;
    private int number2;
    private int result;

    @Given("I have {string} with value {int}")
    public void iHaveWithValue(String numberName, int number) {
        if (numberName.contains("1")) {
            number1 = number;
        }
        if (numberName.contains("2")) {
            number2 = number;
        }
    }

    @When("I perform the {string} of number1 and number2")
    public void iPerformTheOfNumberAndNumber(String method) {
        if(method.equalsIgnoreCase("addition")){
            result = number1 + number2;
        }
        if(method.equalsIgnoreCase("subtraction")){
            result = number1 - number2;
        }
    }
    @Then("I get result of {int}")
    public void iGetResultOf(int expectedResult) {
        Assert.assertEquals(result, expectedResult);
    }


    @Given("I set a mes sage")
    public void iSetAMesSage() {
        System.out.println("Iniciando Calculadora!!!");
    }
}
