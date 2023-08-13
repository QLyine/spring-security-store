package com.service_ft.steps;

import io.restassured.response.Response;

public class ScenarioContext {
    private Response response;
    private String authToken;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
