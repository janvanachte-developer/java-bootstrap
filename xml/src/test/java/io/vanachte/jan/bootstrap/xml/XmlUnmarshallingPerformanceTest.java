package io.vanachte.jan.bootstrap.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.codehaus.stax2.XMLInputFactory2;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import xml.integration.jemos.co.uk.large_file.ObjectFactory;
import xml.integration.jemos.co.uk.large_file.PersonType;
import xml.integration.jemos.co.uk.large_file.PersonsType;

import javax.xml.bind.*;
import javax.xml.stream.*;
import java.io.*;
import java.util.List;

@Slf4j
public class XmlUnmarshallingPerformanceTest {

    private static final String OUTPUT_FOLDER = System.getProperty("user.home")
            + File.separatorChar + "xml-benchmark";

    @Test
    public void compare_jaxb_stax_woodstox() {

        try {

            File outputDir = new File(OUTPUT_FOLDER);
            if (!outputDir.exists()) {
                log.info("Creating output folder: "
                        + outputDir.getAbsolutePath());
                boolean created = outputDir.mkdirs();
                if (!created) {
                    throw new IllegalStateException("Could not create "
                            + outputDir.getAbsolutePath() + ". Aborting...");
                }
            }
            createXmlPortfolio();

            System.gc();
            System.gc();

            for (int i = 0; i < 10; i++) {

                readLargeFileWithJaxb(new File(OUTPUT_FOLDER
                        + File.separatorChar + "large-person-10000.xml"), 10000);
                readLargeFileWithJaxb(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-100000.xml"),
                        100000);
                readLargeFileWithJaxb(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-1000000.xml"),
                        1000000);

                readLargeXmlWithStax(new File(OUTPUT_FOLDER
                        + File.separatorChar + "large-person-10000.xml"), 10000);
                readLargeXmlWithStax(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-100000.xml"),
                        100000);
                readLargeXmlWithStax(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-1000000.xml"),
                        1000000);

                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
                        + File.separatorChar + "large-person-10000.xml"), 10000);
                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-100000.xml"),
                        100000);
                readLargeXmlWithFasterStax(new File(OUTPUT_FOLDER
                                + File.separatorChar + "large-person-1000000.xml"),
                        1000000);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void createXmlPortfolio() throws Exception {
        createXml(10000, OUTPUT_FOLDER + File.separatorChar
                + "large-person-10000.xml");
        log.info("Completed generation of large XML with 10,000 entries...");
        createXml(100000, OUTPUT_FOLDER + File.separatorChar
                + "large-person-100000.xml");
        log.info("Completed generation of large XML with 100,000 entries...");
        createXml(1000000, OUTPUT_FOLDER + File.separatorChar
                + "large-person-1000000.xml");
        log.info("Completed generation of large XML with 1,000,000 entries...");
    }

    private void createXml(int nbrElements, String fileName) throws Exception {

        JAXBContext context = JAXBContext
                .newInstance("xml.integration.jemos.co.uk.large_file");

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        PersonsType personsType = new ObjectFactory().createPersonsType();
        List<PersonType> persons = personsType.getPerson();
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < nbrElements; i++) {
            persons.add(factory.manufacturePojo(PersonType.class));
        }

        JAXBElement<PersonsType> toWrite = new ObjectFactory()
                .createPersons(personsType);

        File file = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file), 4096);

        try {
            marshaller.marshal(toWrite, bos);
            bos.flush();
        } finally {
            IOUtils.closeQuietly(bos);
        }

    }

    private void readLargeFileWithJaxb(File file, int nbrRecords)
            throws Exception {

        JAXBContext ucontext = JAXBContext
                .newInstance("xml.integration.jemos.co.uk.large_file");
        Unmarshaller unmarshaller = ucontext.createUnmarshaller();

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                file));

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            JAXBElement<PersonsType> root = (JAXBElement<PersonsType>) unmarshaller
                    .unmarshal(bis);

            root.getValue().getPerson().size();

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("JAXB (" + nbrRecords + "): - Total Memory used: "
                    + (memstart - memend));

            log.info("JAXB (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            IOUtils.closeQuietly(bis);
        }

    }

    private void readLargeXmlWithStax(File file, int nbrRecords)
            throws FactoryConfigurationError, XMLStreamException,
            FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader xmlr = xmlif
                .createXMLStreamReader(new FileReader(file));

        JAXBContext ucontext = JAXBContext.newInstance(PersonType.class);

        Unmarshaller unmarshaller = ucontext.createUnmarshaller();

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            xmlr.nextTag();
            xmlr.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlr.nextTag();
            while (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {

                JAXBElement<PersonType> pt = unmarshaller.unmarshal(xmlr,
                        PersonType.class);

                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlr.next();
                }
            }

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("STax - (" + nbrRecords + "): - Total memory used: "
                    + (memstart - memend));

            log.info("STax - (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            xmlr.close();
        }

    }

    private void readLargeXmlWithFasterStax(File file, int nbrRecords)
            throws FactoryConfigurationError, XMLStreamException,
            FileNotFoundException, JAXBException {

        // set up a StAX reader
        XMLInputFactory xmlif = XMLInputFactory2.newInstance();
        XMLStreamReader xmlr = xmlif
                .createXMLStreamReader(new FileReader(file));

        JAXBContext ucontext = JAXBContext.newInstance(PersonType.class);

        Unmarshaller unmarshaller = ucontext.createUnmarshaller();

        long start = System.currentTimeMillis();
        long memstart = Runtime.getRuntime().freeMemory();
        long memend = 0L;

        try {
            xmlr.nextTag();
            xmlr.require(XMLStreamConstants.START_ELEMENT, null, "persons");

            xmlr.nextTag();
            while (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {

                JAXBElement<PersonType> pt = unmarshaller.unmarshal(xmlr,
                        PersonType.class);

                if (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
                    xmlr.next();
                }
            }

            memend = Runtime.getRuntime().freeMemory();

            long end = System.currentTimeMillis();

            log.info("Woodstox - (" + nbrRecords + "): Total memory used: "
                    + (memstart - memend));

            log.info("Woodstox - (" + nbrRecords + "): Time taken in ms: "
                    + (end - start));

        } finally {
            xmlr.close();
        }

    }
}
