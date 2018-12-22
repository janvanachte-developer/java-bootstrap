package io.vanachte.jan.bootstrap.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Named
public class PersonModelApiRestImpl implements PersonModelApi {

    @Inject
    private RestTemplate restTemplate;

    @Override
    public ObservableList<PersonModel> findAll() {

        ResponseEntity<List<Person>> response = restTemplate.exchange(
                "http://localhost:8080/persons/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>(){});
        List<Person> persons = response.getBody();

        return persons.stream()
                .map(person -> new PersonModel(person.getFirstName(), person.getLastName()))
                .collect(toCollection(FXCollections::observableArrayList));
    }
}
