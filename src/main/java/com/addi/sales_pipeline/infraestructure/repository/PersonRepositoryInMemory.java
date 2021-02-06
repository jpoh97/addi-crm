package com.addi.sales_pipeline.infraestructure.repository;

import com.addi.sales_pipeline.domain.exception.DomainException;
import com.addi.sales_pipeline.domain.model.Person;
import com.addi.sales_pipeline.domain.model.Stage;
import com.addi.sales_pipeline.domain.repository.PersonRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

@Singleton
public class PersonRepositoryInMemory implements PersonRepository {

    private static final String PERSON_DOES_NOT_EXIST = "Person doesn't exist";
    private final Set<Person> people;

    public PersonRepositoryInMemory() {
        this.people = Set.of(
                Person.create(42L, LocalDate.of(1997, Month.FEBRUARY, 9), "Juan Pablo", "Ospina Herrera", "jpoh97@gmail.com", Stage.LEAD),
                Person.create(123L, LocalDate.of(1990, Month.NOVEMBER, 30), "Magnus", "Carlsen", "magnus@outlook.com", Stage.PROSPECT),
                Person.create(1021L, LocalDate.of(1995, Month.DECEMBER, 20), "Feliks", "Zemdegs", "rubiks@yahoo.com", Stage.LEAD)
        );
    }

    @Override
    public Uni<Person> findByNationalIdentificationNumber(Long nationalIdentificationNumber) {
        return Multi.createFrom().iterable(people)
                .filter(person -> person.getNationalIdentificationNumber().equals(nationalIdentificationNumber))
                .toUni().onItem().ifNull().switchTo(() -> { throw new DomainException(PERSON_DOES_NOT_EXIST); });
    }

    @Override
    public Uni<Void> convertIntoProspect(Long nationalIdentificationNumber) {
        Person person = people.stream()
                .filter(personInDB -> personInDB.getNationalIdentificationNumber().equals(nationalIdentificationNumber))
                .findFirst()
                .orElseThrow(() -> new DomainException(PERSON_DOES_NOT_EXIST));
        person.promoteNextStage();
        return Uni.createFrom().voidItem();
    }
}
