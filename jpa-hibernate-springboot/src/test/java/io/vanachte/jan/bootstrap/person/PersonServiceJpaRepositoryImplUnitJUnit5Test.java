package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.mapper.MapperConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MapperConfiguration.class})
class PersonServiceJpaRepositoryImplUnitJUnit5Test {

    @Inject
    ApplicationContext applicationContext;

    @InjectMocks
    PersonServiceSpringDataImpl personService;

    @Mock
    PersonReadOnlyRepositorySpringDataImpl personReadOnlyRepository;

    @Mock
    PersonRepositorySpringDataImpl personRepository = mock(PersonRepositorySpringDataImpl.class, withSettings().verboseLogging());

    @Spy
    ModelMapper modelMapper = new ModelMapper();
//
//    @BeforeEach
//    public void injectSpringBeans() {
//        modelMapper= applicationContext.getBean(ModelMapper.class);
//    }

    @Test
    void findAll() {

        PersonJpaEntity entity = new PersonJpaEntity();
        entity.setId(1L);
        entity.setIdentifier("personid");
        entity.setFirstName("firstName");
        entity.setLastName("lastName");

        given(personReadOnlyRepository.findAll()).willReturn(Arrays.asList(entity));

//        when()
        List<Person> actual = personService.findAll();

//        then()
        assertIterableEquivalent(Arrays.asList(Person.builder().identifier("personid").firstName("firstName").lastName("lastName").build()),actual);
    }

    private void assertIterableEquivalent(List<Person> expected, List<Person> actual) {

       if ( !(expected.containsAll(actual) && actual.containsAll(expected))) {
           assertIterableEquals(expected, actual);
       }

    }

    @Test
    void saveAll() {
    }

    @Test
    void save() {
    }
}