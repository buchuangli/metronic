/*
 * Open01 Inc.
 *
 * This is the code of Open01 Log Analysis System
 * Copyright (C) Open01 Inc. 2016
 * 
 * All rights reserved.
 * 
 */

package com.open01.logs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * Superclass for all relational database.
 *
 * Provides facade-style services for querying and updating, leaving the
 * processing of the result sets to the subclasses through the use of inner
 * abstract classes.
 *
 * @see MySQLDatabase
 * @author Chen Li
 *
 */
class RDatabase {
	private Logger logger = Logger.getLogger(RDatabase.class.getName());

	private DataSource dataSource;

	/**
	 * Default constructor, parameters will be specified while descendant
	 * classes inheritating.
	 * 
	 * @param resourceName
	 *            The unique name of the resource defined in context.xml.
	 */
	protected RDatabase(String resourceName) {

		try {
			Context iniContext = new InitialContext();
			Context envContext = (Context) iniContext.lookup("java:/comp/env");
			this.dataSource = (DataSource) envContext.lookup(resourceName);
			if (this.dataSource == null) {
				throw new RuntimeException("`" + resourceName
						+ "' is an unknown DataSource");
			}

		} catch (NamingException e) {

			e.printStackTrace();

			logger.severe("Database Pool initialization error: ".concat(e
					.getMessage()));
			throw new RuntimeException(e);
		}
	}

	/**
	 * get database name.
	 */
	String getDatabaseName(String sql) {

		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		executeQuery(sql, result_set_processor_1);
		return result_set_processor_1.dbName;

	}

	/**
	 * Establish database connection.
	 */
	private synchronized Connection openConnection() {

		try {

			// set log writer
			dataSource.setLogWriter(new java.io.PrintWriter(
					new java.io.OutputStreamWriter(System.out)));

			return dataSource.getConnection();

		} catch (SQLException e) {

			logger.severe("Failed to open database connection: ".concat(e
					.getMessage()));

			throw new RuntimeException(e);
		}
	}

	/**
	 * Close database connection. If database pool adopted, it will
	 * automatically reused when new request comes.
	 * 
	 * @param connection
	 *            The connection which is gonne be closed.
	 */
	protected void closeConnection(Connection connection) {

		try {
			if ((connection != null) && (!connection.isClosed())) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {

			logger.severe("Failed to close database connection: ".concat(e
					.getMessage()));

			throw new RuntimeException(e);

		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				logger.severe("Failed to close database connection: ".concat(e
						.getMessage()));
				throw new RuntimeException(e);
			}
		}
	}

