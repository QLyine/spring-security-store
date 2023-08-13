package com.service_ft.steps;

import com.sun.net.httpserver.Headers;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class UserService {

    private static final String USER_SERVICE = "http://127.0.0.1:8081";
    private static final String REGISTER_API = "/user";
    private static final String LOGIN_API = "/login";

    private ScenarioContext scenarioContext;
    public UserService(ScenarioContext scenarioContext){
        this.scenarioContext = scenarioContext;
    }

    @When("I call user register api with the following")
    public void iCallUserRegisterAPIWithTheFollowing(String jsonRequest){
        Response response = RestAssured
                .given()
                .body(jsonRequest)
                .header("Content-Type", "application/json")
                .post(USER_SERVICE + REGISTER_API);

        scenarioContext.setResponse(response);
    }

    @When("I call user login api")
    public void iCallUserLoginApi() {
        final String jsonBody = String.format("""
                    {"username": "%s", "password": "%s"}
                """, UserDatabase.VALID_USER.username(), UserDatabase.VALID_USER.password());
        Response response = RestAssured.given()
                .body(jsonBody)
                .header("Content-Type", "application/json")
                .post(USER_SERVICE + LOGIN_API);

        scenarioContext.setResponse(response);
        scenarioContext.setAuthToken(response.getBody().asString());
    }

    @When("^I call api \\/user\\/(\\d+)$")
    public void iCallApiUser(int userId) {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer "  + scenarioContext.getAuthToken())
                .get(USER_SERVICE + "/user/" + userId);

        scenarioContext.setResponse(response);
    }

    @When("^I call api \\/user\\/(\\d+) with wrong auth$")
    public void iCallApiUserWithWrongAuth(int userId) {
        Response response = RestAssured.given()
                .get(USER_SERVICE + "/user/" + userId);

        scenarioContext.setResponse(response);
    }
}
