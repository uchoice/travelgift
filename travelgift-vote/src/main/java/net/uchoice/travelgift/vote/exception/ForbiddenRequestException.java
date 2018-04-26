package net.uchoice.travelgift.vote.exception;

public class ForbiddenRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenRequestException() {
		super();
	}

	public ForbiddenRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ForbiddenRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenRequestException(String message) {
		super(message);
	}

	public ForbiddenRequestException(Throwable cause) {
		super(cause);
	}
	
}
