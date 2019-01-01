package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;

import java.util.List;

public interface AddressService {

    Address create(Country country, String... lines);
    List<Address> findAll();
}
