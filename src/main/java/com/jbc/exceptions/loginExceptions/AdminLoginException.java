package com.jbc.exceptions.loginExceptions;

public class AdminLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminLoginException() {
	
	}

	public AdminLoginException(String message) {
		super(message);
		
	}

	public AdminLoginException(Throwable cause) {
		super(cause);
		
	}
	public AdminLoginException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AdminLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
