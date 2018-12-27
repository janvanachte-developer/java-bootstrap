package io.vanachte.jan.bootstrap.person;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRestControllerUnitTest {

    @InjectMocks
    PersonRestController personRestController;

    @Mock
    PersonService personService;

    @Spy
    PersonMapper personMapper = new PersonMapperImpl();

    @Test
    void uploadFile() throws Exception {

        // given
        InputStream inputStream = Files.newInputStream(Paths.get("/tmp/xml-benchmark/large-person-10000.xml"));
        assertNotNull(inputStream);
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

        // when
        ResponseEntity<String> actual = personRestController.uploadFile(file);

        // then
        assertNotNull(actual);
        verify(personService,atLeast(10000)).save(any(Person.class));
   }
}