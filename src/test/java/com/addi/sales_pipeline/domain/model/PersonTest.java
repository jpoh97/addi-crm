package com.addi.sales_pipeline.domain.model;

import com.addi.sales_pipeline.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Person domain tests")
final class PersonTest {

    @Test
    @DisplayName("2 people with the same data must be equal")
    void testTwoPeopleEqualData() {
        var person1 = new PersonBuilder().build();
        var person2 = new PersonBuilder().build();

        assertEquals(person1, person2);
    }

    @Test
    @DisplayName("2 people with the different data must be not equal")
    void testTwoPeopleNotEqualData() {
        var person1 = new PersonBuilder().build();
        var person2 = new PersonBuilder().withFirstname("Alexis").build();

        assertNotEquals(person1, person2);
    }

    @Test
    @DisplayName("Throw an exception when email has invalid format")
    void testPersonWithInvalidEmailFormat() {
        DomainException invalidEmailFormat = assertThrows(
                DomainException.class,
                () -> new PersonBuilder().withEmail("Invalid email").build()
        );

        assertEquals("Invalid email format", invalidEmailFormat.getMessage());
    }

    @Test
    @DisplayName("Throw an exception when birthdate is future")
    void testPersonWithBirthdateInTheFuture() {
        final var tomorrow = LocalDate.now().plusDays(1);

        DomainException birthdateInTheFuture = assertThrows(
                DomainException.class,
                () -> new PersonBuilder().withBirthdate(tomorrow).build()
        );

        assertEquals("Birthdate cannot be in the future", birthdateInTheFuture.getMessage());
    }

    @Test
    @DisplayName("Throw an exception when birthdate is max age")
    void testPersonWithBirthdateMaxAge() {
        final var twoHundredYearsAgo = LocalDate.now().minusYears(200);

        DomainException birthdateInTheFuture = assertThrows(
                DomainException.class,
                () -> new PersonBuilder().withBirthdate(twoHundredYearsAgo).build()
        );

        assertEquals("Birthdate cannot be 150 years ago", birthdateInTheFuture.getMessage());
    }
}
