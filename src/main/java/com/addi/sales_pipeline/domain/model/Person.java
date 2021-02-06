package com.addi.sales_pipeline.domain.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;
import java.util.Objects;

@RegisterForReflection
public final class Person {

    private Long nationalIdentificationNumber;
    private Birthdate birthdate;
    private String firstname;
    private String lastname;
    private Email email;
    private Stage pipelineStage;

    public static Person create(Long nationalIdentificationNumber, LocalDate birthdate, String firstname,
                                String lastname, String email, Stage stage) {
        Email validEmail = new Email(email);
        Birthdate validBirthdate = new Birthdate(birthdate);
        return new Person(nationalIdentificationNumber, validBirthdate, firstname, lastname, validEmail, stage);
    }

    public static Person create(Long nationalIdentificationNumber, LocalDate birthdate, String firstname,
                                String lastname, String email) {
        return create(nationalIdentificationNumber, birthdate, firstname, lastname, email, Stage.LEAD);
    }

    private Person(Long nationalIdentificationNumber, Birthdate birthdate, String firstname, String lastname,
                   Email email, Stage pipelineStage) {
        this.nationalIdentificationNumber = nationalIdentificationNumber;
        this.birthdate = birthdate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pipelineStage = pipelineStage;
    }

    public Person promoteNextStage() {
        this.pipelineStage = pipelineStage.nextStage();
        return this;
    }

    Person() { }

    public Long getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    public Birthdate getBirthdate() {
        return birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Email getEmail() {
        return email;
    }

    public Stage getPipelineStage() {
        return pipelineStage;
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
