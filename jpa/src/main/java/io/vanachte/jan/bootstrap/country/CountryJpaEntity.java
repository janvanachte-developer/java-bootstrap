package io.vanachte.jan.bootstrap.country;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@Table(name="COUNTRIES")
@EqualsAndHashCode
public class CountryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUNTRY_ID")
    @SequenceGenerator(name = "SEQ_COUNTRY_ID")
    private long id;

    private String identifier;
}
