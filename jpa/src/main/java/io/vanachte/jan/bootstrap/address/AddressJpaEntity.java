package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.person.PersonJpaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "ADDRESSES")
public class AddressJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS_ID")
    @SequenceGenerator(name = "SEQ_ADDRESS_ID")
    private long id;

    @EqualsAndHashCode.Exclude
    // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    // https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "address_id")
    private List<AddressLineJpaEntity> lines = new ArrayList<>(); // https://thoughts-on-java.org/association-mappings-bag-list-set/

    @Column(name = "country_id")
    private String country;

    @ManyToMany(mappedBy = "addresses")
    @EqualsAndHashCode.Exclude
    private Set<PersonJpaEntity> persons;

    public void add(AddressLineJpaEntity line) {
        if ( lines.contains(line)) {
            lines.remove(line);
        }
        lines.add(line);
    }

    public void add(PersonJpaEntity person) {
        if ( !persons.contains(person) ) {
            persons.add(person);
        }
        person.add(this);
    }

    public void remove(PersonJpaEntity person) {
        if ( persons.contains(person) ) {
            persons.remove(person);
        }
        person.remove(this);
    }

}
