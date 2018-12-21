package io.vanachte.jan.bootstrap;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"io/vanachte/jan/kickstarter/cucumber"},
        plugin = {"pretty"}
        )
public class AcceptanceTest {

}
