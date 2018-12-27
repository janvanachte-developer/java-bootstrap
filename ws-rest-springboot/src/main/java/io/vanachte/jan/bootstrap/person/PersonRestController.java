package io.vanachte.jan.bootstrap.person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import java.io.*;
import java.util.List;

@RestController
@Slf4j
public class PersonRestController {

    private final PersonService service;
    private final PersonMapperImpl mapper;

    @Inject
    public PersonRestController(PersonService service, PersonMapperImpl mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @RequestMapping("/persons") // @GetMapping
    public List<Person> findAll() {
        return service.findAll();
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
        savePersonsFromXmlStreamReader(inputStream);

        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }

    private void savePersonsFromXmlStreamReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try {
            xmlStreamReader.nextTag();
            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlStreamReader.nextTag();
            while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {

                JAXBElement<PersonType> jaxbPersonType = unmarshaller.unmarshal(xmlStreamReader, PersonType.class);
                if (xmlStreamReader.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlStreamReader.next();
                }

                Person person = mapper.map(jaxbPersonType.getValue(), Person.class);
                service.save(person);

            }
        } finally {
            xmlStreamReader.close();
        }
    }

//    private void savePersonsFromXmlEventReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {
//
//        // set up a StAX reader
//        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
//        XMLEventReader xmlStreamReader = xmlInputFactory.createXMLEventReader(inputStream);
//
//        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//
//        try {
//            xmlStreamReader.nextTag();
//            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, "persons");
//
//            xmlStreamReader.nextTag();
//            while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {
//
//                JAXBElement<PersonType> jaxbPersonType = unmarshaller.unmarshal(xmlStreamReader, PersonType.class);
//                if (xmlStreamReader.getEventType() == XMLStreamConstants.CHARACTERS) {
//                    xmlStreamReader.next();
//                }
//
//                Person person = mapper.map(jaxbPersonType.getValue(), Person.class);
//                service.save(person);
//
//            }
//        } finally {
//            xmlStreamReader.close();
//        }
//    }

}
