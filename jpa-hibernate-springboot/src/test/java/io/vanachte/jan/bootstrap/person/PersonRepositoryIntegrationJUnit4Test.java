package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.jpa.HibernateConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        HibernateConfiguration.class
})
@AutoConfigurationPackage // otherwise java.lang.IllegalStateException: Unable to retrieve @EnableAutoConfiguration base packages
@DataJpaTest // https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html
// https://www.baeldung.com/spring-boot-testing
// schema.sql and data.sql are= loaded automagically
public class PersonRepositoryIntegrationJUnit4Test {

    @Inject
    TestEntityManager entityManager;

    @Inject
    PersonReadOnlyRepositorySpringDataImpl personRepository;

    @Test
    public void findByIdentifier_should_return_entity_if_one_exists() {

        // given
        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setIdentifier("IDENTIFIER");
        entityManager.persist(entity);
        entityManager.flush();

        // when
        PersonJpaEntity actual = personRepository.findByIdentifier("IDENTIFIER");

        // then
        assertEquals(entity.getIdentifier(), actual.getIdentifier());
    }
}
