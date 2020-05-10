package a01038582.books2;

@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	public ApplicationException() {
		super();
	}

	/**
	 * Constructs a new throwable with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 * 
	 * @param message
	 *            indicates specified detail message
	 * @param cause
	 *            indicates specified detail cause
	 * @param enableSuppression
	 *            indicates suppression enabled or disabled
	 * @param writableStackTrace
	 *            indicates writable stack trace enabled or disabled.
	 */
	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new throwable with the specified detail message and cause.
	 * 
	 * @param message
	 *            indicates specified detail message
	 * @param cause
	 *            indicates specified detail cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new throwable with the specified detail message.
	 * 
	 * @param message
	 *            indicates specified detail message
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new throwable with the specified detail message.
	 * 
	 * @param cause
	 *            indicates specified detail cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

}
