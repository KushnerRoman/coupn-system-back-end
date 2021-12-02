package com.jbc.exceptions.loginExceptions;

public class CouponLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1030703806270426360L;

	public CouponLoginException() {
	
	}

	public CouponLoginException(String message) {
		super(message);
	
	}

	public CouponLoginException(Throwable cause) {
		super(cause);

	}

	public CouponLoginException(String message, Throwable cause) {
		super(message, cause);

	}

	public CouponLoginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
