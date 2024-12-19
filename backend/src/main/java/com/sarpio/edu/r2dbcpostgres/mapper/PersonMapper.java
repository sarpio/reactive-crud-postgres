package com.sarpio.edu.r2dbcpostgres.mapper;

import com.sarpio.edu.r2dbcpostgres.model.Person;
import com.sarpio.edu.r2dbcpostgres.model.PersonDTO;

public class PersonMapper {


    public static PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setEmail(person.getEmail());
        dto.setPhone(person.getPhone());
        dto.setJob(person.getJob());
        return dto;
    }

    public static Person toEntity(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setJob(dto.getJob());
        return person;
    }
}
