package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.CountryJpaEntity;
import io.vanachte.jan.bootstrap.person.PersonJpaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "ADDRESSES")
public class AddressJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS_ID")
    @SequenceGenerator(name = "SEQ_ADDRESS_ID")
    private long id;

//    @OneToMany(fetch = FetchType.LAZY)
//    private CountryJpaEntity country;

    @ManyToMany(mappedBy = "addresses")
    @EqualsAndHashCode.Exclude
    private Set<PersonJpaEntity> persons;
}
