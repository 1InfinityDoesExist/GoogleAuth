package com.example.googleAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class GoogleAuthExceptionHandler extends ResponseEntityExceptionHandler {

	public ResponseEntity<?> handler(GoogleAuthException ex, WebRequest webRequest) {
		GoogleAuthExceptinResponse response = new GoogleAuthExceptinResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

	}

}
