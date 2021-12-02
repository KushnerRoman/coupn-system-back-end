package com.jbc.exceptions.companiesExceptions;

public class CompanyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 36689306751921193L;

	public CompanyNotFoundException() {
		
	}

	public CompanyNotFoundException(String message) {
		super(message);
		
	}

	public CompanyNotFoundException(Throwable cause) {
		super(cause);
	
	}

	public CompanyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public CompanyNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

}
