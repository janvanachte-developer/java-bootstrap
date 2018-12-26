package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.mapper.MapperConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MapperConfiguration.class})
class PersonServiceJpaRepositoryImplUnitTest {

    @Inject
    ApplicationContext applicationContext;

    @InjectMocks
    PersonServiceJpaRepositoryImpl personService;

    @Mock
    PersonJpaRepository personJpaRepository = mock(PersonJpaRepository.class, withSettings().verboseLogging());

    @Spy
    ModelMapper modelMapper = new ModelMapper();
//
//    @BeforeEach
//    public void injectSpringBeans() {
//        modelMapper= applicationContext.getBean(ModelMapper.class);
//    }

    @Test
    void findAll() {

        given(personJpaRepository.findAll()).willReturn(Arrays.asList(PersonJpaEntity.builder().id(1L).identifier("personid").firstName("firstName").lastName("lastName").build()));

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