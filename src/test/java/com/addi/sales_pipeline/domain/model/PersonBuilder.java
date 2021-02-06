package com.addi.sales_pipeline.domain.model;

import java.time.LocalDate;
import java.time.Month;

final class PersonBuilder {

    private final Long nationalIdentificationNumber;
    private LocalDate birthdate;
    private String firstname;
    private final String lastname;
    private String email;
    private Stage stage;

    PersonBuilder() {
        this.nationalIdentificationNumber = 42L;
        this.birthdate = LocalDate.of(1997, Month.FEBRUARY, 9);
        this.firstname = "Juan Pablo";
        this.lastname = "Ospina Herrera";
        this.email = "jpoh97@gmail.com";
        this.stage = Stage.LEAD;
    }

    PersonBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    PersonBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    PersonBuilder withBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    PersonBuilder withStage(Stage stage) {
        this.stage = stage;
        return this;
    }

    Person build() {
        return Person.create(nationalIdentificationNumber, birthdate, firstname, lastname, email, stage);
    }
}
