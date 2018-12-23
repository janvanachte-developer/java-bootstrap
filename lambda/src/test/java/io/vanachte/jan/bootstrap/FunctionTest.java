package io.vanachte.jan.bootstrap;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {

    @Test
    public void a_predicate1() {
        Predicate<String> pred = string -> string.length() > 4;

        assertTrue(pred.test("abcde"));
        assertFalse(pred.test("abcd"));
    }

    @Test
    public void a_predicate5() {
        Predicate<String> lengthIs9 = s -> s.length() == 9;
        Predicate<String> equalsError = "ERROR"::equals;
        // Note: this could also be: Predicate.isEqual("ERROR")

        Predicate<String> lengthIs9orError = string -> lengthIs9.test(string)||equalsError.test(string);

        assertFalse(lengthIs9orError.test("Hello"));
        assertTrue(lengthIs9orError.test("Hello J1!"));
        assertTrue(lengthIs9orError.test("ERROR"));
        assertFalse(lengthIs9orError.test("Error"));
    }

    @Test
    public void b_function4() {
        Function<String, String> unNullify = s -> s == null ? "" : s;
        Function<String, Integer> length = String::length;

        Function<String, Integer> lengthBis = s -> length.apply(unNullify.apply(s)); // TODO

        assertEquals((Integer)14, lengthBis.apply("Hello JavaOne!"));
        assertEquals((Integer)0, lengthBis.apply(""));
        assertEquals((Integer)0, lengthBis.apply(null));
    }

}
