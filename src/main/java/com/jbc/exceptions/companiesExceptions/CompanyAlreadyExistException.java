package com.jbc.exceptions.companiesExceptions;

public class CompanyAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 682617515062029939L;

	public CompanyAlreadyExistException() {
	
	}

	public CompanyAlreadyExistException(String message) {
		super(message);
	
	}

	public CompanyAlreadyExistException(Throwable cause) {
		super(cause);
	
	}

	public CompanyAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public CompanyAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

}
