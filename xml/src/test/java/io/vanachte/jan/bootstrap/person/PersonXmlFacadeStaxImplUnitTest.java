package io.vanachte.jan.bootstrap.person;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PersonXmlFacadeStaxImplUnitTest {

    @InjectMocks
    PersonXmlFacadeStaxImpl personXmlFacade;

    @Mock
    PersonService personService;

    @Spy
    PersonXmlMapper personMapper = new PersonXmlMapperModelMapperImpl();

    @Test
    void stax_xmlStreamReader_should_upload_xml_file() throws Exception {

        // given
        InputStream inputStream = xml_file_with_10000_persons();

        // when
        int actual = personXmlFacade.savePersonsFromXmlStreamReader(inputStream);

        // then
        Assertions.assertEquals(10000, actual);
        Mockito.verify(personService, Mockito.atLeast(10000)).save(ArgumentMatchers.any(Person.class));
   }

    @Test
    void stax_xmlEventReader_should_upload_xml_file() throws Exception {

        // given
        InputStream inputStream = xml_file_with_10000_persons();

        // when
        int actual = personXmlFacade.savePersonsFromXmlEventReader(inputStream);

        // then
        Assertions.assertEquals(10000, actual);
        Mockito.verify(personService, Mockito.atLeast(10000)).save(ArgumentMatchers.any(Person.class));
    }

    private InputStream xml_file_with_10000_persons() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("/tmp/xml-benchmark/large-person-10000.xml"));
        Assertions.assertNotNull(inputStream);
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return "large-person-10000.xml";
            }

            @Override
            public String getContentType() {
                return "application/xml";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1530125L;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return IOUtils.toByteArray(inputStream);
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return inputStream;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {
                // not imaplemented/ not needed
            }
        };
        return inputStream;
    }
}