package io.vanachte.jan.bootstrap.address;

import lombok.Getter;

@Getter
public class AddressLine {

    private final String line;

    public AddressLine(String line) {
        this.line = line;
    }
}
