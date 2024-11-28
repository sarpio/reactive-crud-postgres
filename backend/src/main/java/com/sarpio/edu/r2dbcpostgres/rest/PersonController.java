package com.sarpio.edu.r2dbcpostgres.rest;

import com.github.javafaker.Faker;
import com.sarpio.edu.r2dbcpostgres.model.PersonDTO;
import com.sarpio.edu.r2dbcpostgres.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
@Tag(name = "Sample Person Controller", description = "Person Controller Endpoints")
public class PersonController {

    private final PersonService personService;

    @Operation(
            summary = "Get list of all persons",
            description = "Get sorted and pageable list of `all persons`"
    )
    @GetMapping
    public Flux<PersonDTO> getAllPersons(

            @RequestParam(defaultValue = "1") @Parameter(description = "Number of selected page starts from 1") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Number of records on single page") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sorting by Column") String sortBy,
            @RequestParam(defaultValue = "asc") @Parameter(description = "Sorting order ASC or DESC") String ascending,
            ServerHttpResponse response
    ) {
        return personService.getDbSize()
                .doOnNext(totalCount -> {
                    response.getHeaders()
                            .add("X-Total-Count", String.valueOf(totalCount));
                })
                .flatMapMany(dbSize -> personService.getAllPersons(page, size, sortBy, ascending));
    }

    @Operation(
            summary = "Get single person",
            description = "Get single person by `ID`"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Mono<PersonDTO>> getPersonById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonById(id));
    }

    @Operation(
            summary = "Find person by email",
            description = "Find person by `email` address with its validation"
    )
    @GetMapping("/email")
    public ResponseEntity<Flux<PersonDTO>> getPersonByEmail(
            @RequestParam("email")
            @Email(message = "Wrong email format")
            @NotBlank(message = "Email address is blank")
            @NotEmpty(message = "Email address is empty")
            String email) {
        return ResponseEntity.status(HttpStatus.FOUND).body(personService.getPersonByEmail(email));
    }

    @Operation(
            summary = "Add person to DB",
            description = "`Create` new person"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PersonDTO> createPerson(@RequestBody @Valid PersonDTO dto) {
        return personService.createPerson(dto);
    }

    @Operation(
            summary = "Add multiple persons to DB",
            description = "`Insert many` records in one query"
    )
    @PostMapping("/many")
    public void createPersons(@RequestBody @Valid List<PersonDTO> dtos) {
        personService.createPersons(dtos);
    }


    @Operation(
            summary = "Update person data",
            description = "`Update` person data base od person `id`"
    )
    @PutMapping("/{id}")
    public Mono<ResponseEntity<PersonDTO>> updatePerson(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid PersonDTO person) {
        return personService.updatePerson(id, person)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete operation",
            description = "`Delete` person by Person `Id`"
    )
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deletePerson(@PathVariable @NotNull Long id) {
        return personService.deletePerson(id).map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Initialization",
            description = "`Initialize` default data in DB with given number of rows"
    )
    @GetMapping("/load/{rows}")
    public String loadDataIntoDatabase(@PathVariable int rows) {
        if (rows <= 0) rows = 100;
        Faker faker = new Faker();
        List<PersonDTO> dtos = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            PersonDTO person = new PersonDTO();
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setEmail(faker.internet().emailAddress());
            person.setJob(faker.job().position());
            person.setPhone(faker.phoneNumber().cellPhone());
            dtos.add(person);
            if (i % 100000 == 0 && i != 0)
                log.info("Generated {} persons", i);
        }
        long start = System.currentTimeMillis();
        personService.createPersons(dtos);
        long end = System.currentTimeMillis();
        log.info("Executed in {}ms", end - start);
        return "Executed in " + (end - start) + "ms";
    }

    @Operation(
            summary = "DB Size",
            description = "Returns number of records base on count() function"
    )
    @GetMapping("/size")
    public Mono<Long> getDbSize() {
        return personService.getDbSize();
    }
}

