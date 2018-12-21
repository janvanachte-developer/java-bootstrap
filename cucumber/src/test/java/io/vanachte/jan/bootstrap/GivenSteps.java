package io.vanachte.jan.bootstrap;

import cucumber.api.java.en.Given;

import javax.inject.Inject;

public class GivenSteps {

    @Inject
    World world;

    @Given("there is a name \"(.+)\"")
    public void thereIsANameName(String name) {
        world.setName(name);
    }
}
