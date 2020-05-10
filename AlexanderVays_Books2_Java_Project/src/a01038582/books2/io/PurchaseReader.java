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
import a01038582.books2.data.Purchase;

public class PurchaseReader {

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * @return the ArrayList of Purchase objects
	 * @throws ApplicationException
	 *             in case not enough number of elements in purchase
	 * @throws IOException
	 *             if IOException thrown while reading data from a file
	 */
	public static ArrayList<Purchase> readInputPurchaseFile() throws ApplicationException, IOException {
		ArrayList<Purchase> purchases = new ArrayList<>();
		File file = new File("purchases.csv");
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
				if (record.size() < 4) {
					throw new ApplicationException(
							String.format("Expected 4 but got %d %s", record.size(), record.toString()));
				}
				String id = record.get("id");
				String customerId = record.get("customer_id");
				String bookId = record.get("book_id");
				String price = record.get("price");

				Purchase purchase = new Purchase.Builder(Integer.parseInt(id), Integer.parseInt(customerId),
						Integer.parseInt(bookId)).setPrice(Double.parseDouble(price)).build();
				purchases.add(purchase);
			}
			in.close();
			return purchases;
		}
		return null;
	}
}
