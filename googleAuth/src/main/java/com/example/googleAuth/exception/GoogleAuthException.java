package com.example.googleAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GoogleAuthException extends RuntimeException {

	public GoogleAuthException(String message) {
		super(message);
	}

}
