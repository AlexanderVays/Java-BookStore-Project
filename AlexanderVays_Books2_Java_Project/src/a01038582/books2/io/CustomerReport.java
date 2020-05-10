package a01038582.books2.io;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import a01038582.books2.data.Customer;
import a01038582.books2.data.util.Common;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class CustomerReport {

	public static final String HORIZONTAL_LINE = "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	public static final String HEADER_FORMAT = "%4s. %-6s %-12s %-12s %-40s %-25s %-12s %-15s %-42s%s";
	public static final String CUSTOMER_FORMAT = "%4d. %06d %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s";

	/**
	 * private constructor to prevent instantiation
	 */
	private CustomerReport() {
	}

	/**
	 * Print the report.
	 * 
	 * @param customers
	 *            indicates Customer list
	 * @param out
	 *            the PrintStream out
	 */
	public static void printReport(List<Customer> customers, PrintStream out) {
		if (customers != null) {
			String text = null;
			println("Customers Report", out);
			System.out.println("Customers Report");
			println(HORIZONTAL_LINE, out);
			System.out.println(HORIZONTAL_LINE);
			text = String.format(HEADER_FORMAT, "#", "ID", "First name", "Last name", "Street", "City", "Postal Code",
					"Phone", "Email", "Join Date");
			println(text, out);
			System.out.println(text);
			println(HORIZONTAL_LINE, out);
			System.out.println(HORIZONTAL_LINE);

			int i = 0;
			for (Customer customer : customers) {
				LocalDate date = customer.getJoinedDate();
				text = String.format(CUSTOMER_FORMAT, ++i, customer.getId(), customer.getFirstName(),
						customer.getLastName(), customer.getStreet(), customer.getCity(), customer.getPostalCode(),
						customer.getPhone(), customer.getEmailAddress(), Common.DATE_FORMAT.format(date));
				println(text, out);
				System.out.println(text);
			}
		}
	}

	private static void println(String text, PrintStream out) {
		out.println(text);
	}
}
