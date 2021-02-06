package com.addi.sales_pipeline.infraestructure.external_systems;

import com.addi.sales_pipeline.domain.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@ApplicationScoped
class WireMockStubs {

    private static final int MAX_MOCK_LATENCY = 5000;
    private static final int MIN_MOCK_LATENCY = 2000;

    @ConfigProperty(name = "external.services.port")
    private Integer externalServicesPort;

    private WireMockServer wireMockServer;
    private final ObjectMapper objectMapper;

    @Inject
    WireMockStubs(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    void onStart(@Observes StartupEvent ev) {
        wireMockServer = new WireMockServer(options().port(externalServicesPort).notifier(new Slf4jNotifier(true)));
        wireMockServer.start();

        wireMockServer.stubFor(get(urlPathMatching("/api/person/[0-9]+"))
                .willReturn(ok(getMockPersonJson()).withUniformRandomDelay(MIN_MOCK_LATENCY, MAX_MOCK_LATENCY)));
        wireMockServer.stubFor(get(urlPathMatching("/api/person/judicialRecords/[0-9]+"))
                .willReturn(notFound().withUniformRandomDelay(MIN_MOCK_LATENCY, MAX_MOCK_LATENCY)));
        wireMockServer.stubFor(get(urlPathMatching("/api/prospect/qualification/[0-9]+"))
                .willReturn(ok(getMockScoreJson()).withUniformRandomDelay(MIN_MOCK_LATENCY, MAX_MOCK_LATENCY)));

    }

    void onStop(@Observes ShutdownEvent ev) {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }

    private String getMockPersonJson() {
        return objectToJson(Person.create(42L, LocalDate.of(1997, Month.FEBRUARY, 9), "Juan Pablo", "Ospina Herrera", "jpoh97@gmail.com"));
    }

    private String getMockScoreJson() {
        return objectToJson(new Score(70));
    }

    private String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