	protected void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			logger.severe("Failed to close Statement: ".concat(e.getMessage()));
			throw new RuntimeException(e);
		} finally {
			try {
				if (statement != null) {
					statement = null;
				}
			} catch (Exception e) {
				logger.severe("Failed to close Statement: ".concat(e
						.getMessage()));
				throw new RuntimeException(e);
			}
		}

	}

	protected void closeResultSet(java.sql.ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {

			logger.severe("Failed to close ResultSet: ".concat(e.getMessage()));
			throw new RuntimeException(e);

		} finally {
			try {
				if (resultSet != null) {
					resultSet = null;
				}
			} catch (Exception e) {
				logger.severe("Failed to close ResultSet: ".concat(e
						.getMessage()));
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Process result set after SQL executed.
	 */
	protected abstract class ResultSetProcessor {

		protected abstract void process(ResultSet resultSet)
				throws SQLException, Exception;
	}

	/**
	 * Prepared statement process
	 */
	protected abstract class PreparedStatementProcessor {

		protected abstract void process(PreparedStatement statement)
				throws SQLException;
	}

	/**
	 * Simple query method for execute SQL. Useful for SQL without external
	 * parameters.
	 */
	protected void executeQuery(String sql,
			ResultSetProcessor resultSetProcessor) {

		Connection connection = openConnection();

		try {
			executeQuery(connection, sql, resultSetProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeQuery(Connection connection, String sql,
			ResultSetProcessor resultSetProcessor) {

		Statement statement = null;
		ResultSet result_set = null;

		try {
			statement = connection.createStatement();
			result_set = statement.executeQuery(sql);
			try {
				resultSetProcessor.process(result_set);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}
	}

	/**
	 * Query method for execute SQL. Useful for SQL with external parameters.
	 */
	protected void executeQuery(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		Connection connection = openConnection();

		try {
			executeQuery(connection, sql, statementProcessor,
					resultSetProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeQuery(Connection connection, String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {
		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {
			statement = connection.prepareStatement(sql);
			statementProcessor.process(statement);
			result_set = statement.executeQuery();
			try {
				resultSetProcessor.process(result_set);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}

	}

	/**
	 * Simple method for execute update SQL (such as insert, update or delete)
	 * without result set. Useful for SQL without external parametres.
	 */
	protected void executeUpdate(String sql) {

		Connection connection = openConnection();

		try {
			executeUpdate(connection, sql);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeUpdate(Connection connection, String sql) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeStatement(statement);
		}

	}

	/**
	 * Update method for execute SQL. Useful for SQL with external parameters.
	 */
	protected void executeUpdate(String sql,
			PreparedStatementProcessor statementProcessor) {

		Connection connection = openConnection();

		try {
			executeUpdate(connection, sql, statementProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeUpdate(Connection connection, String sql,
			PreparedStatementProcessor statementProcessor) {

		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statementProcessor.process(statement);
			statement.executeUpdate();
			result_set = statement.getGeneratedKeys();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}

	}

	/**
	 * Simple method for execute update SQL (such as insert, update or delete)
	 * with result set. Useful for SQL without external parameters.
	 */
	protected void executeUpdate(String sql,
			ResultSetProcessor resultSetProcessor) {

		Connection connection = openConnection();

		try {
			executeUpdate(connection, sql, resultSetProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeUpdate(Connection connection, String sql,
			ResultSetProcessor resultSetProcessor) {
		Statement statement = null;
		ResultSet result_set = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			result_set = statement.getGeneratedKeys();
			try {
				resultSetProcessor.process(result_set);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}
	}

	/**
	 * Update method for execute SQL. Useful for SQL with external parameters.
	 */
	protected void executeUpdate(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		Connection connection = openConnection();

		try {
			executeUpdate(connection, sql, statementProcessor,
					resultSetProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	private void executeUpdate(Connection connection, String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		PreparedStatement statement = null;
		ResultSet result_set = null;

		try {
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statementProcessor.process(statement);
			statement.executeUpdate();
			result_set = statement.getGeneratedKeys();
			try {
				resultSetProcessor.process(result_set);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}

	}

	/**
	 * Batch update method for execute SQL. Useful for SQL with external
	 * parameters.
	 */
	protected void executeBatch(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		Connection connection = openConnection();

		try {
			executeBatch(connection, sql, statementProcessor,
					resultSetProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeBatch(Connection connection, String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		PreparedStatement statement = null;
		ResultSet result_set = null;
		try {
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			statementProcessor.process(statement);
			statement.executeBatch();
			result_set = statement.getGeneratedKeys();
			try {
				resultSetProcessor.process(result_set);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResultSet(result_set);
			closeStatement(statement);
		}

	}

	/**
	 * Update method for execute SQL. Useful for SQL with external parametres.
	 */
	protected void executeBatch(String sql,
			PreparedStatementProcessor statementProcessor) {

		Connection connection = openConnection();

		try {
			executeBatch(connection, sql, statementProcessor);
		} finally {
			closeConnection(connection);
		}
	}

	//
	//
	//
	//
	//

	private void executeBatch(Connection connection, String sql,
			PreparedStatementProcessor statementProcessor) {

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);

			statementProcessor.process(statement);
			statement.executeBatch();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeStatement(statement);
		}

	}

	//
	//
	//
	//
	//

	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private String dbName;

		protected void process(ResultSet resultSet) throws SQLException {

			if (resultSet.next()) {

				dbName = resultSet.getString("dbname");
			}
		}
	}
}
