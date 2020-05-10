package a01038582.books2.data.util;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String YYYYMMDD_PATTERN = "(20\\d{2})(\\d{2})(\\d{2})"; // valid for years 2000-2099

	private Validator() {
	}

	/**
	 * Validate an email string.
	 * 
	 * @param email
	 *            the email string.
	 * @return true if the email address is valid, false otherwise.
	 */
	public static boolean validateEmail(final String email) {
		return email.matches(EMAIL_PATTERN);
	}

	/**
	 * @param yyyymmdd
	 *            the string
	 * @return true or false after date validation
	 */
	public static boolean validateJoinedDate(String yyyymmdd) {
		return yyyymmdd.matches(YYYYMMDD_PATTERN);
	}

}
