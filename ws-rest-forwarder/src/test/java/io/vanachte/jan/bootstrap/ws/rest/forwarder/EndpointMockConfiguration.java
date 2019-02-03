package io.vanachte.jan.bootstrap.ws.rest.forwarder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.mockito.Mockito.mock;

@Configuration
@Import(EndpointMock.class)
public class EndpointMockConfiguration {

    // Expose constant so this can be configured in the spring boot test
    public static final String PROPERTY_NAME_REQUEST_MAPPING = "com.zetes.caboverde.common.utils.rest.test.REQUEST_MAPPING";

    @Bean
    EndpointMock.Responder<HttpMethod, String, String, Map<String,String>, String, ResponseEntity<String>> responder() {

        return mock(EndpointMock.Responder.class);
    }

}
