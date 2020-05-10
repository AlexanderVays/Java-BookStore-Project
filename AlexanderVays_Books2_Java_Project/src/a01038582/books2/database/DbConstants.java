package a01038582.books2.database;

/**
 * @author Alexander Vays, A01038582
 *
 */
public interface DbConstants {

	String DB_PROPERTIES_FILENAME = "db.properties";
	String DB_DRIVER_KEY = "db.driver";
	String DB_URL_KEY = "db.url";
	String DB_USER_KEY = "db.user";
	String DB_PASSWORD_KEY = "db.password";
	String TABLE_ROOT = "_A01038582";
	String CUSTOMER_TABLE_NAME = "Customer" + TABLE_ROOT;
	String BOOK_TABLE_NAME = "Book" + TABLE_ROOT;
	String PURCHASE_TABLE_NAME = "Purchase" + TABLE_ROOT;
}
