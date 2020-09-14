package com.qa.ndtv.core;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5288040126302961917L;

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

}
