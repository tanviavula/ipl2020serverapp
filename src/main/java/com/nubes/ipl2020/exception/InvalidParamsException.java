package com.nubes.ipl2020.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidParamsException {
	@ExceptionHandler({ IllegalArgumentException.class})
	public ResponseEntity<ApiErrorResponse> handleNotFound(Exception e) throws IOException {
		ApiErrorResponse error = new ApiErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError(e.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
}
