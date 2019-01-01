package io.vanachte.jan.bootstrap.address;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS_LINES")
@Getter
public class AddressLineJpaEntity extends AddressLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS_LINE_ID")
    @SequenceGenerator(name = "SEQ_ADDRESS_LINE_ID")
    private long id;

    public AddressLineJpaEntity(String line) {
        super(line);
    }

    public AddressLineJpaEntity() {
        super("");
    }

    // https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/
//    @ManyToOne(fetch = FetchType.LAZY)
//    private long address_id;
}
