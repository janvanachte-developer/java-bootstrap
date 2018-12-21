package io.vanachte.jan.bootstrap.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {

    String identifier;
    String firstName;
    String lastName;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonBuilder {

    }
}
