package com.addi.sales_pipeline.infraestructure.rest_controller;

import com.addi.sales_pipeline.domain.model.Person;
import com.addi.sales_pipeline.domain.model.Stage;
import com.addi.sales_pipeline.infraestructure.external_systems.Score;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;
import java.time.Month;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@DisplayName("Person resource tests")
class PersonResourceTest {

    private static final int EXTERNAL_SERVICES_PORT = 80;

    private static final WireMockServer wireMockServer = new WireMockServer(options().bindAddress("localhost").port(EXTERNAL_SERVICES_PORT));
    private final ObjectMapper objectMapper;

    @Inject
    PersonResourceTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    static void setup() {
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    private String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void stubExternalServices(Person person, Score score) {
        wireMockServer.stubFor(get(urlPathMatching("/api/person/[0-9]+"))
                .willReturn(ok(objectToJson(person))));
        wireMockServer.stubFor(get(urlPathMatching("/api/person/judicialRecords/[0-9]+"))
                .willReturn(notFound()));
        wireMockServer.stubFor(get(urlPathMatching("/api/prospect/qualification/[0-9]+"))
                .willReturn(ok(objectToJson(score))));
    }

    private void stubExternalServices() {
        var person = Person.create(42L, LocalDate.of(1997, Month.FEBRUARY, 9), "Juan Pablo", "Ospina Herrera", "jpoh97@gmail.com");
        var score = new Score(70);

        stubExternalServices(person, score);
    }

    @Test
    @DisplayName("Convert lead into prospect successfully")
    void testConvertIntoProspectSuccessfully() {
        stubExternalServices();

        given()
                .when().patch("/person/lead/42")
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @DisplayName("Person doesn't exist in local database")
    void testPersonDoesNotExistInLocalDatabase() {
        stubExternalServices();

        given()
                .when().patch("/person/lead/7")
                .then()
                .statusCode(Status.BAD_REQUEST.getStatusCode())
                .body(is("Person doesn't exist"));
    }

    @Test
    @DisplayName("Person doesn't exist in national registry identification")
    void testPersonDoesNotExistInNationalRegistryIdentification() {
        stubExternalServices();

        wireMockServer.stubFor(get(urlPathMatching("/api/person/[0-9]+"))
                .willReturn(notFound()));

        given()
                .when().patch("/person/lead/42")
                .then()
                .statusCode(Status.BAD_REQUEST.getStatusCode())
                .body(is("The person should exist in the national registry identification external system"));
    }

    @Test
    @DisplayName("Person in national registry identification doesn't match with local database")
    void testPersonDoesNotMatchInNationalRegistryIdentification() {
        var person = Person.create(42L, LocalDate.of(1950, Month.DECEMBER, 9), "Magnus", "Ospina Herrera", "jpoh97@gmail.com");
        var score = new Score(70);

        stubExternalServices(person, score);

        given()
                .when().patch("/person/lead/42")
                .then()
                .statusCode(Status.BAD_REQUEST.getStatusCode())
                .body(is("Personal information not match with the information stored in our local database"));
    }

    @Test
    @DisplayName("Person has judicial records in national archives system")
    void testPersonHasJudicialRecordsInNationalArchivesSystem() {
        stubExternalServices();

        wireMockServer.stubFor(get(urlPathMatching("/api/person/judicialRecords/[0-9]+"))
                .willReturn(ok()));

        given()
                .when().patch("/person/lead/42")
                .then()
                .statusCode(Status.BAD_REQUEST.getStatusCode())
                .body(is("The person should not have any judicial records in the national archives"));
    }

    @Test
    @DisplayName("Internal prospect qualification should be greater than 60")
    void testInternalProspectQualificationShouldBeGreaterThan60() {
        var person = Person.create(42L, LocalDate.of(1997, Month.FEBRUARY, 9), "Juan Pablo", "Ospina Herrera", "jpoh97@gmail.com");
        var score = new Score(60);

        stubExternalServices(person, score);

        given()
                .when().patch("/person/lead/42")
                .then()
                .statusCode(Status.BAD_REQUEST.getStatusCode())
                .body(is("Lead can't be turned into prospect. The score should be greater than 60"));
    }

    @Test
    @DisplayName("Should convert into prospect when score greater than 60")
    void testShouldConvertIntoProspectWhenScoreGreaterThan60() {
        var person = Person.create(1021L, LocalDate.of(1995, Month.DECEMBER, 20), "Feliks", "Zemdegs", "rubiks@yahoo.com", Stage.LEAD);
        var score = new Score(61);

        stubExternalServices(person, score);

        given()
                .when().patch("/person/lead/1021")
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode());
    }
}