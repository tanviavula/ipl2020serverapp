package com.nubes.ipl2020.service.exception;

public class PlayerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException(String message) {
		super(message);
	}

	public PlayerNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
