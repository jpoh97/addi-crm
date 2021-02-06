package com.addi.sales_pipeline.application;

import com.addi.sales_pipeline.application.external_systems.NationalArchivesSystemRepository;
import com.addi.sales_pipeline.application.external_systems.NationalRegistryIdentificationRepository;
import com.addi.sales_pipeline.application.external_systems.ProspectQualificationSystemRepository;
import com.addi.sales_pipeline.domain.exception.DomainException;
import com.addi.sales_pipeline.domain.model.Person;
import com.addi.sales_pipeline.domain.repository.PersonRepository;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ConvertLeadIntoProspect {

    private static final String PERSONAL_INFORMATION_NOT_MATCH = "Personal information not match with the information stored in our local database";
    private static final String PERSON_SHOULD_NOT_HAVE_ANY_JUDICIAL_RECORDS = "The person should not have any judicial records in the national archives";
    private static final String SCORE_SHOULD_BE_GREATER_THAN_60 = "Lead can't be turned into prospect. The score should be greater than 60";

    private final PersonRepository personRepository;
    private final NationalRegistryIdentificationRepository nationalRegistryIdentificationRepository;
    private final NationalArchivesSystemRepository nationalArchivesSystemRepository;
    private final ProspectQualificationSystemRepository prospectQualificationSystemRepository;

    @Inject
    public ConvertLeadIntoProspect(PersonRepository personRepository,
                                   NationalRegistryIdentificationRepository nationalRegistryIdentificationRepository,
                                   NationalArchivesSystemRepository nationalArchivesSystemRepository,
                                   ProspectQualificationSystemRepository prospectQualificationSystemRepository) {
        this.personRepository = personRepository;
        this.nationalRegistryIdentificationRepository = nationalRegistryIdentificationRepository;
        this.nationalArchivesSystemRepository = nationalArchivesSystemRepository;
        this.prospectQualificationSystemRepository = prospectQualificationSystemRepository;
    }

    public Uni<Void> execute(Long nationalIdentificationNumber) {
        return Uni.combine().all().unis(
                personRepository.findByNationalIdentificationNumber(nationalIdentificationNumber),
                nationalRegistryIdentificationRepository.findByNationalIdentificationNumber(nationalIdentificationNumber),
                nationalArchivesSystemRepository.hasJudicialRecords(nationalIdentificationNumber)
        ).asTuple()
                .map(requestResults -> validateExternalSystems(requestResults.getItem1(), requestResults.getItem2(), requestResults.getItem3()))
                .chain(person -> prospectQualificationSystemRepository.findByNationalIdentificationNumber(nationalIdentificationNumber))
                .map(this::validateInternalProspectQualification)
                .chain(score -> personRepository.convertIntoProspect(nationalIdentificationNumber));
    }

    private Integer validateInternalProspectQualification(Integer score) {
        if (score <= 60) {
            throw new DomainException(SCORE_SHOULD_BE_GREATER_THAN_60);
        }
        return score;
    }

    private Person validateExternalSystems(Person personFromLocalDatabase, Person personFromNationalRegistryIdentification, boolean hasJudicialRecords) {
        if (!personFromLocalDatabase.equals(personFromNationalRegistryIdentification)) {
            throw new DomainException(PERSONAL_INFORMATION_NOT_MATCH);
        }
        if (hasJudicialRecords) {
            throw new DomainException(PERSON_SHOULD_NOT_HAVE_ANY_JUDICIAL_RECORDS);
        }
        return personFromLocalDatabase;
    }


}
