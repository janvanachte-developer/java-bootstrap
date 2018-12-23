package io.vanachte.jan.bootstrap.country;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.inject.Inject;

@Endpoint
@Slf4j
public class CountryEndpoint {

    private static final String NAMESPACE_URI = "http://bootstrap.jan.vanachte.io/country";

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Inject
    public CountryEndpoint(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        log.debug("country=" + request.getName());
        response.setCountry(modelMapper.map(countryRepository.findCountry(request.getName()),CountryType.class));

        return response;
    }
}


