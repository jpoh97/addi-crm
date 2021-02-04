package com.addi.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class Person {

    private final Integer nationalIdentificationNumber;
    private final Birthdate birthdate;
    private final String firstname;
    private final String lastname;
    private final Email email;

    public static Person create(Integer nationalIdentificationNumber, LocalDate birthdate, String firstname, String lastname, String email) {
        Email validEmail = Email.create(email);
        Birthdate validBirthdate = Birthdate.create(birthdate);
        return new Person(nationalIdentificationNumber, validBirthdate, firstname, lastname, validEmail);
    }

    private Person(Integer nationalIdentificationNumber, Birthdate birthdate, String firstname, String lastname, Email email) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.birthdate = birthdate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(nationalIdentificationNumber, person.nationalIdentificationNumber)
                && Objects.equals(birthdate, person.birthdate)
                && Objects.equals(firstname, person.firstname)
                && Objects.equals(lastname, person.lastname)
                && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalIdentificationNumber, birthdate, firstname, lastname, email);
    }
}
