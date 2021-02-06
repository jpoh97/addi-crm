package com.addi.sales_pipeline.domain.repository;

import com.addi.sales_pipeline.domain.model.Person;
import io.smallrye.mutiny.Uni;

public interface PersonRepository {

    Uni<Person> findByNationalIdentificationNumber(Long nationalIdentificationNumber);

    Uni<Void> convertIntoProspect(Long nationalIdentificationNumber);
}
