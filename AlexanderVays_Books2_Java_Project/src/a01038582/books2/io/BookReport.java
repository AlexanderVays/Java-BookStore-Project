package a01038582.books2.io;

import java.io.PrintStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.data.Book;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class BookReport {

	private static final String HORIZONTAL_LINE = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	private static final String HEADER_FORMAT = "%6s %10s %30s %35s %28s %9s %11s %18s";
	private static final String BOOK_FORMAT = "%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s";

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * @param books
	 *            indicates Book list
	 * @param out
	 *            indicates PrintStream out
	 */
	public static void printReport(List<Book> books, PrintStream out) {
		String text = null;
		println("Books Report", out);
		System.out.println("Books Report");
		println(HORIZONTAL_LINE, out);
		System.out.println(HORIZONTAL_LINE);
		text = String.format(HEADER_FORMAT, "#", "ID", "First name", "Last name", "Street", "City", "Postal Code",
				"Phone", "Email", "Join Date");
		println(text, out);
		System.out.println(text);
		println(HORIZONTAL_LINE, out);
		System.out.println(HORIZONTAL_LINE);
		for (Book i : books) {
			try {
				text = String.format(BOOK_FORMAT, i.getBookId(), i.getIsbn(), truncateString(i.getAuthor(), 40),
						truncateString(i.getOriginalTitle(), 40), i.getOriginalPublicationYear(), i.getAverageRating(),
						i.getRatingsCount(), i.getImageUrl());
				println(text, out);
				System.out.println(text);
			} catch (NullPointerException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	private static void println(String text, PrintStream out) {
		out.println(text);
	}

	/**
	 * @param input
	 *            the input string
	 * @param i
	 *            the integer as a number
	 * @return string the truncated string
	 */
	public static String truncateString(String input, int i) {
		String output = null;
		if (input.length() > i) {
			output = input.substring(0, i - 3) + "...";
		} else {
			output = input;
		}
		return output;
	}

}