package io.vanachte.jan.bootstrap.ws.rest.forwarder;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Stream;

import static io.vanachte.jan.bootstrap.ws.rest.forwarder.ForwardingEndpointSpringBootTest.TARGET_CONTEXT;
import static java.util.stream.Collectors.toMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {
        ForwardingEndpoint.class,

        EndpointMockConfiguration.class,
})
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        EndpointMockConfiguration.PROPERTY_NAME_REQUEST_MAPPING + "=" + TARGET_CONTEXT + "/**",
        "server.port=" + 63003,
        "target.endpoint.url=http://localhost:" + 63003,

        "server.servlet.context-path=",
        "target.endpoint.context-path=" + TARGET_CONTEXT,
        "spring.main.allow-bean-definition-overriding=true"
})
class ForwardingEndpointSpringBootTest {

    public static final String TARGET_CONTEXT = "/bus/rest";

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EndpointMock.Responder<HttpMethod, String, String, Map<String, String>, String, ResponseEntity<String>> busMock;

    @ParameterizedTest
    @CsvSource(delimiter = '#', value = {
            "GET#/batch-assessment#application/json##true", // -> no content
    })
    void configured_urls_should_invoke_filter_and_factory(String httpMethod, String path, String mediaType, String contentResource, boolean shouldBeInvoked) throws IOException {

        // given
        String requestUrl = "http://localhost:" + port + TARGET_CONTEXT + path;
        String queryString = null;
        Map<String, String> headers = Stream.of(new String[][]{ // copy of headers produced by RestTemplate
                {"host", "localhost:" + port},
                {"content-type", "application/json"},
                {"connection", "keep-alive"},
                {"accept", "text/plain, application/json, application/*+json, */*"},
                {"user-agent", "Java/" + System.getProperty("java.version")}
        }).collect(toMap(value -> value[0], value -> value[1]));
        HttpHeaders httpHeaders = new HttpHeaders();// all of the above headers will be added by RestTemplate
        httpHeaders.setContentType(MediaType.valueOf(mediaType));
//        for ( String headerName : new String[]{"AUDIT_TOUR_OPERATOR_ID", "AUDIT_USER_ID","USER_NAME","LAST_NAME","FIRST_NAME"} ){
//            httpHeaders.set(headerName, "somevalue");
//        }

        // given
        HttpEntity<String> httpEntity;
        String content = null;
        if (contentResource != null) {
            content = IOUtils.toString(new ClassPathResource(contentResource).getInputStream(), Charset.defaultCharset());
            httpEntity = new HttpEntity(content, httpHeaders);
        } else {
            httpEntity = new HttpEntity(httpHeaders);
        }

        // when
        ResponseEntity actual = restTemplate.exchange("http://localhost:" + port + path, HttpMethod.valueOf(httpMethod), httpEntity, String.class);

        // then
        verify(busMock, times(shouldBeInvoked ? 1 : 0)).apply(HttpMethod.valueOf(httpMethod), requestUrl, queryString, headers, content);
    }
}