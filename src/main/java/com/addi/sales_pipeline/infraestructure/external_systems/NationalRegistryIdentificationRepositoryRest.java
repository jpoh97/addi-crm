package com.addi.sales_pipeline.infraestructure.external_systems;

import com.addi.sales_pipeline.application.external_systems.NationalRegistryIdentificationRepository;
import com.addi.sales_pipeline.domain.exception.DomainException;
import com.addi.sales_pipeline.domain.model.Person;
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
final class NationalRegistryIdentificationRepositoryRest implements NationalRegistryIdentificationRepository {

    private static final String PERSON_SHOULD_EXIST = "The person should exist in the national registry identification external system";
    private final Vertx vertx;

    private WebClient webClient;

    @ConfigProperty(name = "national.registry.identification.host")
    private String hostname;

    @Inject
    NationalRegistryIdentificationRepositoryRest(Vertx vertx) {
        this.vertx = vertx;
    }

    @PostConstruct
    void initialize() {
        webClient = WebClient.create(vertx, new WebClientOptions().setDefaultHost(hostname));
    }

    @Override
    public Uni<Person> findByNationalIdentificationNumber(Long nationalIdentificationNumber) {
        return webClient.get("/api/person/" + nationalIdentificationNumber)
                .send().map(resp -> {
                    if (resp.statusCode() == Status.NOT_FOUND.getStatusCode()) {
                        throw new DomainException(PERSON_SHOULD_EXIST);
                    }
                    return resp.bodyAsJson(Person.class);
                });
    }
}
