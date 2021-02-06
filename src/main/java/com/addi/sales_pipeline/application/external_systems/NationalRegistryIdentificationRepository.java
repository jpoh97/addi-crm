package com.addi.sales_pipeline.application.external_systems;

import com.addi.sales_pipeline.domain.model.Person;
import io.smallrye.mutiny.Uni;

public interface NationalRegistryIdentificationRepository {

    Uni<Person> findByNationalIdentificationNumber(Long nationalIdentificationNumber);

}
