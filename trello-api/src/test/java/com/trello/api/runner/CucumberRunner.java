package com.trello.api.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Cucumber Runner.
 */
@CucumberOptions(
        plugin = {"pretty", "json:reports/cucumber.json", "junit:report/cucumber.xml",
        "html:report/cucumber-html-report.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        features = {"src/test/resources/features"},
//        trello-api\src\test\resources\features
        glue = {"com.trello.api"}
//        strict = true
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
