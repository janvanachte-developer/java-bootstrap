package io.vanachte.jan.bootstrap.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class Audit {

    private LocalDateTime createdOn;
    private LocalDate keepUntil;
}
