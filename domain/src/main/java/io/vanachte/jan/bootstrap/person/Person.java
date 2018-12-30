package io.vanachte.jan.bootstrap.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.vanachte.jan.bootstrap.address.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode
@JsonDeserialize(builder = Person.PersonBuilder.class)
public class Person {

    @NonNull
    String identifier;
    String firstName;
    @NonNull
    String lastName;
    @Builder.Default
    @NonFinal
    Status status = Status.STATUS_1;


    @Builder.Default
    @Getter(AccessLevel.NONE)
    List<Address> addresses = new ArrayList<>();

    public void setStatus(Status status) throws Exception {
        if ( this.status.canBeFollewedBy(status)) {
            this.status = status;
        } else {
            throw new Exception("Status " + this.status + " cannot be followed by status " + status);
        }
    }

    public void add(Address address) {
        addresses.add(address);
    }

    public List<Address> getAddresses() {
        return new ArrayList<>(addresses);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PersonBuilder {

    }

    public enum Status {
        STATUS_1,
        STATUS_2,
        STATUS_3;

        public boolean canBeFollewedBy(Status nextStatus) {
            // a status cannot be followed by itself
            if ( this.equals(nextStatus))  {
                return false;
            }

            // every status can be followed by the last status (except for the last - but that is already handled)
            if ( STATUS_3.equals(nextStatus)) {
                return true;
            }

            // first status can be folled by second
            if ( STATUS_1.equals(this) && STATUS_2.equals(nextStatus)) {
                return true;
            }

            // all other transitions are illegal
            return false;
        }
    }
}
