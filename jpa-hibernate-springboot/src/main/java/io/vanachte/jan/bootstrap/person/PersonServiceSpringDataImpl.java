package io.vanachte.jan.bootstrap.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Named
public class PersonServiceSpringDataImpl implements PersonService {

    private final PersonRepositorySpringDataImpl personRepository;
    private final PersonReadOnlyRepositorySpringDataImpl readOnlyRepository;
    private final ModelMapper modelMapper;

    @Inject
    public PersonServiceSpringDataImpl(PersonRepositorySpringDataImpl personRepository, PersonReadOnlyRepositorySpringDataImpl readOnlyRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.readOnlyRepository = readOnlyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Person> findAll() {
        return readOnlyRepository.findAll().stream()
                .map(entity -> map(entity,Person.class))
                .collect(toList());
    }


    @Transactional
    @Override
    public List<Person> saveAll(List<Person> persons) {

        // move to domain-mapper
        // http://modelmapper.org/user-manual/generics/
        Type entitiesListType = new TypeToken<List<PersonJpaEntity>>() {}.getType();
        List<PersonJpaEntity> entities = modelMapper.map(persons, entitiesListType);

        List<PersonJpaEntity> result = personRepository.saveAll(entities);

        Type domainListType = new TypeToken<List<Person>>() {}.getType();
//        return modelMapper.map(result, domainListType);
        return result.stream()
                .map(entity -> map(entity, Person.class))
                .collect(toList());
    }

    private <T> Person map(PersonJpaEntity entity, Class<T> clazz) {
        return Person.builder().identifier(entity.getIdentifier()).firstName(entity.getFirstName()).lastName(entity.getLastName()).build();
    }


    @Override
    @Transactional
    public Person save(Person person) {

        PersonJpaEntity entity = personRepository.save(modelMapper.map(person, PersonJpaEntity.class));

        return map(entity, Person.class);
    }
}
