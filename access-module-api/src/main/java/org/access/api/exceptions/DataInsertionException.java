package org.access.api.exceptions;

public class DataInsertionException extends Exception {
	public DataInsertionException() {
	}

	public DataInsertionException(String message) {
		super(message);
	}

	public DataInsertionException(Throwable cause) {
		super(cause);
	}

	public DataInsertionException(String message, Throwable cause) {
		super(message, cause);
	}
}
