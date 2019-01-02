package io.vanachte.jan.bootstrap.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class AddressLine {

    @Setter
    private String line;

    public AddressLine(String line) {
        this.line = line;
    }
}
