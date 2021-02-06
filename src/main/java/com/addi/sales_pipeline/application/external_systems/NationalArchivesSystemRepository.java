package com.addi.sales_pipeline.application.external_systems;

import io.smallrye.mutiny.Uni;

public interface NationalArchivesSystemRepository {

    Uni<Boolean> hasJudicialRecords(Long nationalIdentificationNumber);

}
