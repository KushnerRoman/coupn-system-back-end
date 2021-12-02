package com.jbc.exceptions.customersExceptions;

public class CustomerExistAlreadyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 151675441134954678L;

	public CustomerExistAlreadyException() {
		
	}

	public CustomerExistAlreadyException(String message) {
		super(message);
		
	}

	public CustomerExistAlreadyException(Throwable cause) {
		super(cause);
		
	}

	public CustomerExistAlreadyException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CustomerExistAlreadyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
