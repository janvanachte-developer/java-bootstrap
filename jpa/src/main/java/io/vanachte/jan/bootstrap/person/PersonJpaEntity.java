package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.address.AddressJpaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

//import org.hibernate.annotations.NaturalId;


@Entity
@Table(name = "PERSONS")
@Data
public class PersonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON_ID")
    @SequenceGenerator(name = "SEQ_PERSON_ID")
    private long id;

//    @NaturalId
    private String identifier;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Person.Status status = Person.Status.STATUS_1;

//    @Convert(converter = BooleanToVarcharAttributeConverter.class)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "person_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "address_id",referencedColumnName="id")
    )
    @EqualsAndHashCode.Exclude
    private Set<AddressJpaEntity> addresses;
}
