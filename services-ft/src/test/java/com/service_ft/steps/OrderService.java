package com.service_ft.steps;

import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OrderService {

    private static final String ORDER_SERVICE = "http://127.0.0.1:8083";
    private static final String ORDER_API = "/order";

    private ScenarioContext scenarioContext;
    public OrderService(ScenarioContext scenarioContext){
        this.scenarioContext = scenarioContext;
    }


    @When("I post order api with the following")
    public void iPOstOrderApiWithTheFollowing(String jsonBodyRequest) {
        Response response = RestAssured.given()
                .body(jsonBodyRequest)
                .contentType("application/json")
                .header("Authorization", "Bearer " + scenarioContext.getAuthToken())
                .post(ORDER_SERVICE + ORDER_API);
        scenarioContext.setResponse(response);
    }
}
