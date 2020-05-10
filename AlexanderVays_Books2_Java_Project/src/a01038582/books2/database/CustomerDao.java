package a01038582.books2.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.data.Customer;
import a01038582.books2.data.util.DbUtil;
import a01038582.books2.ui.MainFrame;
import a01038582.books2.ui.UIStringConstants;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class CustomerDao extends Dao {

	private static final Logger LOG = LogManager.getLogger();

	public CustomerDao(Database database) {
		super(database, DbConstants.CUSTOMER_TABLE_NAME);
	}

	public static void create(Statement statement) throws SQLException {
		String sql = String.format("create table %s(" + //
				"customerId INT, " + //
				"firstName VARCHAR(15), " + //
				"lastName VARCHAR(15), " + //
				"street VARCHAR(40), " + //
				"city VARCHAR(40), " + //
				"zipCode VARCHAR(15), " + //
				"phone VARCHAR(20), " + //
				"email VARCHAR(40), " + //
				"joinDate Date)", //
				DbConstants.CUSTOMER_TABLE_NAME);
		executeUpdate(statement, sql);
	}

	public static void insertCustomersFromFile(Statement statement, List<Customer> customers) throws SQLException {
		String sql;
		try {
			for (Customer i : customers) {
				String Data = "'" + i.getId() + "', '" + i.getFirstName() + "', '" + i.getLastName() + "', '"
						+ i.getStreet() + "', '" + i.getCity() + "', '" + i.getPostalCode() + "', '" + i.getPhone()
						+ "', '" + i.getEmailAddress() + "', '" + i.getJoinedDateString() + "'";
				sql = String.format("insert into %s values(%s)", DbConstants.CUSTOMER_TABLE_NAME, Data);
				CustomerDao.executeUpdate(statement, sql);
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

	public Customer readByCustomerId(long customerId) throws SQLException, Exception {
		Connection connection = null;
		Statement statement = null;
		Customer customer = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format("SELECT* FROM %s WHERE %s = '%s'", DbConstants.CUSTOMER_TABLE_NAME,
					Fields.CUSTOMER_ID.getName(), customerId);
			LOG.debug(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			// get the Customer
			// throw an exception if we get more than one result
			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new Exception(String.format("Expected one result, got %d", count));
				}
				customer = new Customer();
				customer.setId(resultSet.getLong((Fields.CUSTOMER_ID.getName())));
				customer.setFirstName(resultSet.getString(Fields.FIRST_NAME.getName()));
				customer.setLastName(resultSet.getString(Fields.LAST_NAME.getName()));
				customer.setStreet(resultSet.getString(Fields.STREET.getName()));
				customer.setCity(resultSet.getString(Fields.CITY.getName()));
				customer.setPhone(resultSet.getString(Fields.PHONE.getName()));
				customer.setPostalCode(resultSet.getString(Fields.POSTAL_CODE.getName()));
				customer.setEmailAddress(resultSet.getString(Fields.EMAIL.getName()));
				customer.setJoinedDate(resultSet.getDate(Fields.JOIN_DATE.getName()));
			}
		} finally {
			close(statement);
		}

		return customer;
	}

	public static int executeUpdate(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to executeUpdate: " + sql);
		int count = statement.executeUpdate(sql);
		return count;
	}

	/**
	 * @return number of customers in Customer table
	 * @throws Exception
	 *             if thrown
	 */
	public static int getNumberOfCustomers() throws Exception {
		Statement statement = null;
		Connection connection = null;
		int count = 0;
		try {
			connection = BookStore.database.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select count(*) from " + DbConstants.CUSTOMER_TABLE_NAME);

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return count;
	}

	public void add(Customer customer) throws SQLException {
		Statement statement = null;
		try {
			String sql = String.format("insert into %s values(" // 1 tableName
					+ "'%s', " // 2 Id
					+ "'%s', " // 3 FirstName
					+ "'%s', " // 4 LastName
					+ "'%s', " // 5 Street
					+ "'%s', " // 6 City
					+ "'%s', " // 7 ZipCode
					+ "'%s', " // 8 Email
					+ "'%s')", // 9 JoinDate
					DbConstants.CUSTOMER_TABLE_NAME, // 1
					customer.getId(), // 2
					customer.getFirstName(), // 3
					customer.getLastName(), // 5
					customer.getStreet(), // 6
					customer.getCity(), // 7
					customer.getPostalCode(), // 8
					customer.getEmailAddress(), // 9
					customer.getJoinedDate()); // 10
			LOG.debug(sql);
			executeUpdate(statement, sql);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			close(statement);
		}
	}

	/**
	 * @param customer
	 *            the Customer object
	 * @throws SQLException
	 *             if SQLException is thrown
	 */
	public void update(Customer customer) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format(
					"UPDATE %s set %s='%d', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s='%d'",
					tableName, //
					Fields.CUSTOMER_ID.getName(), customer.getId(), //
					Fields.FIRST_NAME.getName(), customer.getFirstName(), //
					Fields.LAST_NAME.getName(), customer.getLastName(), //
					Fields.STREET.getName(), customer.getStreet(), //
					Fields.CITY.getName(), customer.getCity(), //
					Fields.PHONE.getName(), customer.getPhone(), //
					Fields.POSTAL_CODE.getName(), customer.getPostalCode(), //
					Fields.JOIN_DATE.getName(), customer.getJoinedDate(), Fields.CUSTOMER_ID.getName(),
					customer.getId());
			LOG.debug(sql);
			int rowcount = statement.executeUpdate(sql);
			System.out.println(String.format("Updated %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	/**
	 * @param customer
	 *            the Customer object
	 * @throws SQLException
	 *             if SQLEXception is thrown
	 */
	public void delete(Customer customer) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format("DELETE from %s WHERE %s='%s'", tableName, Fields.CUSTOMER_ID.getName(),
					customer.getId());
			LOG.debug(sql);
			int rowcount = statement.executeUpdate(sql);
			System.out.println(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	public enum Fields {

		CUSTOMER_ID("customerId", "INT", 9, 1), //
		FIRST_NAME("firstName", "VARCHAR", 20, 2), //
		LAST_NAME("lastName", "VARCHAR", 20, 2), //
		STREET("street", "VARCHAR", 20, 2), //
		CITY("city", "VARCHAR", 20, 2), //
		POSTAL_CODE("zipCode", "VARCHAR", 10, 8), //
		PHONE("phone", "VARCHAR", 10, 5), //
		EMAIL("email", "VARCHAR", 40, 7), //
		JOIN_DATE("joinDate", "Date", -1, 6); //

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

	public static List<String> geIds() {
		List<String> output = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		try {
			connection = BookStore.database.getConnection();
			statement = connection.createStatement();
			ResultSet results = DbUtil.executeQueryForCustomerId(statement);
			while (results.next()) {
				output.add(String.format("%s", results.getLong(Fields.CUSTOMER_ID.getName())));
			}
		} catch (SQLException e) {
			LOG.error(e);
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return output;
	}

	public Customer getCustomer(long id) {
		Customer customer = null;
		try {
			customer = readByCustomerId(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return customer;
	}

	@Override
	public void create() throws SQLException {
	}

	public static String getSQLStatement() {
		if (MainFrame.getByJoinDate()) {
			return "SELECT* FROM " + DbConstants.CUSTOMER_TABLE_NAME + " ORDER BY joinDate";
		}
		return "SELECT* FROM " + DbConstants.CUSTOMER_TABLE_NAME;
	}

	public static String[] getCustomersList() {
		String data = UIStringConstants.CUSTOMER_LINE + "/" + UIStringConstants.CUSTOMER_HEADING + "/"
				+ UIStringConstants.CUSTOMER_LINE + "/";
		String cusList[] = null;
		try {
			Connection connection = BookStore.database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getSQLStatement());
			while (rs.next()) {
				data += rs.getString("customerId") + "                " + rs.getString("firstName") + "              "
						+ rs.getString("lastName") + "                " + rs.getString("joinDate") + "/";
			}
			cusList = data.split("/");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return cusList;
	}

	/**
	 * @param input
	 *            the string
	 * @return long the customer id
	 */
	public static long getCustomerId(String input) {
		long output = 0;
		String[] string = input.split("  ");
		output = Long.parseLong(string[0]);
		return output;
	}

}
