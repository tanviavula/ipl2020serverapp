package com.nubes.ipl2020.web;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nubes.ipl2020.service.exception.PlayerNotFoundException;
import com.nubes.ipl2020.service.exception.TeamNotFoundException;

@ControllerAdvice
public class CustomGlobalException extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ TeamNotFoundException.class,PlayerNotFoundException.class})
	public ResponseEntity<ApiErrorResponse> handleNotFound(Exception e) throws IOException {
		ApiErrorResponse error = new ApiErrorResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(e.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
}
