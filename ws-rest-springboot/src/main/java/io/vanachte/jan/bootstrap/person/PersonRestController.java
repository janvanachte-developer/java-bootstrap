package io.vanachte.jan.bootstrap.person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.List;

@RestController
@Slf4j
public class PersonRestController {

    private final PersonService personService;
    private final PersonXmlFacade personXmlFacade;
    private final PersonXmlMapper personMapper;

    @Inject
    public PersonRestController(PersonService personService, PersonXmlFacade personXmlFacade, PersonXmlMapper mapper) {
        this.personService = personService;
        this.personXmlFacade = personXmlFacade;
        this.personMapper = mapper;
    }

    @RequestMapping("/persons") // @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping("/upload") //https://www.jeejava.com/file-upload-example-using-spring-rest-controller/
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        if (file == null) {
            throw new RuntimeException("You must select a file for uploading");
        }

        InputStream inputStream = file.getInputStream();
        String originalName = file.getOriginalFilename();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();

        log.info("inputStream: " + inputStream);
        log.info("originalName: " + originalName);
        log.info("name: " + name);
        log.info("contentType: " + contentType);
        log.info("size: " + size);

        // Do processing with uploaded file data in Service layer
        personXmlFacade.savePersonsFromXmlEventReader(inputStream);

        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }
}
