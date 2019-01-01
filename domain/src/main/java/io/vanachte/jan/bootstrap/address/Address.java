package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Address {

    private List<AddressLine> lines;

    @NonNull
    private Country country;
}
