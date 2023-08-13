package com.service_ft.steps;

import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductService {

    private static final String PRODUCT_SERVICE = "http://127.0.0.1:8082";
    private static final String PRODUCT_API = "/product";

    private ScenarioContext scenarioContext;
    public ProductService(ScenarioContext scenarioContext){
        this.scenarioContext = scenarioContext;
    }

    @When("I post product api with the following")
    public void iPostProductApiWithTheFollowing(String jsonRequestBody) {
        Response response = RestAssured.given()
                .body(jsonRequestBody)
                .contentType("application/json")
                .header("Authorization", "Bearer " + scenarioContext.getAuthToken())
                .post(PRODUCT_SERVICE + PRODUCT_API);

        this.scenarioContext.setResponse(response);
    }

    @When("I get product api")
    public void iGetProductApi() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + scenarioContext.getAuthToken())
                .get(PRODUCT_SERVICE + PRODUCT_API);

        this.scenarioContext.setResponse(response);
    }

    @When("^I get product api \\/product\\/(\\d+)$")
    public void iGetProductApiProduct(int productId) {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + scenarioContext.getAuthToken())
                .get(PRODUCT_SERVICE + PRODUCT_API + "/" + productId);

        this.scenarioContext.setResponse(response);
    }
}
