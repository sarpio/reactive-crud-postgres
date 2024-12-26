package com.sarpio.edu.r2dbcpostgres.rest;

import com.sarpio.edu.r2dbcpostgres.model.Person;
import com.sarpio.edu.r2dbcpostgres.repo.PersonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Order(1)
    @Test
    void shouldReturnPerson() {
        webTestClient.get()
                .uri("/api/persons/1")
                .exchange()
                .expectStatus().isOk().expectBody(Person.class);
    }

    @Order(2)
    @Test
    void shouldReturnStatusNotFound() {
        webTestClient.get()
                .uri("/api/persons/1000010")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Order(3)
    @Test
    void shouldReturnMultipleRecords() {
        webTestClient.get()
                .uri("/api/persons")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class).hasSize(10);
    }

    @Order(4)
    @Test
    void shouldReturnCreatedPerson() throws Exception {
        webTestClient.post()
                .uri("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\n" +
                        "  \"firstName\": \"Adam\",\n" +
                        "  \"lastName\": \"Ant\",\n" +
                        "  \"email\": \"adam@gmail.com\",\n" +
                        "  \"job\": \"Director\",\n" +
                        "  \"phone\": \"5432321881\"\n" +
                        "}"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class);
    }

    @Order(5)
    @Test
    void shouldDeletePerson() {
        Person createdPerson = webTestClient.post()
                .uri("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\n" +
                        "  \"firstName\": \"Adam\",\n" +
                        "  \"lastName\": \"Ant\",\n" +
                        "  \"email\": \"adam1@gmail.com\",\n" +
                        "  \"job\": \"Director\",\n" +
                        "  \"phone\": \"5432321881\"\n" +
                        "}"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class)
                .returnResult()
                .getResponseBody();

        Long id = Objects.requireNonNull(createdPerson).getId();
        if (id != 0) {
            webTestClient.delete()
                    .uri("/api/persons/" + id)
                    .exchange()
                    .expectStatus().isOk();
        } else {
            throw new RuntimeException("id for Person deletion test is ZERO!");
        }
    }
}