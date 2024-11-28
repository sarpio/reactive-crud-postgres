package com.sarpio.edu.r2dbcpostgres.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {
	public PersonAlreadyExistsException(String message) {
		super(message);
	}
}
