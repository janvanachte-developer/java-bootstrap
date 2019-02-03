package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.person.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.codehaus.stax2.XMLInputFactory2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.xml.bind.*;
import javax.xml.stream.*;
import java.io.*;
import java.util.List;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PersonXmlFacadePerformanceTest {

    @InjectMocks
    PersonXmlFacadeStaxImpl stax;

    @Spy
    PersonXmlMapperModelMapperImpl personXmlMapper;

    @Mock
    PersonService personService;

    private static final String OUTPUT_FOLDER = "/tmp" + File.separatorChar + "xml-benchmark";

    @Test
    public void compare_jaxb_stax_woodstox() {

        try {
            File outputFolder = new File(OUTPUT_FOLDER);
            if (!outputFolder.exists()) {
                log.info("Creating output folder: "
                        + outputFolder.getAbsolutePath());
                boolean created = outputFolder.mkdirs();
                if (!created) {
                    throw new IllegalStateException("Could not create "
                            + outputFolder.getAbsolutePath() + ". Aborting...");
                }
            }

            if (outputFolder != null && outputFolder.list() != null && outputFolder.list().length == 0) {
                createXmlTestFiles();
            }

            System.gc();
            System.gc();

//            for (int i = 0; i < 10; i++) {
            for (int i = 0; i < 2; i++) {

                File file = new File(OUTPUT_FOLDER + File.separatorChar + "large-person-10000.xml");

                try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){

                    long start = System.currentTimeMillis();
                    long memstart = Runtime.getRuntime().freeMemory();

                    int number = stax.savePersonsFromXmlStreamReader(inputStream);

                    long memend = Runtime.getRuntime().freeMemory();
                    long end = System.currentTimeMillis();

                    log.info("Stax Stream Reader (" + number + "): - Total Memory used: " + (memstart - memend));
                    log.info("Stax Stream Reader (" + number + "): -  Time taken in ms: " + (end - start));
                }

                try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){

                    long start = System.currentTimeMillis();
                    long memstart = Runtime.getRuntime().freeMemory();

                    int number = stax.savePersonsFromXmlEventReader(inputStream);

                    long memend = Runtime.getRuntime().freeMemory();
                    long end = System.currentTimeMillis();

                    log.info("Stax Event Reader (" + number + "): - Total Memory used: " + (memstart - memend));
                    log.info("Stax Event Reader (" + number + "): -  Time taken in ms: " + (end - start));
                }

//                readLargeFileWithJaxb(new File(OUTPUT_FOLDER + File.separatorChar + "large-person-10000.xml"), 10000);
//                readLargeFileWithJaxb(new File(OUTPUT_FOLDER + File.separatorChar + "large-person-100000.xml"),
//                        100000);
//                readLargeFileWithJaxb(new File(OUTPUT_FOLDER
//                                + File.separatorChar + "large-person-1000000.xml"),
//                        1000000);
//
//                readLargeXmlWithStax(new File(OUTPUT_FOLDER
//                        + File.separatorChar + "large-person-10000.xml"), 10000);
//                readLargeXmlWithStax(new File(OUTPUT_FOLDER
//                                + File.separatorChar + "large-person-100000.xml"),
//                        100000);
//                readLargeXmlWithStax(new File(OUTPUT_FOLDER
//                                + File.separatorChar + "large-person-1000000.xml"),
//                        1000000);
//
//                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
//                        + File.separatorChar + "large-person-10000.xml"), 10000);
//                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
//                                + File.separatorChar + "large-person-100000.xml"),
//                        100000);
//                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
//                                + File.separatorChar + "large-person-1000000.xml"),
//                        1000000);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void createXmlTestFiles() throws Exception {
        createXml(10, OUTPUT_FOLDER + File.separatorChar
                + "large-person-10000.xml");
        log.info("Completed generation of large XML with 10,000 entries...");
        createXml(10, OUTPUT_FOLDER + File.separatorChar
                + "large-person-100000.xml");
        log.info("Completed generation of large XML with 100,000 entries...");
        createXml(10, OUTPUT_FOLDER + File.separatorChar
                + "large-person-1000000.xml");
        log.info("Completed generation of large XML with 1,000,000 entries...");
    }

    private void createXml(int nbrElements, String fileName) throws Exception {

        PersonsType personsType = new ObjectFactory().createPersonsType();
        List<PersonType> persons = personsType.getPerson();
        PodamFactory factory = new PodamFactoryImpl();

        for (int i = 0; i < nbrElements; i++) {
            persons.add(factory.manufacturePojo(PersonType.class));
        }

        JAXBElement<PersonsType> toWrite = new ObjectFactory().createPersons(personsType);

        File file = new File(fileName);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 4096);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("io.vanachte.jan.bootstrap.person");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(toWrite, outputStream);
            outputStream.flush();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }

    }

    private void readLargeFileWithJaxb(File file, int nbrRecords) throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance("io.vanachte.jan.bootstrap.person");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(
                file));

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            JAXBElement<PersonsType> root = (JAXBElement<PersonsType>) unmarshaller.unmarshal(inputStream);

            root.getValue().getPerson().size();

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("JAXB (" + nbrRecords + "): - Total Memory used: "
                    + (memstart - memend));

            log.info("JAXB (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }

    private void readLargeXmlWithStax(File file, int nbrRecords) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileReader(file));

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            xmlStreamReader.nextTag();
            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlStreamReader.nextTag();
            while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {

                JAXBElement<PersonType> personType = unmarshaller.unmarshal(xmlStreamReader, PersonType.class);
                if (xmlStreamReader.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlStreamReader.next();
                }
            }

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("STax - (" + nbrRecords + "): - Total memory used: "
                    + (memstart - memend));

            log.info("STax - (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            xmlStreamReader.close();
        }

    }

    private void readLargeXmlWithFasterStax(File file, int nbrRecords) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlInputFactory = XMLInputFactory2.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileReader(file));

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            xmlStreamReader.nextTag();
            xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlStreamReader.nextTag();
            while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                JAXBElement<PersonType> personType = unmarshaller.unmarshal(xmlStreamReader, PersonType.class);

                if (xmlStreamReader.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlStreamReader.next();
                }
            }

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("Woodstox - (" + nbrRecords + "): Total memory used: "
                    + (memstart - memend));

            log.info("Woodstox - (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            xmlStreamReader.close();
        }

    }
}
