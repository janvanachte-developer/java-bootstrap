package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.address.AddressJpaEntity;
import io.vanachte.jan.bootstrap.address.AddressLineJpaEntity;
import io.vanachte.jan.bootstrap.jpa.HibernateConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        HibernateConfiguration.class
})
@AutoConfigurationPackage // otherwise java.lang.IllegalStateException: Unable to retrieve @EnableAutoConfiguration base packages
@DataJpaTest // https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html
// https://www.baeldung.com/spring-boot-testing
// schema.sql and data.sql are= loaded automagically
public class PersonRepositoryIntegrationJUnit5Test {

    @Inject
    TestEntityManager entityManager;

    @Inject
    PersonReadOnlyRepositorySpringDataImpl repository;

    @Test
    public void findByIdentifier_should_return_entity_if_one_exists() {

        // given
        PersonJpaEntity entity = createPersonEntity("PERSON_IDENTIFIER");
        entityManager.persist(entity);
        entityManager.flush();

        // when
        PersonJpaEntity actual = repository.findByIdentifier("PERSON_IDENTIFIER");

        // then
        assertEquals(entity.getIdentifier(), actual.getIdentifier());
    }

    public static PersonJpaEntity createPersonEntity(String identifier) {

        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setIdentifier(identifier);
        entity.setFirstName("PERSON_FIRST_NAME");
        entity.setLastName("PERSON_LAST_NAME");

        AddressJpaEntity address1 = new AddressJpaEntity();
        address1.setCountry("BE");
        address1.add(new AddressLineJpaEntity("Address 1 Line 1"));
        address1.add(new AddressLineJpaEntity("Address 1 Line 2"));
        entity.add(address1);

        AddressJpaEntity address2 = new AddressJpaEntity();
        address2.setCountry("UK");
        address2.add(new AddressLineJpaEntity("Address 2 Line 1"));
        address2.add(new AddressLineJpaEntity("Address 2 Line 2"));
        address2.add(new AddressLineJpaEntity("Address 2 Line 3"));
        entity.add(address2);

        return entity;
    }

}
