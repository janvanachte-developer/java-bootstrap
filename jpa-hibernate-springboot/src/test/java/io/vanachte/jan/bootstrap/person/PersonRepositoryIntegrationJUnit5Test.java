package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.jpa.HibernateConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

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
    PersonReadOnlyRepositorySpringDataImpl personRepository;

    @Test
    public void findByIdentifier_should_return_entity_if_one_exists() {

        // given
        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setIdentifier("JUNIT5");
        entityManager.persist(entity);
        entityManager.flush();

        // when
        PersonJpaEntity actual = personRepository.findByIdentifier("JUNIT5");

        // then
        assertEquals(entity.getIdentifier(), actual.getIdentifier());
    }
}
