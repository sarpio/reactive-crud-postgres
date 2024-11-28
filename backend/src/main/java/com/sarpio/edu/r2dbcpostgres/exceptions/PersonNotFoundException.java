package com.sarpio.edu.r2dbcpostgres.exceptions;

public class PersonNotFoundException  extends RuntimeException {
	public PersonNotFoundException(String message) {
		super(message);
	}

}
