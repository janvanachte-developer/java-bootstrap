package io.vanachte.jan.bootstrap.country;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@Named
@Slf4j
public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country spain = Country.builder().code("ES").name("Spain").capital("Madrid").currency(Currency.getInstance("EUR")).build();
        countries.put(spain.getName(), spain);

        Country poland = Country.builder().code("PL").name("Poland").capital("Warsaw").currency(Currency.getInstance("PLN")).build();
        countries.put(poland.getName(), poland);

//        Country uk = new Country();
//        uk.setName("United Kingdom");
//        uk.setCapital("London");
//        uk.setCurrency(Currency.GBP);
//        uk.setPopulation(63705000);
//
//        countries.put(uk.getName(), uk);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        log.debug(countries.get(name).toString());

        return countries.get(name);
    }
}
