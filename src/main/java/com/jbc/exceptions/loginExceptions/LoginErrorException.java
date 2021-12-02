package com.jbc.exceptions.loginExceptions;

public class LoginErrorException extends Exception {

	public LoginErrorException() {

	}

	public LoginErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public LoginErrorException(String message, Throwable cause) {
		super(message, cause);

	}

	public LoginErrorException(String message) {
		super(message);
	
	}

	public LoginErrorException(Throwable cause) {
		super(cause);
	
	}

	
	
	
}
