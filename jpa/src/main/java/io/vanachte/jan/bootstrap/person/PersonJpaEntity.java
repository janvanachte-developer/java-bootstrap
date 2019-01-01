package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.address.AddressJpaEntity;
import io.vanachte.jan.bootstrap.jpa.Audit;
import io.vanachte.jan.bootstrap.jpa.AuditEntityListener;
import io.vanachte.jan.bootstrap.jpa.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

//import org.hibernate.annotations.NaturalId;


@Entity
@Table(name = "PERSONS")
@EntityListeners(AuditEntityListener.class)
@Data
public class PersonJpaEntity implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON_ID")
    @SequenceGenerator(name = "SEQ_PERSON_ID", sequenceName = "SEQ_PERSON_ID", allocationSize = 1)  // 'allocationSize' should be same a 'INCREMENT BY'
    // negative values: https://stackoverflow.com/questions/9861416/hibernate-generates-negative-id-values-when-using-a-sequence
    private long id;

//    @NaturalId
    private String identifier;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Person.Status status = Person.Status.STATUS_1;

//    @Convert(converter = BooleanToVarcharAttributeConverter.class)
    private Boolean active;

    @Embedded
    @EqualsAndHashCode.Exclude
    private Audit audit;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "person_address",
            joinColumns = @JoinColumn(name = "person_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "address_id",referencedColumnName="id")
    )
    @EqualsAndHashCode.Exclude
    private Set<AddressJpaEntity> addresses;

    public void add(AddressJpaEntity address) {
        if( !addresses.contains(address) ) {
            addresses.add(address);
        }
        address.add(this);
    }

    public void remove(AddressJpaEntity address) {
        if ( addresses.contains(address) ) {
            addresses.remove(address);
        }
        address.remove(this);
    }
}
