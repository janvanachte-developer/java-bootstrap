package io.vanachte.jan.bootstrap.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonMapperImplUnitTest {

    PersonMapper personMapper = new PersonMapperImpl();

    @Test
    public void personType_should_be_mapped_to_person() {

        // given
        PersonType personType = new PersonType();
        personType.setIdentifier("ID");
        personType.setFirstName("FIRST");
        personType.setLastName("LAST");

        // when
        Person actual = personMapper.map(personType, Person.class);

        // then
        assertTrue(actual instanceof Person);
        assertEquals("ID", actual.getIdentifier());
        assertEquals("FIRST",actual.getFirstName());
        assertEquals("LAST", actual.getLastName());
        assertEquals(Person.Status.INITIAL, actual.getStatus());

    }


}