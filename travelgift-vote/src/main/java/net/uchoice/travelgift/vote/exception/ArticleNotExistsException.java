package net.uchoice.travelgift.vote.exception;

public class ArticleNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArticleNotExistsException() {
		super();
	}

	public ArticleNotExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ArticleNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArticleNotExistsException(String message) {
		super(message);
	}

	public ArticleNotExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
