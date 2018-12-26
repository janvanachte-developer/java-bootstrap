package io.vanachte.jan.bootstrap.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {

    @NonNull
    String identifier;
    String firstName;
    @NonNull
    String lastName;
    @Builder.Default
    Status status = Status.INITIAL;

    public void setStatus(Status status) throws Exception {
        if ( this.status.canBeFollewedBy(status)) {
            this.status = status;
        } else {
            throw new Exception("Status " + this.status + " cannot be followed by status " + status);
        }
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonBuilder {

    }

    public enum Status {
        INITIAL,
        STARTED,
        FINISHED;

        public boolean canBeFollewedBy(Status nextStatus) {
            // a status cannot be followed by itself
            if ( this.equals(nextStatus))  {
                return false;
            }

            // every status can be followed by the last status (except for the last - but that is already handled)
            if ( FINISHED.equals(nextStatus)) {
                return true;
            }

            // first status can be folled by second
            if ( INITIAL.equals(this) && STARTED.equals(nextStatus)) {
                return true;
            }

            // all other transitions are illegal
            return false;
        }
    }
}
