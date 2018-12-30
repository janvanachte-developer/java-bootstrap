package io.vanachte.jan.bootstrap.jpa;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
public class AuditColumns {

    private LocalDateTime created;
    private LocalDate keepUntil;
}
