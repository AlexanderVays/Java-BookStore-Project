package a01038582.books2.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.data.Purchase;
import a01038582.books2.ui.MainFrame;
import a01038582.books2.ui.UIStringConstants;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class PurchaseDao extends Dao {

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * @param database
	 *            the database
	 */
	public PurchaseDao(Database database) {
		super(database, DbConstants.PURCHASE_TABLE_NAME);
	}

	public static void create(Statement statement) throws SQLException {
		String sql = String.format("create table %s(" + //
				"purchaseId INT, " + //
				"customerId INT, " + //
				"bookId INT, " + //
				"price FLOAT)", //
				DbConstants.PURCHASE_TABLE_NAME);
		executeUpdate(statement, sql);
	}

	/**
	 * @param statement
	 *            the Statement
	 * @param purchases
	 *            the Purchase list
	 * @throws SQLException
	 *             if thrown SQLException
	 */
	public static void insertPurchasesFromFile(Statement statement, List<Purchase> purchases) throws SQLException {
		String sql;
		try {
			for (Purchase i : purchases) {
				String Data = "'" + i.getPurchaseId() + "', '" + i.getCustomerId() + "', '" + i.getBookId() + "', '"
						+ i.getPrice() + "'";
				sql = String.format("insert into %s values(%s)", DbConstants.PURCHASE_TABLE_NAME, Data);
				CustomerDao.executeUpdate(statement, sql);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param statement
	 *            the Statement
	 * @param sql
	 *            the sql
	 * @return count
	 * @throws SQLException
	 *             if thrown SQLException
	 */
	public static int executeUpdate(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to executeUpdate: " + sql);
		int count = statement.executeUpdate(sql);
		return count;
	}

	/**
	 * @param sql
	 *            the sql command string
	 * @return number of books in books table
	 * @throws Exception
	 *             if thrown
	 */
	public static double getTotalOfPurchases(String sql) throws Exception {
		Statement statement = null;
		Connection connection = null;
		double count = 0;
		try {
			connection = BookStore.database.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				count = resultSet.getDouble(1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return count;
	}

	/**
	 * @param purchase
	 *            the Purchase object
	 * @throws SQLException
	 *             if thrown SQLException
	 */
	public void add(Purchase purchase) throws SQLException {
		Statement statement = null;
		try {
			String sql = String.format("insert into %s values(" // 1 tableName
					+ "'%s', " // 2 purchaseId
					+ "'%s', " // 3 customerId
					+ "'%s', " // 4 bookId
					+ "'%s')", // 5 price
					DbConstants.PURCHASE_TABLE_NAME, // 1
					purchase.getPurchaseId(), // 2
					purchase.getCustomerId(), // 3
					purchase.getBookId(), // 4
					purchase.getPrice()); // 5
			LOG.debug(sql);
			executeUpdate(statement, sql);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}
	}

	/**
	 * @param purchase
	 *            the Purchase object
	 * @throws SQLException
	 *             if thrown SQLException
	 */
	public void update(Purchase purchase) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format(
					"UPDATE %s set %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s='%s'",
					tableName, //
					Fields.PURCHASE_ID.getName(), purchase.getPurchaseId(), //
					Fields.CUSTOMER_ID.getName(), purchase.getCustomerId(), //
					Fields.BOOK_ID.getName(), purchase.getBookId(), //
					Fields.PRICE.getName(), purchase.getPrice());
			LOG.debug(sql);
			int rowcount = statement.executeUpdate(sql);
			System.out.println(String.format("Updated %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	public enum Fields {

		PURCHASE_ID("purchaseId", "INT", 9, 1), //
		CUSTOMER_ID("customerId", "INT", 9, 2), //
		BOOK_ID("bookId", "INT", 9, 3), //
		PRICE("price", "FLOAT", 9, 4); //

		private final String name;
		private final String type;
		private final int length;
		private final int column;

		Fields(String name, String type, int length, int column) {
			this.name = name;
			this.type = type;
			this.length = length;
			this.column = column;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public int getLength() {
			return length;
		}

		public int getColumn() {
			return column;
		}
	}

	@Override
	public void create() throws SQLException {
	}

	public static String getSQLStatement() {
		if (MainFrame.getByLastName()) {
			if (MainFrame.getPurchasesDesc()) {
				return "SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
						+ "FROM Book_A01038582 "
						+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
						+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId "
						+ "ORDER BY lastName DESC";
			} else {
				return "SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
						+ "FROM Book_A01038582 "
						+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
						+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId "
						+ "ORDER BY lastName";
			}
		}

		else if (MainFrame.getByTitle()) {
			if (MainFrame.getPurchasesDesc()) {
				return "SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
						+ "FROM Book_A01038582 "
						+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
						+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId "
						+ "ORDER BY originalTitle DESC";
			} else {
				return "SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
						+ "FROM Book_A01038582 "
						+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
						+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId "
						+ "ORDER BY originalTitle";
			}
		}

		else {
			return "SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
					+ "FROM Book_A01038582 "
					+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
					+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId ";
		}
	}

	public static String[] getPurchasesList() {
		String data = UIStringConstants.PURCHASE_LINE + "/" + UIStringConstants.PURCHASE_HEADING + "/"
				+ UIStringConstants.PURCHASE_LINE + "/";
		String bookList[] = null;
		try {
			Connection connection = BookStore.database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getSQLStatement());
			while (rs.next()) {
				data += checkString(rs.getString("firstName") + " " + rs.getString("lastName"))
						+ "                          "
						+ checkString(BookDao.truncateString(rs.getString("originalTitle"), 25))
						+ "                          " + rs.getString("price") + "/";
			}
			bookList = data.split("/");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return bookList;
	}

	public static String[] getPurchasesByCustomerId(Integer id) {
		String data = UIStringConstants.PURCHASE_LINE + "/" + UIStringConstants.PURCHASE_HEADING + "/"
				+ UIStringConstants.PURCHASE_LINE + "/";
		String bookList[] = null;
		try {
			Connection connection = BookStore.database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT Customer_A01038582.firstName, Customer_A01038582.lastName, Book_A01038582.originalTitle, Purchase_A01038582.price "
							+ "FROM Book_A01038582 "
							+ "INNER JOIN Purchase_A01038582 ON Book_A01038582.bookId = Purchase_A01038582.bookId "
							+ "INNER JOIN Customer_A01038582 ON Customer_A01038582.customerId = Purchase_A01038582.customerId "
							+ "WHERE Customer_A01038582.customerId = " + id);
			while (rs.next()) {
				data += rs.getString("firstName") + " " + rs.getString("lastName") + "                       "
						+ checkString(BookDao.truncateString(rs.getString("originalTitle"), 25))
						+ "                            " + checkString(rs.getString("price")) + "/";
			}
			bookList = data.split("/");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return bookList;
	}

	public static String checkString(String input) {
		String output = "";
		String space = " ";
		StringBuilder sb = new StringBuilder(50);
		if (input.length() <= 10) {
			sb.append(input + space + space + space + space + space + space);
		} else if ((input.length() < 10) && (input.length() <= 15)) {
			sb.append(input + space + space + space + space);
		} else if ((input.length() > 15) && (input.length() <= 20)) {
			sb.append(input + space + space + space);
		} else if ((input.length() > 20) && (input.length() <= 25)) {
			sb.append(input + space + space);
		} else {
			sb.append(input);
		}
		output = sb.toString();
		return output;
	}

}
