package a01038582.books2.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.data.Book;
import a01038582.books2.ui.MainFrame;
import a01038582.books2.ui.UIStringConstants;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class BookDao extends Dao {

	private static final Logger LOG = LogManager.getLogger();

	public static final String FIELD_DELIMITER = "\\'";

	public BookDao(Database database) {
		super(database, DbConstants.BOOK_TABLE_NAME);
	}

	public static void create(Statement statement) throws SQLException {
		String sql = String.format("create table %s(" + //
				"bookId INT, " + //
				"isbn VARCHAR(30), " + //
				"author VARCHAR(45), " + //
				"originalPublicationYear INT, " + //
				"originalTitle VARCHAR(45), " + //
				"averageRating FLOAT, " + //
				"ratingsCount INT, " + //
				"imageUrl VARCHAR(100))", //
				DbConstants.BOOK_TABLE_NAME);
		executeUpdate(statement, sql);
	}

	public static void insertBooksFromFile(Statement statement, List<Book> books) throws SQLException {
		String sql;
		try {
			for (Book i : books) {
				String Data = "'" + i.getBookId() + "', '" + i.getIsbn() + "', '"
						+ checkTheSyntax(truncateString(i.getAuthor(), 41)) + "', '" + i.getOriginalPublicationYear()
						+ "', '" + checkTheSyntax(truncateString(i.getOriginalTitle(), 41)) + "', '"
						+ i.getAverageRating() + "', '" + i.getRatingsCount() + "', '" + i.getImageUrl() + "'";
				sql = String.format("insert into %s values(%s)", DbConstants.BOOK_TABLE_NAME, Data);
				BookDao.executeUpdate(statement, sql);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public Book readByBookId(long bookId) throws SQLException, Exception {
		Connection connection = null;
		Statement statement = null;
		Book book = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", DbConstants.BOOK_TABLE_NAME,
					Fields.BOOK_ID.getName(), bookId);
			LOG.debug(sql);
			ResultSet resultSet = statement.executeQuery(sql);

			// get the Student
			// throw an exception if we get more than one result
			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new Exception(String.format("Expected one result, got %d", count));
				}
				book = new Book();
				book.setBookId(resultSet.getLong((Fields.BOOK_ID.getName())));
				book.setIsbn(resultSet.getString(Fields.ISBN.getName()));
				book.setAuthor(resultSet.getString(checkTheSyntax(truncateString(Fields.AUTHOR.getName(), 40))));
				book.setOriginalPublicationYear(resultSet.getInt(Fields.ORIGINAL_PUBLICATION_YEAR.getName()));
				book.setOriginalTitle(
						resultSet.getString(checkTheSyntax(truncateString(Fields.ORIGINAL_TITLE.getName(), 40))));
				book.setAverageRating(resultSet.getDouble(Fields.AVERAGE_RATING.getName()));
				book.setRatingsCount(resultSet.getInt(Fields.RAITING_COUNT.getName()));
				book.setImageUrl(resultSet.getString(Fields.IMAGE_URL.getName()));
			}
		} finally {
			close(statement);
		}

		return book;
	}

	public static int executeUpdate(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to executeUpdate: " + sql);
		int count = statement.executeUpdate(sql);
		return count;
	}

	/**
	 * @return number of books in books table
	 * @throws Exception
	 *             if thrown
	 */
	public static int getNumberOfBooks() throws Exception {
		Statement statement = null;
		Connection connection = null;
		int count = 0;
		try {
			connection = BookStore.database.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select count(*) from " + DbConstants.BOOK_TABLE_NAME);

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return count;
	}

	public enum Fields {

		BOOK_ID("bookId", "INT", 9, 1), //
		ISBN("isbn", "VARCHAR", 20, 2), //
		AUTHOR("author", "VARCHAR", 20, 3), //
		ORIGINAL_PUBLICATION_YEAR("originalPublicationYear", "INT", 9, 4), //
		ORIGINAL_TITLE("originalTitle", "VARCHAR", 40, 5), //
		AVERAGE_RATING("averageRating", "DOUBLE", 9, 6), //
		RAITING_COUNT("ratingsCount", "INT", 9, 7), //
		IMAGE_URL("imageUrl", "VARCHAR", 40, 8); //

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

	public void add(Book book) throws SQLException {
		Statement statement = null;
		try {
			String sql = String.format("insert into %s values(" // 1 tableName
					+ "'%s', " // 2 Id
					+ "'%s', " // 3 ISBN
					+ "'%s', " // 4 Author
					+ "'%s', " // 5 Original Publication Year
					+ "'%s', " // 6 Original Title
					+ "'%s', " // 7 Average Rating
					+ "'%s', " // 8 RaitingsCount
					+ "'%s')", // 9 ImageURL
					DbConstants.BOOK_TABLE_NAME, // 1
					book.getBookId(), // 2
					book.getIsbn(), // 3
					book.getAuthor(), // 5
					book.getOriginalPublicationYear(), // 6
					book.getOriginalTitle(), // 7
					book.getAverageRating(), // 8
					book.getRatingsCount(), // 9
					book.getImageUrl()); // 10
			LOG.debug(sql);
			executeUpdate(statement, sql);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}
	}

	/**
	 * @param book
	 *            the Book object
	 * @throws SQLException
	 *             if SQLException is thrown
	 */
	public void update(Book book) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format(
					"UPDATE %s set %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s='%s'",
					tableName, //
					Fields.BOOK_ID.getName(), book.getBookId(), //
					Fields.ISBN.getName(), book.getIsbn(), //
					Fields.AUTHOR.getName(), book.getAuthor(), //
					Fields.ORIGINAL_PUBLICATION_YEAR.getName(), book.getOriginalPublicationYear(), //
					Fields.ORIGINAL_TITLE.getName(), book.getOriginalTitle(), //
					Fields.AVERAGE_RATING.getName(), book.getAverageRating(), //
					Fields.RAITING_COUNT.getName(), book.getRatingsCount(), //
					Fields.IMAGE_URL.getName(), book.getImageUrl());
			LOG.debug(sql);
			int rowcount = statement.executeUpdate(sql);
			System.out.println(String.format("Updated %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	@Override
	public void create() throws SQLException {
	}

	/**
	 * @param book
	 *            the Book object
	 * @throws SQLException
	 *             if SQLEXception is thrown
	 */
	public void delete(Book book) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format("DELETE from %s WHERE %s='%s'", tableName, Fields.BOOK_ID.getName(),
					book.getBookId());
			LOG.debug(sql);
			int rowcount = statement.executeUpdate(sql);
			System.out.println(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	/**
	 * @param input
	 *            the input string
	 * @param i
	 *            the integer as a number
	 * @return string the truncated string
	 */
	public static String truncateString(String input, int i) {
		String output;
		if (input.length() > i) {
			output = input.substring(0, i - 3) + "...";
		} else {
			output = input;
		}
		return output;
	}

	/**
	 * @param input
	 *            the input string
	 * @return String the string
	 */
	public static String checkTheSyntax(String input) {
		String output = null;
		if (input.contains("'")) {
			String[] string = input.split(FIELD_DELIMITER);
			output = string[0];
			for (int i = 1; i < string.length; i++) {
				output += string[i];
			}
			return output;
		}
		return input;
	}

	public static String getSQLStatement() {
		if (MainFrame.getByAuthor()) {
			if (MainFrame.getBooksDesc()) {
				return "SELECT* FROM " + DbConstants.BOOK_TABLE_NAME + " ORDER BY author DESC";
			} else {
				return "SELECT* FROM " + DbConstants.BOOK_TABLE_NAME + " ORDER BY author";
			}
		} else {
			return "SELECT* FROM " + DbConstants.BOOK_TABLE_NAME;
		}
	}

	public static String[] getBooksList() {
		String data = UIStringConstants.BOOK_LINE + "/" + UIStringConstants.BOOK_HEADING + "/"
				+ UIStringConstants.BOOK_LINE + "/";
		String bookList[] = null;
		try {
			Connection connection = BookStore.database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getSQLStatement());
			while (rs.next()) {
				data += rs.getString("bookId") + "          " + rs.getString("isbn") + "                 "
						+ truncateString(rs.getString("author"), 20) + "                    "
						+ truncateString(rs.getString("originalTitle"), 20) + "                    "
						+ rs.getString("originalPublicationYear") + "/";
			}
			bookList = data.split("/");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return bookList;
	}

}
