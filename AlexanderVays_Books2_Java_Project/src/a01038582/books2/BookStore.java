package a01038582.books2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01038582.books2.data.Book;
import a01038582.books2.data.Customer;
import a01038582.books2.data.Purchase;
import a01038582.books2.database.BookDao;
import a01038582.books2.database.CustomerDao;
import a01038582.books2.database.Database;
import a01038582.books2.database.DbConstants;
import a01038582.books2.database.PurchaseDao;
import a01038582.books2.io.BookReader;
import a01038582.books2.io.CustomerReader;
import a01038582.books2.io.PurchaseReader;
import a01038582.books2.ui.MainFrame;

/**
 * Project: Books
 * File: BookStore.java
 * Date: May, 2018
 */

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class BookStore {

	public static Database database;
	public static Connection connection;

	private static ArrayList<Customer> customers;
	private static List<Book> books;
	private static List<Purchase> purchases;

	public static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";

	static {
		configureLogging();
	}

	public static final Logger LOG = LogManager.getLogger();

	private static void configureLogging() {
		ConfigurationSource source;

		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(String.format(
					"WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
					LOG4J_CONFIG_FILENAME));
		}
	}

	/**
	 * @param args
	 *            the Commindline arguments
	 * @throws ApplicationException
	 *             if ApplicationException thrown
	 * @throws ParseException
	 *             if ParseException thrown
	 */
	public BookStore(String[] args) throws ApplicationException, ParseException {
		LOG.info("Starting Books");
		BookOptions.process(args);
	}

	/**
	 * @param args
	 *            the arguments indicates arguments
	 * @throws Exception
	 *             if Exception thrown
	 */
	public static void main(String[] args) throws Exception {
		Instant startTime = Instant.now();
		LOG.info(startTime);

		File dbPropertiesFile = new File(DbConstants.DB_PROPERTIES_FILENAME);
		if (!dbPropertiesFile.exists()) {
			showUsage();
			System.exit(-1);
		}
		database = new Database(dbPropertiesFile);
		connection = database.getConnection();
		run();
		new MainFrame();
		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	public static void run() throws Exception {
		LOG.debug("run()");
		database.run();
		try {
			if (!database.tableExists(DbConstants.CUSTOMER_TABLE_NAME)) {
				customerTable();
			}
			if (!database.tableExists(DbConstants.BOOK_TABLE_NAME)) {
				bookTable();
			}
			if (!database.tableExists(DbConstants.PURCHASE_TABLE_NAME)) {
				purchaseTable();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	private static void showUsage() {
		System.err.println(
				String.format("Program cannot start because %s cannot be found.", DbConstants.DB_PROPERTIES_FILENAME));
	}

	public static void customerTable() throws SQLException, ApplicationException {
		try {
			Statement statement = connection.createStatement();
			CustomerDao.create(statement);
			customers = CustomerReader.readInputCustomerFile();
			CustomerDao.insertCustomersFromFile(statement, customers);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	public static void bookTable() throws SQLException, ApplicationException, IOException {
		try {
			Statement statement = connection.createStatement();
			BookDao.create(statement);
			books = BookReader.readInputBookFile();
			BookDao.insertBooksFromFile(statement, books);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	public static void purchaseTable() throws SQLException, ApplicationException, IOException {
		try {
			Statement statement = connection.createStatement();
			PurchaseDao.create(statement);
			purchases = PurchaseReader.readInputPurchaseFile();
			PurchaseDao.insertPurchasesFromFile(statement, purchases);
		} catch (

		SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	public static void dropTablesFromMenu() throws Exception {
		Statement statement = connection.createStatement();
		if (database.tableExists(DbConstants.CUSTOMER_TABLE_NAME)) {
			Database.dropTable(statement, DbConstants.CUSTOMER_TABLE_NAME);
		}
		if (database.tableExists(DbConstants.BOOK_TABLE_NAME)) {
			Database.dropTable(statement, DbConstants.BOOK_TABLE_NAME);
		}
		if (database.tableExists(DbConstants.PURCHASE_TABLE_NAME)) {
			Database.dropTable(statement, DbConstants.PURCHASE_TABLE_NAME);
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static Database getDatabase() {
		return database;
	}

}
