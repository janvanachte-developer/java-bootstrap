package io.vanachte.jan.bootstrap.person;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static java.util.stream.Collectors.toList;

//import org.modelmapper.ModelMapper;

@Named
public class PersonServiceJpaRepositoryImpl implements PersonService {

    private final PersonJpaRepository personRepository;

//    private final ModelMapper modelMapper;

    @Inject
    public PersonServiceJpaRepositoryImpl(PersonJpaRepository personRepository) {
        this.personRepository = personRepository;
//        this.modelMapper = modelMapper;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll().stream()
                .map(entity -> map(entity,Person.class))
                .collect(toList());
    }

    private <T> Person map(PersonJpaEntity entity, Class<T> clazz) {
        return Person.builder().identifier(entity.getIdentifier()).firstName(entity.getFirstName()).lastName(entity.getLastName()).build();
    }
}
