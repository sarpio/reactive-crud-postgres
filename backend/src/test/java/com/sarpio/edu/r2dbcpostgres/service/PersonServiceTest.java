package com.sarpio.edu.r2dbcpostgres.service;

import com.sarpio.edu.r2dbcpostgres.mapper.PersonMapper;
import com.sarpio.edu.r2dbcpostgres.model.Person;
import com.sarpio.edu.r2dbcpostgres.model.PersonDTO;
import com.sarpio.edu.r2dbcpostgres.repo.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Test
    void shouldReturnPersonList() {
    }

    @Test
    void getPersonById() {

    }

    @Test
    void createPerson() {
    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void createPersons() {
    }

    @Test
    void getDbSize() {
    }
}