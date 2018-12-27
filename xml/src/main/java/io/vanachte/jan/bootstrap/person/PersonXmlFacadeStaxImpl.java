package io.vanachte.jan.bootstrap.person;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.lang.System.out;

@Named
public class PersonXmlFacadeStaxImpl implements PersonXmlFacade {

    private final PersonService personService;
    private final PersonXmlMapper personXmlMapper;

    @Inject
    public PersonXmlFacadeStaxImpl(PersonService personService, PersonXmlMapper personXmlMapper) {
        this.personService = personService;
        this.personXmlMapper = personXmlMapper;
    }

    @Override
    public int savePersonsFromXmlStreamReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        int counter = 0;

        try {
            xmlStreamReader.nextTag();
            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlStreamReader.nextTag();
            while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {

                JAXBElement<PersonType> jaxbPersonType = unmarshaller.unmarshal(xmlStreamReader, PersonType.class);
                if (xmlStreamReader.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlStreamReader.next();
                }

                Person person = personXmlMapper.map(jaxbPersonType.getValue(), Person.class);
                personService.save(person);
                counter++;
            }
        } finally {
            xmlStreamReader.close();
        }

        return counter;
    }

    @Override // https://stackoverflow.com/questions/25643097/can-a-part-of-xml-be-marshalled-using-jaxb-or-jaxb-stax
    public int savePersonsFromXmlEventReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {

        int counter = 0;

        // set up a StAX reader
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        try {
            while (xmlEventReader.hasNext()) {
                if(xmlEventReader.peek().isStartElement() && xmlEventReader.peek().asStartElement().getName().getLocalPart().equals("person")) {
                    // Unmarshal the File object from the XMLEventReader
                    JAXBElement<PersonType> jaxbPersonType = unmarshaller.unmarshal(xmlEventReader, PersonType.class);
                    Person person = personXmlMapper.map(jaxbPersonType.getValue(), Person.class);
                    personService.save(person);
                    counter++;
                } else {
                    xmlEventReader.nextEvent();
                }
            }
        } finally {
            xmlEventReader.close();
        }
        return counter;
    }

}
