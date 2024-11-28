package com.sarpio.edu.r2dbcpostgres.service;

import com.sarpio.edu.r2dbcpostgres.exceptions.PersonAlreadyExistsException;
import com.sarpio.edu.r2dbcpostgres.exceptions.PersonNotFoundException;
import com.sarpio.edu.r2dbcpostgres.mapper.PersonMapper;
import com.sarpio.edu.r2dbcpostgres.model.PersonDTO;
import com.sarpio.edu.r2dbcpostgres.repo.PersonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private final PersonRepository personRepository;

    public Flux<PersonDTO> getAllPersons(int page, int size, String sortBy, String ascending) {
        page = page - 1;
        if (page < 0) page = 0;

        boolean order = "asc".equalsIgnoreCase(ascending);

        Sort sort = Sort.by(order ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return personRepository.findAllBy(pageable)
                .publishOn(Schedulers.boundedElastic()) // Makes delay as non-blocking operation to simulate slow bandwidth
                .doOnNext(person -> {
                    try {
                        sleep(0);   // delay in ms between single record publishing
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).map(PersonMapper::toDTO);
    }

    public Mono<PersonDTO> getPersonById(Long id) {
        return personRepository
                .findById(id)
                .switchIfEmpty(
                        Mono.error(new PersonNotFoundException("No Person"))
                ).map(PersonMapper::toDTO);
    }

    public Flux<PersonDTO> getPersonByEmail(@Valid final String email) {


        return personRepository
                .findByEmail(email)
                .switchIfEmpty(
                        Flux.error(new PersonNotFoundException("No Person found with email: " + email))
                ).map(PersonMapper::toDTO);
    }

    public Mono<PersonDTO> createPerson(@Valid PersonDTO person) {
        return personRepository.findByEmail(person.getEmail())
                .collectList()
                .flatMap(existingEmail -> {
                    if (!existingEmail.isEmpty()) {
                        return Mono.error(new PersonAlreadyExistsException("Person with email: " + person.getEmail() + " already exists"));
                    }
                    return personRepository.save(PersonMapper.toEntity(person)).map(PersonMapper::toDTO);
                });
    }

    public Mono<PersonDTO> updatePerson(Long id, PersonDTO person) {
        person.setId(id);
        return personRepository.updatePersonData(
                        id,
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        person.getJob(),
                        person.getPhone()
                        )
                .flatMap(
                        rowsUpdated -> personRepository.findPersonById(person.getId())).map(PersonMapper::toDTO);
    }


    public Mono<String> deletePerson(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new PersonNotFoundException("Person with ID: " + id + " not found")))
                .flatMap(person -> personRepository.deleteById(id)).then(Mono.just("Person with id: " + id + " has been deleted"));
    }

    public void createPersons(List<PersonDTO> dtos) {
        long start = System.currentTimeMillis();
        Flux.fromIterable(dtos)
                .map(PersonMapper::toEntity)
                .flatMap(personRepository::save)
                .onErrorComplete()
                .subscribe(/*(person) -> System.out.println(), (error) -> System.out.println("Error occurred: " + error),() -> System.out.println("All people saved")*/);
    }

    public Mono<Long> getDbSize() {
        return personRepository.count();
    }
}
