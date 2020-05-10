package a01038582.books2.io;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import a01038582.books2.ApplicationException;
import a01038582.books2.data.Purchase;

public class PurchaseReport {

	private static final int ZERO = 0;
	private static final String HORIZONTAL_LINE = "--------------------------------------------------------------------------------------------------------------------------";
	private static final String HEADER_FORMAT = "%-24s %-80s %5s";
	private static final String PURCHASE_FORMAT = "%-24s %-80s $%.2f";

	/**
	 * @param purchases
	 *            indicates Purchase class object of arrays
	 * 
	 * @param out
	 *            the PrintStream out
	 * 
	 * @throws IOException
	 *             if IOException is thrown
	 * @throws ApplicationException
	 *             if ApplicationException thrown
	 */
	public static void printReport(List<Purchase> purchases, PrintStream out) throws IOException, ApplicationException {
		String text = null;
		println("Purchases report", out);
		System.out.println("Purchases report");
		println(HORIZONTAL_LINE, out);
		System.out.println(HORIZONTAL_LINE);
		text = String.format(HEADER_FORMAT, "Name", "Title", "Price");
		println(text, out);
		System.out.println(text);
		println(HORIZONTAL_LINE, out);
		System.out.println(HORIZONTAL_LINE);
		for (Purchase i : purchases) {

			text = String.format(PURCHASE_FORMAT, CustomerReader.sortCustomer(i), BookReader.sortBooks(i),
					i.getPrice());
			println(text, out);
			System.out.println(text);
		}
	}

	/**
	 * @param purchases
	 *            the List of Purchase objects
	 * @return double the total purchases
	 */
	public static double getTotalPurchases(List<Purchase> purchases) {
		double total = ZERO;
		for (Purchase i : purchases) {
			total += i.getPrice();
		}
		return total;
	}

	/**
	 * @param text
	 *            the string text
	 * @param out
	 *            the PrintStream
	 */
	public static void println(String text, PrintStream out) {
		out.println(text);
	}

}
