package com.franzzle.tooling.lipsync.api.service;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class ListConvertablesStepDefinitions {
    public static final String BASE_URI = "http://localhost:9280";

    @Given("all existing convertables are removed")
    public void removeAllExisting() {

    }

    @Given("at least one convertable is found")
    public void allStepDefinitionsAreImplemented() {
        RestAssured.baseURI = BASE_URI;
        RequestSpecification request = RestAssured.given();
        final Response response = request.get("/conversion");

        String jsonString = response.asString();
        List<Map<String, String>> convertables = JsonPath.from(jsonString).get("convertables");
        Assertions.assertEquals(0, convertables.size());
    }

    @When("add one convertable")
    public void addOneConvertable() {
        //TODO
    }

    @Then("the new convertable shows up in the list")
    public void theScenarioPasses() {
        //TODO
    }
}
