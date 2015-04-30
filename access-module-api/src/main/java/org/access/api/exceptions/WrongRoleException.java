package org.access.api.exceptions;

public class WrongRoleException extends Exception {
	public WrongRoleException() {

	}

	public WrongRoleException(String message) {
		super(message);
	}

	public WrongRoleException(Throwable cause) {
		super(cause);
	}

	public WrongRoleException(String message, Throwable cause) {
		super(message, cause);
	}
}
