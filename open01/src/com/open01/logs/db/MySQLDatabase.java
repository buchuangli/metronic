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

/**
 *
 * Superclass for all relational database.
 *
 * Provides facade-style services for querying and updating, leaving the
 * processing of the result sets to the subclasses through the use of inner
 * abstract classes.
 *
 * @see RDatabase
 * @author Chen Li
 *
 */
class MySQLDatabase extends RDatabase {
	private String databaseName;

	protected MySQLDatabase(String resourceName) {
		super(resourceName);

		// get database name
		if (databaseName == null || databaseName.equals("")) {
			synchronized (MySQLDatabase.class) {
				this.databaseName = super
						.getDatabaseName("SELECT DATABASE() AS dbname;");
			}
		}
	}

	String getDatabaseName() {
		return this.databaseName;

	}

	@Override
	protected void executeQuery(String sql,
			ResultSetProcessor resultSetProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeQuery(sql, resultSetProcessor);

		}
	}

	@Override
	protected void executeQuery(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeQuery(sql, statementProcessor, resultSetProcessor);

		}
	}

	@Override
	protected void executeUpdate(String sql) {

		synchronized (MySQLDatabase.class) {

			super.executeUpdate(sql);

		}
	}

	@Override
	protected void executeUpdate(String sql,
			PreparedStatementProcessor statementProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeUpdate(sql, statementProcessor);

		}
	}

	@Override
	protected void executeUpdate(String sql,
			ResultSetProcessor resultSetProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeUpdate(sql, resultSetProcessor);

		}
	}

	@Override
	protected void executeUpdate(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeUpdate(sql, statementProcessor, resultSetProcessor);

		}
	}

	@Override
	protected void executeBatch(String sql,
			PreparedStatementProcessor statementProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeBatch(sql, statementProcessor);

		}
	}

	@Override
	protected void executeBatch(String sql,
			PreparedStatementProcessor statementProcessor,
			ResultSetProcessor resultSetProcessor) {

		synchronized (MySQLDatabase.class) {

			super.executeBatch(sql, statementProcessor, resultSetProcessor);

		}
	}
}
