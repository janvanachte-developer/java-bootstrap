package io.vanachte.jan.bootstrap.ws.rest.forwarder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static io.vanachte.jan.bootstrap.ws.rest.forwarder.EndpointMockConfiguration.PROPERTY_NAME_REQUEST_MAPPING;

@RestController
@RequestMapping("${" + PROPERTY_NAME_REQUEST_MAPPING + "}")
public class EndpointMock {

    private static final Logger LOG = LoggerFactory.getLogger(EndpointMock.class);

    @Autowired
    Responder<HttpMethod, String, String, Map, String, ResponseEntity> responder;

    @RequestMapping(
            path={"","**"}, // match everything
            method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS}
    )
    public ResponseEntity<String> respond(HttpServletRequest httpServletRequest) {
        return respond(httpServletRequest, null);
    }

    @RequestMapping(
            path={"","**"}, // match everything
            method = {RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT}
    )
    public ResponseEntity<String> respond(@RequestBody String content, HttpServletRequest httpServletRequest) {
        return respond(httpServletRequest, content);
    }

    private ResponseEntity<String> respond(HttpServletRequest httpServletRequest, String content) {
        String method = httpServletRequest.getMethod();
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();

        LOG.info("{} {}{} {}", method, requestUrl, queryString!=null?"?"+queryString:"",content!=null?"RequestBody="+content:"");

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while ( headerNames.hasMoreElements() ) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            headers.put(key, value);
        }

        return responder.apply(HttpMethod.valueOf(method), requestUrl, queryString, headers, content);
    }

    @FunctionalInterface
    public interface Responder<T, U, V, W, X, R> {

        R apply(T t, U u, V v, W w, X x);
    }
}
