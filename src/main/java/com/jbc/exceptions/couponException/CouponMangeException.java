package com.jbc.exceptions.couponException;

public class CouponMangeException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -655214723179901068L;

	public CouponMangeException() {
	
	}

	public CouponMangeException(String message) {
		super(message);
		
	}

	public CouponMangeException(Throwable cause) {
		super(cause);
		
	}

	public CouponMangeException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public CouponMangeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
