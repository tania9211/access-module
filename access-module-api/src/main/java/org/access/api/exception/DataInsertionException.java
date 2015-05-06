package org.access.api.exception;

public class DataInsertionException extends Exception {

	private static final long serialVersionUID = 2090643159625295232L;

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
