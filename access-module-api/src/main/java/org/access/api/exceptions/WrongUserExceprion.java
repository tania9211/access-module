package org.access.api.exceptions;

public class WrongUserExceprion extends Exception {
	
	public WrongUserExceprion() {

	}

	public WrongUserExceprion(String message) {
		super(message);
	}

	public WrongUserExceprion(Throwable cause) {
		super(cause);
	}

	public WrongUserExceprion(String message, Throwable cause) {
		super(message, cause);
	}
}
