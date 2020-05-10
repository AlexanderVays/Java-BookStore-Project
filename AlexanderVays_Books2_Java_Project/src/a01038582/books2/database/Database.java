package a01038582.books2.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a01038582.books2.ApplicationException;
import a01038582.books2.data.util.DbUtil;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class Database {

	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";

	private static Properties properties;
	private static Connection connection;

	static {
		configureLogging();
	}

	private static final Logger LOG = LogManager.getLogger();

	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(
					String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}

	static void connect() throws SQLException, ClassNotFoundException {
		connection = DriverManager.getConnection( //
				properties.getProperty(DbConstants.DB_URL_KEY), //
				properties.getProperty(DbConstants.DB_USER_KEY), //
				properties.getProperty(DbConstants.DB_PASSWORD_KEY));
		LOG.info("Database connected");
	}

	public Database(File file) throws IOException {
		properties = new Properties();
		LOG.debug("Loading database properties from " + file);
		properties.load(new FileInputStream(file));
	}

	public void run() throws ClassNotFoundException, SQLException, ApplicationException {
		LOG.info("Running");
		Class.forName(properties.getProperty(DbConstants.DB_DRIVER_KEY));
		LOG.info("Driver loaded");
		connect();
	}

	public static void shutdown() {
		LOG.debug("Shutting down");
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}

		try {
			connect();
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}

		return connection;
	}

	public boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}

		return false;
	}

	public static void dropTable(Statement statement, String table) throws SQLException {
		if (DbUtil.tableExists(connection, table)) {
			statement.executeUpdate(String.format("drop table %s", table));
			LOG.debug(String.format("drop table %s", table));
		}

	}

}
