package io.vanachte.jan.bootstrap.person;

import javax.xml.bind.JAXBException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface PersonXmlFacade {
    int savePersonsFromXmlStreamReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException;

    int savePersonsFromXmlEventReader(InputStream inputStream) throws FactoryConfigurationError, XMLStreamException, FileNotFoundException, JAXBException;
}
