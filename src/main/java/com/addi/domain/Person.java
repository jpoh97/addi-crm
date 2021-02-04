package com.addi.domain;

import java.time.LocalDate;

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
}
