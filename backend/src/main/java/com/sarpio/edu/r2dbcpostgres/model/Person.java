package com.sarpio.edu.r2dbcpostgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("persons")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Person Model")
public class Person implements Persistable<Long> {

	@Id

	@Schema(description = "Unique person id")
	private long id;

	@Schema(
			description = "First Name",
			maxLength = 50,
			example = "John"
	)
	@Column("first_name")
	private String firstName;

	@Schema(
			description = "Last Name",
			maxLength = 50,
			example = "Smith"
	)
	@Column("last_name")
	private String lastName;

	@Schema(
			description = "Email address",
			maxLength = 50,
			example = "john.doe@gmail.com"
	)

	private String email;

	@Schema(
			description = "Person role in  organization",
			maxLength = 50,
			example = "Sales Manager"
	)
	private String job;

	@Schema(
			description = "Phone number in US format",
			maxLength = 50,
			example = "(623) 131-7085"
	)
	private String phone;

	@Transient @JsonIgnore @Builder.Default
	private boolean isNewEntry = true;

	@Override
	public Long getId() {
		return this.id;
	}

	@Schema(description = "technical record Transient")
	public boolean isNew() {
		return isNewEntry;
	}
}
