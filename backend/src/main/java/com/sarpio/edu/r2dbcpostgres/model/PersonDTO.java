package com.sarpio.edu.r2dbcpostgres.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class PersonDTO {

    private long id;

    @NotBlank(message = "First name cannot be blank")
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 3, max = 50)
    @Column("first_name")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @NotEmpty(message = "Last Name cannot be empty")
    @Size(min = 3, max = 50)
    @Column("last_name")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 10, max = 50)
    @Email(message = "Email address has incorrect format")
    private String email;

    @NotBlank(message = "Job name cannot be blank")
    @NotEmpty(message = "Job name cannot be empty")
    @Size(min = 3, max = 50)
    private String job;

    @NotBlank(message = "Phone name cannot be blank")
    @NotEmpty(message = "Phone name cannot be empty")
    @Size(min = 9, max = 50)
    private String phone;
}
