package io.vanachte.jan.bootstrap.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS_LINES")
@Getter
@EqualsAndHashCode(callSuper = true) // otherwise line is not included and as id at creation=0 all lines are equal
public class AddressLineJpaEntity extends AddressLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS_LINE_ID")
    @SequenceGenerator(name = "SEQ_ADDRESS_LINE_ID", allocationSize = 1)
    private long id;

    @Override
    @Access(AccessType.PROPERTY)
    @Column(name="LINE")
    public String getLine() {
        return super.getLine();
    }

    public AddressLineJpaEntity(String line) {
        super(line);
    }

    public AddressLineJpaEntity() {
        super("");
    }

    @EqualsAndHashCode.Exclude
    // https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/
//    private long address_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressJpaEntity address;
}
