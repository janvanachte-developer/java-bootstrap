package io.vanachte.jan.bootstrap.ws.rest.forwarder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Enumeration;

public abstract class AbstractForwardingEndpoint {

    @Value("${server.servlet.context-path}")
    private String servletContext;

    @Value("${target.endpoint.url}")
    private String targetUrl;

    @Value("${target.endpoint.context-path}")
    private String targetContext;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    private static final ObjectReader objectReader = new ObjectMapper().reader();
    private static final ObjectWriter objectWriter = new ObjectMapper().writer();

    private static final Logger LOG = LoggerFactory.getLogger(AbstractForwardingEndpoint.class);
    private static final String LOG_PATTERN_FORWARDING_TO = "{} {}{} forwarding to {}";
    private static final String LOG_PATTERN_RESPONDING_WITH = "{} {}{} responding with {}";

    @RequestMapping(
            path = {"", "**"},
            method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.TRACE, RequestMethod.OPTIONS})
    public ResponseEntity<String> forward(HttpServletRequest httpServletRequest, HttpMethod httpMethod) {
        LOG.info("{} {}{}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "");

        HttpHeaders httpHeaders = copyHeaders(httpServletRequest);
        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);

        String path = copyPath(httpServletRequest);
        URI uri = URI.create(targetUrl + targetContext + path);

        return respond(httpEntity, uri, httpServletRequest, httpMethod);
    }

    @RequestMapping(
            path = {"", "**"},
            method = {RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<String> forward(@RequestBody String content, HttpServletRequest httpServletRequest, HttpMethod httpMethod) {
        LOG.info("{} {}{}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "");
        LOG.debug("{} {}{} RequestBody={}", httpMethod, httpServletRequest.getRequestURL(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", content);

        String path = copyPath(httpServletRequest);
        URI uri = URI.create(targetUrl + targetContext + path);

        HttpHeaders httpHeaders = copyHeaders(httpServletRequest);
        HttpEntity<String> httpEntity = new HttpEntity(content, httpHeaders);

        return respond(httpEntity, uri, httpServletRequest, httpMethod);
    }

    private String copyPath(HttpServletRequest httpServletRequest) {

        String requestURI = httpServletRequest.getRequestURI();
        String requestPath = requestURI.substring(servletContext.length());

        String queryString = httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "";
        return requestPath + queryString;
    }

    private HttpHeaders copyHeaders(HttpServletRequest httpServletRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();

        Enumeration<String> requestHttpHeaders = httpServletRequest.getHeaderNames();
        while (requestHttpHeaders.hasMoreElements()) {
            String key = requestHttpHeaders.nextElement();
            String value = httpServletRequest.getHeader(key);
            httpHeaders.set(key, value);
        }

        return httpHeaders;
    }

    private ResponseEntity<String> respond(HttpEntity<String> httpEntity, URI uri, HttpServletRequest httpServletRequest, HttpMethod httpMethod) {
        LOG.info(LOG_PATTERN_FORWARDING_TO + " with headers {}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", uri, httpEntity.getHeaders());
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, httpEntity, String.class);
            LOG.info(LOG_PATTERN_RESPONDING_WITH, httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", response.getStatusCode());
            LOG.debug(LOG_PATTERN_RESPONDING_WITH +"\n{}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", response.getStatusCode(), response.getBody());
            return response;
        } catch (HttpClientErrorException e) {
            return respondFromClientException(e, uri, httpServletRequest, httpMethod);
        } catch (Exception e) {
            return respondFromTargetException(e, uri, httpServletRequest, httpMethod);
        }
    }

    private ResponseEntity<String> respondFromClientException(HttpClientErrorException e, URI uri, HttpServletRequest httpServletRequest, HttpMethod httpMethod) {
        LOG.info(LOG_PATTERN_FORWARDING_TO + " responded with {}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", uri, e.getStatusCode());
        LOG.info(LOG_PATTERN_FORWARDING_TO + " responded with {}\n{}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", uri, e.getStatusCode(), e.getResponseBodyAsString());

        HttpHeaders responseHttpHeaders = e.getResponseHeaders();
        String responseBody = getResponseBodyAsJson(e.getResponseBodyAsString());
        if ( responseHttpHeaders != null && responseHttpHeaders.containsKey(HttpHeaders.CONTENT_LENGTH)) {
            responseHttpHeaders.remove(HttpHeaders.CONTENT_LENGTH); // will be recalculated
        }
        ResponseEntity<String> response = new ResponseEntity<>(responseBody, responseHttpHeaders, e.getStatusCode());

        LOG.info(LOG_PATTERN_RESPONDING_WITH, httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", response.getStatusCode());
        LOG.info(LOG_PATTERN_RESPONDING_WITH + "\n{}", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", response.getStatusCode(), response.getBody());
        return response;
    }

    private ResponseEntity<String> respondFromTargetException(Exception e, URI uri, HttpServletRequest httpServletRequest, HttpMethod httpMethod) {
        LOG.error(String.format("%s %s%s forwarding to %s responded with an Exception", httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", uri), e);

        String responseBody = getResponseBodyAsJson(e.getMessage());

        ResponseEntity<String> response = new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);

        LOG.info(LOG_PATTERN_RESPONDING_WITH, httpMethod, httpServletRequest.getRequestURI(), httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "", response.getStatusCode());
        return response;
    }

    private String getResponseBodyAsJson(String responseBody) {
        if (responseBody == null || responseBody.length() < 1) {
            return responseBody;
        } else {
            try {
                objectReader.readTree(responseBody);
                return responseBody;
            } catch (Exception noJson) {
                JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
                ObjectNode jsonObject = jsonNodeFactory.objectNode();
                StringBuilder sb = new StringBuilder();
                new JsonStringEncoder().quoteAsString(responseBody, sb);
                jsonObject.put("error", sb.toString());

                String json;
                try {
                    json = objectWriter.writeValueAsString(jsonObject);
                } catch (JsonProcessingException e1) {
                    // give up
                    json = "";
                }
                return json;
            }
        }
    }

    @PostConstruct
    public void init() {
        restTemplate = restTemplateBuilder.build();

        LOG.info("Forwarding endpoint {} started.", this.getClass().getCanonicalName());
        LOG.info("Forwarding {} to {}{}", servletContext, targetUrl, targetContext);
    }
}


