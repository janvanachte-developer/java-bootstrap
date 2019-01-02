package io.vanachte.jan.bootstrap.address;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS_LINES")
@Getter
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

    // https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/
//    @ManyToOne(fetch = FetchType.LAZY)
    private long address_id;
}
