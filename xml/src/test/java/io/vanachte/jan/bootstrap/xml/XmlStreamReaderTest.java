package io.vanachte.jan.bootstrap.xml;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class XmlStreamReaderTest {

    @Test
    public void cursor() {

        Path path = Paths.get("src/test/resources/input.xml");

        try (InputStream input = Files.newInputStream(path)) {

            XMLInputFactory factory = XMLInputFactory.newInstance();

            try {
                XMLStreamReader r = factory.createXMLStreamReader(input);
                int event = r.getEventType();
                while (true) {
                    switch (event) {
                        case XMLStreamConstants.START_DOCUMENT:
                            out.println("Start Document.");
                            break;
                        case XMLStreamConstants.START_ELEMENT:
                            out.println("Start Element: " + r.getName());
                            for (int i = 0, n = r.getAttributeCount(); i < n; ++i)
                                out.println("Attribute: " + r.getAttributeName(i)
                                        + "=" + r.getAttributeValue(i));

                            break;
                        case XMLStreamConstants.CHARACTERS:
                            if (r.isWhiteSpace())
                                break;

                            out.println("Text: " + r.getText());
                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            out.println("End Element:" + r.getName());
                            break;
                        case XMLStreamConstants.END_DOCUMENT:
                            out.println("End Document.");
                            break;
                    }

                    if (!r.hasNext())
                        break;

                    event = r.next();
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void event() throws XMLStreamException {

        Path path = Paths.get("src/test/resources/input.xml");

        try (InputStream input = Files.newInputStream(path)) {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(input);
            try {
                while (reader.hasNext()) {
                    XMLEvent e = reader.nextEvent();
                    if (e.isCharacters() && ((Characters) e).isWhiteSpace())
                        continue;

                    out.println(e);
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validate() {

        Path path = Paths.get("src/test/resources/input.xml");

        try (InputStream input = Files.newInputStream(path)) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(input);

            reader = new StreamReaderDelegate(reader) {
                public int next() throws XMLStreamException {
                    int n = super.next();

                    // process event

                    return n;
                }
            };

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/test/resources/schema.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(reader));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}