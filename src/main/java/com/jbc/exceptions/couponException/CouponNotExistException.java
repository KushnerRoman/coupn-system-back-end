package com.jbc.exceptions.couponException;

public class CouponNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponNotExistException() {
		// TODO Auto-generated constructor stub
	}

	public CouponNotExistException(String message) {
		super(message);
		
	}

	public CouponNotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CouponNotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CouponNotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
