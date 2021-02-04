package com.addi.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Agregar contratista")
final class PersonTest {

    @Test
    @DisplayName("2 people with the same data must be equal")
    void testTwoPeopleAreTheSame() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().build();

        assertEquals(person1, person2);
    }
}
