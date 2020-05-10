package a01038582.books2.data.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class DbUtil {

	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
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

	public static ResultSet executeQuery(Statement statement, String sql) throws SQLException {
		System.out.println("Ready to query: " + sql);
		ResultSet resultSet = statement.executeQuery(sql);
		return resultSet;
	}

	public static ResultSet executeQueryForCustomerId(Statement statement) throws SQLException {
		String sql = "SELECT customerId FROM Customer_A01038582";
		ResultSet resultSet = statement.executeQuery(sql);
		return resultSet;
	}

}