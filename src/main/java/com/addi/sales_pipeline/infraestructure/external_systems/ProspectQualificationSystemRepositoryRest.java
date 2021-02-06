package com.addi.sales_pipeline.infraestructure.external_systems;

import com.addi.sales_pipeline.application.external_systems.ProspectQualificationSystemRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ProspectQualificationSystemRepositoryRest implements ProspectQualificationSystemRepository {

    private final Vertx vertx;

    private WebClient webClient;

    @Inject
    ProspectQualificationSystemRepositoryRest(Vertx vertx) {
        this.vertx = vertx;
    }

    @PostConstruct
    void initialize() {
        webClient = WebClient.create(vertx, new WebClientOptions().setLogActivity(true));
    }

    @Override
    public Uni<Integer> findByNationalIdentificationNumber(Long nationalIdentificationNumber) {
        return webClient.get("/api/prospect/qualification/" + nationalIdentificationNumber).send()
                .map(resp -> resp.bodyAsJson(Score.class))
                .map(Score::getValue);
    }
}
