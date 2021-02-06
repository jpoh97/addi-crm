package com.addi.sales_pipeline.application.external_systems;

import io.smallrye.mutiny.Uni;

public interface ProspectQualificationSystemRepository {

    Uni<Integer> findByNationalIdentificationNumber(Long nationalIdentificationNumber);

}
