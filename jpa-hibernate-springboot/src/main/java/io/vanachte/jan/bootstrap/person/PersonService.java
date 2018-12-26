package io.vanachte.jan.bootstrap.person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    List<Person> saveAll(List<Person> persons);

    Person save(Person person);
}
