package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Address {

    @NonNull
    Country country;
}
