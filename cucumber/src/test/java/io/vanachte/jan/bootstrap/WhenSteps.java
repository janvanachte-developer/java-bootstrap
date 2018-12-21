package io.vanachte.jan.bootstrap;

import cucumber.api.java.en.When;

import javax.inject.Inject;

public class WhenSteps {

    @Inject
    World world;

    @When("name is given to (Foo|Bar) api")
    public void nameIsGivenToApi()  { ;
    }
}
