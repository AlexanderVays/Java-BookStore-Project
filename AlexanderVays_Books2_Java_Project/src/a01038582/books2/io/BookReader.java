package a01038582.books2.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.ApplicationException;
import a01038582.books2.data.Book;
import a01038582.books2.data.Purchase;
import a01038582.books2.data.util.BookSorter;

public class BookReader {

	private static final Logger LOG = LogManager.getLogger();
	static ArrayList<Book> books = new ArrayList<>();

	/**
	 * @return an Array of Book
	 * @throws ApplicationException
	 *             in case not enough number of elements in book
	 * @throws IOException
	 *             if IOException thrown while reading data from a file
	 */
	public static ArrayList<Book> readInputBookFile() throws ApplicationException, IOException {
		File file = new File("books500.csv");
		FileReader in = null;
		Iterable<CSVRecord> records = null;
		LOG.debug("Reading " + file.getAbsolutePath());
		try {
			in = new FileReader(file);
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		if (records != null) {
			for (CSVRecord record : records) {
				if (record.size() < 8) {
					throw new ApplicationException(
							String.format("Expected 8 but got %d %s", record.size(), record.toString()));
				}
				String id = record.get("book_id");
				String isbn = record.get("isbn");
				String author = record.get("authors");
				String originalPublicationYear = record.get("original_publication_year");
				String originalTitle = record.get("original_title");
				String averageRating = record.get("average_rating");
				String ratingsCount = record.get("ratings_count");
				String imageUrl = record.get("image_url");
				Book book = new Book.Builder(Integer.parseInt(id), isbn).setAuthor(author)
						.setOriginalPublicationYear(Integer.parseInt(originalPublicationYear))
						.setOriginalTitle(originalTitle).setAverageRating(Double.parseDouble(averageRating))
						.setRatingsCount(Integer.parseInt(ratingsCount)).setImageUrl(imageUrl).build();
				books.add(book);
			}
			in.close();
			return books;
		}
		return null;
	}

	/**
	 * @param purchase
	 *            the Purchase object
	 * @return string as the book title
	 * @throws ApplicationException
	 *             if happen on run
	 */
	public static String sortBooks(Purchase purchase) throws ApplicationException {
		String output = null;
		Purchase temp = purchase;
		int a = temp.getBookId();
		for (Book i : books) {
			if (i.getBookId() == a) {
				output = i.getOriginalTitle();
			}
		}
		return output;
	}

	/**
	 * @param purchases
	 *            the ArrayList of Purchase objects
	 * @return the ArrayList of Purchase objects
	 */
	public static ArrayList<Purchase> sortPurchaseByTitles(ArrayList<Purchase> purchases) {
		ArrayList<Purchase> tempPurchases = new ArrayList<>();
		ArrayList<Purchase> purch = new ArrayList<>();
		ArrayList<Book> tempBooks = new ArrayList<>();
		boolean run;
		for (Purchase i : purchases) {
			run = true;
			int a = i.getBookId();
			for (Book j : books) {
				if ((j.getBookId() == a) && (run)) {
					tempBooks.add(j);
					run = false;
				}
			}
		}
		tempBooks.sort(new BookSorter.compareByTitle());
		purch = purchases;
		for (Book i : tempBooks) {
			long b = i.getBookId();
			for (int j = 0; j < purch.size(); j++) {
				if (purch.get(j).getBookId() == b) {
					tempPurchases.add(purch.get(j));
					purch.remove(j);
				}
			}

		}
		return tempPurchases;
	}

	/**
	 * @param purchases
	 *            the ArrayList of Purchase objects
	 * @return the ArrayList of Purchase objects
	 */
	public static ArrayList<Purchase> sortPurchaseByTitlesDESC(ArrayList<Purchase> purchases) {
		ArrayList<Purchase> tempPurchases = new ArrayList<>();
		ArrayList<Purchase> purch = new ArrayList<>();
		ArrayList<Book> tempBooks = new ArrayList<>();
		boolean run;
		for (Purchase i : purchases) {
			run = true;
			int a = i.getBookId();
			for (Book j : books) {
				if ((j.getBookId() == a) && (run)) {
					tempBooks.add(j);
					run = false;
				}
			}
		}
		tempBooks.sort(new BookSorter.compareByTitleDescending());
		purch = purchases;
		for (Book i : tempBooks) {
			long b = i.getBookId();
			for (int j = 0; j < purch.size(); j++) {
				if (purch.get(j).getBookId() == b) {
					tempPurchases.add(purch.get(j));
					purch.remove(j);
				}
			}

		}
		return tempPurchases;
	}
}
