package io.vanachte.jan.bootstrap.person;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static io.vanachte.jan.bootstrap.person.Person.Status.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testing Person")
public class PersonUnitTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("beforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("beforeEach");
    }

    @Nested
    @DisplayName("Unit tests for Person identifier")
    public class IdentifierUnitTest {

        @Test
        @DisplayName("A Person has an identifier")
        public void a_person_has_an_identifier() {

            // given
            String identifier = "Foo";

            // when
            Person person = Person.builder().identifier(identifier).lastName("Foo").build();

            // then
            assertEquals(identifier, person.getIdentifier());
        }

        @Test
        @DisplayName("A Person should have an identifier")
        public void a_person_should_have_an_identifier() {

            // given
            // when
            // then
            assertThrows(NullPointerException.class, () -> Person.builder().lastName("Foo").build());
        }

    }

    @Nested
    @DisplayName("Unit tests for Person name")
    public class NameUnitTest {

        @ParameterizedTest(name = "A Person can have a last name {arguments}")
        @DisplayName("A Person has a last name")
        @ValueSource(strings = {"Foo", "Person"})
        public void a_person_has_a_name(String name) {

            // given

            // when
            Person person = Person.builder().identifier("Foo").lastName(name).build();

            // then
            assertEquals(name, person.getLastName());
        }

        @Test
        @DisplayName("A Person should have a last name")
        public void a_person_should_have_a_name() {

            // given
            // when
            // then
            assertThrows(NullPointerException.class, () -> Person.builder().identifier("Foo").build());
        }

    }

    @AfterEach
    public void afterEach() {
        System.out.println("afterEach");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("afterAll");
    }

    @Nested
    @DisplayName("Unit tests for Person status")
    public class StatusUnitTest {

        @ParameterizedTest(name = "A Person can have a status {arguments}")
        @DisplayName("A Person has a status")
        @EnumSource(Person.Status.class)
        public void a_person_has_a_status(Person.Status status) {

            // given
            // when
            Person person = Person.builder().identifier("Foo").lastName("Foo").status(status).build();

            // then
            assertEquals(status, person.getStatus());
        }

        @Test
        @DisplayName("A Person should have a status. If no status is given is should default to INITIAL")
        public void a_person_should_have_a_status() {

            // given
            Person.Status defaultStatus = INITIAL;

            // when
            Person person = Person.builder().identifier("Foo").lastName("Foo").build();

            // then
            assertEquals(defaultStatus, person.getStatus());
        }

        @ParameterizedTest(name = "Person.Status {0} can {2} be followed by Person.Status {1}.")
        @ArgumentsSource(StatusUnitTestArgumentsSource.class)
        @DisplayName("A Person.Status can only followed by specific set of Person.Status")
        public void a_person_status_can_only_be_followed_by_some_person_status(Person.Status firstStatus, Person.Status nextStatus, boolean expected) {

            // given

            // when
            boolean actual = firstStatus.canBeFollewedBy(nextStatus);

            // then
            assertEquals(expected,actual);
        }

    }

    @Nested
    @DisplayName("Unit test for Person status transitions")
    public class StatusTransitionUnitTest {

        @ParameterizedTest(name = "Person.Status {0} can {2} be followed by Person.Status {1}.")
        @ArgumentsSource(StatusUnitTestArgumentsSource.class)
        public void accepted_transitions(Person.Status firstStatus, Person.Status nextStatus, boolean can) throws Exception {

            // given
            Person person = Person.builder().identifier("Foo").lastName("name").status(firstStatus).build();

            if ( can ) {
                // when
                person.setStatus(nextStatus);
                // then
                assertEquals(nextStatus, person.getStatus());
            } else {
                // when
                // then
                assertThrows(Exception.class, () -> person.setStatus(nextStatus));
            }

        }
    }

    private static class StatusUnitTestArgumentsSource implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(INITIAL, INITIAL, FALSE),
                    Arguments.of(INITIAL, STARTED, TRUE),
                    Arguments.of(INITIAL, FINISHED, TRUE),
                    Arguments.of(STARTED, INITIAL, FALSE),
                    Arguments.of(STARTED, STARTED, FALSE),
                    Arguments.of(STARTED, FINISHED, TRUE),
                    Arguments.of(FINISHED, INITIAL, FALSE),
                    Arguments.of(FINISHED, STARTED, FALSE),
                    Arguments.of(FINISHED, FINISHED, FALSE)
            );
        }
    }
}
