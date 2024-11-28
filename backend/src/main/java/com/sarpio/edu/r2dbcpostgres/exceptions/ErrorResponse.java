package com.sarpio.edu.r2dbcpostgres.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponse {
	private int status;
	private Map<String, String> errors;
	private long timestamp;

	public ErrorResponse(final int status, final String message, final long timestamp) {
		this.status = status;
		this.errors = new HashMap<>();
		this.timestamp = timestamp;
		this.errors.put("message", message);
	}
}
