package com.addi.person.domain;

import java.time.LocalDate;
import java.time.Month;

final class PersonBuilder {

    private final Integer nationalIdentificationNumber;
    private final LocalDate birthdate;
    private String firstname;
    private final String lastname;
    private final String email;

    PersonBuilder() {
        this.nationalIdentificationNumber = 42;
        this.birthdate = LocalDate.of(1997, Month.FEBRUARY, 9);
        this.firstname = "Juan Pablo";
        this.lastname = "Ospina Herrera";
        this.email = "jpoh97@gmail.com";
    }

    PersonBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    Person build() {
        return Person.create(nationalIdentificationNumber, birthdate, firstname, lastname, email);
    }
}
