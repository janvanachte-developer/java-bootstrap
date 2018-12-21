package io.vanachte.jan.bootstrap.person;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class PersonRestController {

    private final PersonService service;

    @Inject
    public PersonRestController(PersonService service) {
        this.service = service;
    }

    @RequestMapping("/persons")
    public List<Person> findAll() {
        return service.findAll();
    }
}
