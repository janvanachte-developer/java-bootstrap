package io.vanachte.jan.bootstrap.xml.jaxb;

import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import org.eclipse.persistence.jaxb.*;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContext;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContextFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.bind.JAXBContext;
import javax.xml.stream.XMLStreamWriter;

import org.eclipse.persistence.dynamic.DynamicEntity;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DynamicJaxbContextTest {

    @Test //https://www.eclipse.org/eclipselink/documentation/2.5/solutions/jpatoxml006.htm
    public void test() throws JAXBException {

        // given dynamic jaxbContext and marshaller
        ClassLoader classLoader = DynamicJaxbContextTest.class.getClassLoader();
        InputStream inputStream = DynamicJaxbContextTest.class.getResourceAsStream("/io/vanachte/jan/bootstrap/xml/jaxb/AffiliationOrTerminationApplication.xsd");
        EntityResolver resolver = (publicId, systemId) -> {
            // Grab only the filename part from the full path
            String filename = new File(systemId).getName();
            String correctedId = "io/vanachte/jan/bootstrap/xml/jaxb/" + filename;

            InputSource inputSource = new InputSource(ClassLoader.getSystemResourceAsStream(correctedId));
            inputSource.setSystemId(correctedId);

            return inputSource;
        };
        DynamicJAXBContext jaxbContext = DynamicJAXBContextFactory.createContextFromXSD(inputStream,resolver, classLoader,null);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // given an entity
        DynamicEntity dossier = jaxbContext.newDynamicEntity("be.vlaanderen.mercurius.payload.v1.AffiliationOrTerminationApplicationRequest.Dossier");
        dossier.set("ssin","19600101 999 99");
        List<DynamicEntity> dossiers = new ArrayList<>();
        dossiers.add(dossier);

        DynamicEntity affiliationOrTerminationApplicationRequest = jaxbContext.newDynamicEntity("be.vlaanderen.mercurius.payload.v1.AffiliationOrTerminationApplicationRequest");
        affiliationOrTerminationApplicationRequest.set("dossier",dossiers);

        // given stax
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLStreamWriter xmlStreamWriter = XMLStreamWriterFactory.create(outputStream);

        // when marshalling
       marshaller.marshal(affiliationOrTerminationApplicationRequest, xmlStreamWriter);
        String xml = outputStream.toString();

        // then
        System.out.println(xml);
    }

    @Test //http://blog.bdoughan.com/2013/04/moxys-object-graphs-dynamic-jaxb.html
    public void how_to_specify_object_graph_through_metadata() throws JAXBException {

        Map<String, Object> properties = new HashMap<String, Object>(1);
        properties.put(JAXBContextProperties.OXM_METADATA_SOURCE,
                "io/vanachte/jan/bootstrap/xml/jaxb/DynamicJaxbContextTest-oxm.xml");
        JAXBContext jc = JAXBContext.newInstance("io.vanachte.jan.bootstrap.person",
                DynamicJaxbContextTest.class.getClassLoader(), properties);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("src/test/java/io/vanachte/jan/bootstrap/xml/jaxb/DynamicJaxbContextTest-input.xml");
        DynamicEntity customer = (DynamicEntity) unmarshaller.unmarshal(xml);

        // Output XML
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customer, System.out);

        // Output XML - Based on Object Graph
        marshaller.setProperty(MarshallerProperties.OBJECT_GRAPH, "contact info");
        marshaller.marshal(customer, System.out);

        // Output JSON - Based on Object Graph
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.setProperty(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        marshaller.marshal(customer, System.out);
    }

    @Test // http://blog.bdoughan.com/2013/04/moxys-object-graphs-dynamic-jaxb.html
    public void how_to_create_object_map_dynamically() throws JAXBException {
        Map<String, Object> properties = new HashMap<String, Object>(1);
        properties.put(JAXBContextProperties.OXM_METADATA_SOURCE,
                "io/vanachte/jan/bootstrap/xml/jaxb/DynamicJaxbContextTest-oxm.xml");
        JAXBContext jc = JAXBContext.newInstance("io.vanachte.jan.bootstrap.person",
                DynamicJaxbContextTest.class.getClassLoader(), properties);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("src/test/java/io/vanachte/jan/bootstrap/xml/jaxb/DynamicJaxbContextTest-input.xml");
        DynamicEntity customer = (DynamicEntity) unmarshaller.unmarshal(xml);

        // Output XML
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customer, System.out);

        // Create the Object Graph
        ObjectGraph contactInfo = JAXBHelper.getJAXBContext(jc)
                .createObjectGraph("io.vanachte.jan.bootstrap.person.Person");
        contactInfo.addAttributeNodes("name");
        Subgraph location = contactInfo.addSubgraph("billingAddress");
        location.addAttributeNodes("city", "province");
        Subgraph simple = contactInfo.addSubgraph("phoneNumbers");
        simple.addAttributeNodes("value");

        // Output XML - Based on Object Graph
        marshaller.setProperty(MarshallerProperties.OBJECT_GRAPH, contactInfo);
        marshaller.marshal(customer, System.out);

        // Output JSON - Based on Object Graph
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.setProperty(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        marshaller.marshal(customer, System.out);
    }
}
