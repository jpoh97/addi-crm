package com.addi.person.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Agregar contratista")
final class PersonTest {

    @Test
    @DisplayName("2 people with the same data must be equal")
    void testTwoPeopleEqualData() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().build();

        assertEquals(person1, person2);
    }

    @Test
    @DisplayName("2 people with the different data must be not equal")
    void testTwoPeopleNotEqualData() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withFirstname("Alexis").build();

        assertNotEquals(person1, person2);
    }

    @Test
    @DisplayName("Do not throw exception when email is valid")
    void testPersonWithValidEmail() {
        try {
            new PersonBuilder().build();
        } catch (DomainException exception) {
            fail();
        }
    }

    @Test
    @DisplayName("Throw exception when email has invalid format")
    void testPersonWithInvalidEmailFormat() {
        try {
            new PersonBuilder().withEmail("Invalid email").build();
            fail();
        } catch (DomainException exception) {
            assertEquals("Invalid email format", exception.getMessage());
        }
    }
}
