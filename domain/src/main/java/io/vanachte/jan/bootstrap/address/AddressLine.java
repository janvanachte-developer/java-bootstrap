package io.vanachte.jan.bootstrap.address;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddressLine {

    @Setter
    private String line;

    public AddressLine(String line) {
        this.line = line;
    }
}
