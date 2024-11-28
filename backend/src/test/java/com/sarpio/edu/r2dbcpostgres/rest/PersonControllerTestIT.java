package com.sarpio.edu.r2dbcpostgres.rest;

import com.sarpio.edu.r2dbcpostgres.model.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTestIT {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void shouldReturnPerson() {
		webTestClient.get()
			.uri("/api/persons/1")
			.exchange()
			.expectStatus().isOk().expectBody(Person.class);
	}

	@Test
	void shouldReturnStatusNotFound() {
		webTestClient.get()
			.uri("/api/persons/1000010")
			.exchange()
			.expectStatus().isNotFound()
			.expectBody().isEmpty();
	}

	@Test
	void shouldReturnMultipleRecords() {
		webTestClient.get()
			.uri("/api/persons")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Person.class).hasSize(10);
	}

	@Test
	void shouldReturnCreatedPerson() throws Exception {
		webTestClient.post()
			.uri("/api/persons")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue("{\n"
				+ "    \"fistName\":\"Christian\", \n"
				+ "    \"lastName\":\"Andersen\", \n"
				+ "    \"email\":\"nancey.welch@gmail.com\",\n"
				+ "    \"job\":\"Coordinator\"\n"
				+ "}"))
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Person.class);
	}

	@Test
	void shouldDeletePerson() {
		Person createdPerson = webTestClient.post()
			.uri("/api/persons")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue("{\n"
				+ "    \"fistName\":\"Christian\", \n"
				+ "    \"lastName\":\"Andersen\", \n"
				+ "    \"email\":\"nancey.welch@gmail.com\",\n"
				+ "    \"job\":\"Coordinator\"\n"
				+ "}"))
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Person.class)
			.returnResult()
			.getResponseBody();

		long id = createdPerson.getId();
		if (id!=0){
			webTestClient.delete()
				.uri("/api/persons/" + id)
				.exchange()
				.expectStatus().isOk();
		} else {
			throw new RuntimeException("id for Person deletion test is ZERO!");
		}
	}
}