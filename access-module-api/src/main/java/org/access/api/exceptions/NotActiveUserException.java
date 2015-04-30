package org.access.api.exceptions;

public class NotActiveUserException extends Exception {
	public NotActiveUserException() {

	}

	public NotActiveUserException(String message) {
		super(message);
	}

	public NotActiveUserException(Throwable cause) {
		super(cause);
	}

	public NotActiveUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
