package io.vanachte.jan.bootstrap;

import cucumber.api.java.en.Then;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class ThenSteps {

    @Inject
    World world;

    @Then("a Foo is created with name {string}")
    public void aFooIsCreatedWithName(String name) throws Throwable {
        assertEquals(name, world.getName());
    }

    @Then("a Bar is created with name {string}")
    public void aBarIsCreatedWithName(String name) throws Throwable {
        assertEquals(name, world.getName());
    }

}
