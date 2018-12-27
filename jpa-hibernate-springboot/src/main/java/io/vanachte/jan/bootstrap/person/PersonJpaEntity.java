package io.vanachte.jan.bootstrap.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import org.hibernate.annotations.NaturalId;


@Entity
@Table(name = "PERSONS")
@Data
public class PersonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON_ID")
    @SequenceGenerator(name = "SEQ_PERSON_ID")
    long id;

//    @NaturalId
    String identifier;
    String firstName;
    String lastName;
}
