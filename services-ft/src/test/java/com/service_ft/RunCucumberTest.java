package com.service_ft;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.service_ft.steps"},
  plugin = {"pretty", "html:target/cucumber-report.html"},
  features = {"src/test/resources"}
)
public class RunCucumberTest {
}
