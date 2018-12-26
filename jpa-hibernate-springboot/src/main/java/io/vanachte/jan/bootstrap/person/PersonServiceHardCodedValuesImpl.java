package io.vanachte.jan.bootstrap.person;

import java.util.ArrayList;
import java.util.List;

//@Named
public class PersonServiceHardCodedValuesImpl implements PersonService {

    @Override
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();

        persons.add(Person.builder().firstName("Hans").lastName("Muster").build());
        persons.add(Person.builder().firstName("Ruth").lastName("Dubois").build());
        persons.add(Person.builder().firstName("Heinz").lastName( "Kurz").build());
        persons.add(Person.builder().firstName("Cornelia").lastName("Meier").build());
        persons.add(Person.builder().firstName("Werner").lastName("Meyer").build());
        persons.add(Person.builder().firstName("Lydia").lastName("Kunz").build());
        persons.add(Person.builder().firstName("Anna").lastName("Best").build());
        persons.add(Person.builder().firstName("Stefan").lastName("Meier").build());
        persons.add(Person.builder().firstName("Martin").lastName("Dubois").build());

        return persons;
    }

    @Override
    public List<Person> saveAll(List<Person> persons) {
        return persons;
    }

    @Override
    public Person save(Person person) {
        return person;
    }
}
