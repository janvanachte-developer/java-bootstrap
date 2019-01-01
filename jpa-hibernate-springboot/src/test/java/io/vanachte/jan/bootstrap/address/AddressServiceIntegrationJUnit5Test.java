package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;
import io.vanachte.jan.bootstrap.jpa.HibernateConfiguration;
import io.vanachte.jan.bootstrap.jpa.JpaRepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        JpaRepositoryConfiguration.class,
        HibernateConfiguration.class
})
@AutoConfigurationPackage // otherwise java.lang.IllegalStateException: Unable to retrieve @EnableAutoConfiguration base packages
@DataJpaTest // https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html
// https://www.baeldung.com/spring-boot-testing
// schema.sql and data.sql are= loaded automagically
public class AddressServiceIntegrationJUnit5Test {

    @Inject
    TestEntityManager entityManager;

    @Inject
    AddressService service;

    @Test
    public void save_should_persist_parent_and_children() {

        // given
        Country country = Country.builder().code("BE").build();

        // when
        Address actual = service.create(country, "Line 1", "Line 2", "Line 3");

        // then
        assertEquals(3,actual.getLines().size());
    }

    @Test
    public void save_should_persist_all_attributes() {

        // given
        Country country = Country.builder().code("UK").build();

        // when
        Address actual = service.create(country, "Line 1", "Line 2", "Line 3");

        // then
        assertEquals("UK",actual.getCountry().getCode());
        assertEquals(3,actual.getLines().size());
        assertEquals("Line 1",actual.getLines().get(0).getLine());
        assertEquals("Line 2",actual.getLines().get(1).getLine());
        assertEquals("Line 3",actual.getLines().get(2).getLine());
    }
}
