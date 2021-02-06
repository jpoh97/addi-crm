package com.addi.sales_pipeline.infraestructure.external_systems;

import com.addi.sales_pipeline.application.external_systems.NationalArchivesSystemRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response.Status;

@Singleton
final class NationalArchivesSystemRepositoryRest implements NationalArchivesSystemRepository {

    private final Vertx vertx;

    private WebClient webClient;

    @ConfigProperty(name = "national.archives.system.host")
    private String hostname;

    @Inject
    NationalArchivesSystemRepositoryRest(Vertx vertx) {
        this.vertx = vertx;
    }

    @PostConstruct
    void initialize() {
        webClient = WebClient.create(vertx, new WebClientOptions().setDefaultHost(hostname));
    }

    @Override
    public Uni<Boolean> hasJudicialRecords(Long nationalIdentificationNumber) {
        return webClient.get("/api/person/judicialRecords/" + nationalIdentificationNumber)
                .send().map(resp -> resp.statusCode() != Status.NOT_FOUND.getStatusCode());
    }
}
