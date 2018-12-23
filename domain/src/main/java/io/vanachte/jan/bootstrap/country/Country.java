package io.vanachte.jan.bootstrap.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Currency;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Country {

    private String code;
    private String name;
    private String capital;
    private Currency currency;
}