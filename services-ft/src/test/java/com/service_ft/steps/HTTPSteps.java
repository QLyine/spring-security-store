package com.service_ft.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.hamcrest.Matchers;

public class HTTPSteps {
    private ScenarioContext scenarioContext;

    public HTTPSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Then("^HTTP response code is (\\d+)")
    public void hTTPResponseIs(int code){
        scenarioContext.getResponse().then().statusCode(code);
    }

    @And("HTTP response body is not empty or null")
    public void httpResponseBodyIsNotEmptyOrNull() {
        scenarioContext.getResponse().then().body(Matchers.allOf(Matchers.notNullValue(), Matchers.not(Matchers.emptyString())));
    }

    @And("^HTTP response body contains the JSON key (\\S+) with string value (\\S+)$")
    public void httpResponseBodyContainsTheJSONKeyWithStringValueValue(String jsonKey, String jsonValue) {
        scenarioContext.getResponse()
                .then().body(jsonKey, Matchers.equalTo(jsonValue));
    }

    @And("^HTTP response body contains the JSON key (\\S+) with int value (\\d+)")
    public void httpResponseBodyContainsTheJSONKeyCaloriesWithIntValueValid(String jsonKey, int jsonValue) {
        scenarioContext.getResponse()
                .then().body(jsonKey, Matchers.equalTo(jsonValue));
    }

    @And("^HTTP response body contains the JSON key (\\S+)$")
    public void httpResponseBodyContainsTheJSONKeyPassword(String jsonKey) {
        scenarioContext.getResponse()
                .then().body(jsonKey, Matchers.notNullValue());
    }
}
