package com.grapedrink.persistence.mysql.connection;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {

    private Connection connection;
    private Statement statement;
    private static final String JDBC_SCHEME = "jdbc:mysql://";
    private static final String LOCALHOST = "localhost";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private class Statements {
        private static final String CREATE_DATABASE = "CREATE DATABASE %s";
        private static final String DROP_DATABASE_IF_EXISTS = "DROP DATABASE IF EXISTS %s";
    }

    /**
     * Creates a connection to the MySQL database on localhost.
     * 
     * @param user
     *            MySQL username
     * @param password
     *            password
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public MySQLConnection(String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        this(LOCALHOST, user, password);
    }

    /**
     * Creates a connection to a MySQL instance on the host machine.
     * 
     * @param host
     *            host you wish to connect to (ex: 127.0.0.1)
     * @param user
     *            MySQL username
     * @param password
     *            password
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public MySQLConnection(String host, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            SQLException {
        Class.forName(MYSQL_DRIVER).newInstance();
        this.connection = DriverManager.getConnection(JDBC_SCHEME + host, user, password);
        this.statement = connection.createStatement();
    }

    /**
     * Returns a list of databases available to this user.
     * 
     * @return database names
     * @throws SQLException
     */
    public List<String> getDatabaseNames() throws SQLException {
        List<String> databases = new ArrayList<>();
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getCatalogs();
        while (res.next()) {
            databases.add(res.getString("TABLE_CAT"));
        }
        res.close();
        return databases;
    }

    /**
     * Creates a database with the given name.
     * 
     * @param databaseName
     *            name
     * @return created Database
     * @throws SQLException
     */
    public Database createDatabase(String databaseName) throws SQLException {
        databaseName = databaseName.trim();
        String createDatabase = String.format(Statements.CREATE_DATABASE, databaseName);
        statement.executeUpdate(createDatabase);
        return getDatabase(databaseName);
    }

    /**
     * Drops the database with the given name.
     * 
     * @param databaseName
     *            name
     * @throws SQLException
     */
    public void dropDatabaseIfExists(String databaseName) throws SQLException {
        databaseName = databaseName.trim();
        String drop = String.format(Statements.DROP_DATABASE_IF_EXISTS, databaseName);
        statement.executeUpdate(drop);
    }

    /**
     * Executes an arbitrary SQL statement.
     * 
     * @param statement
     *            statement to execute
     * @throws SQLException
     */
    public void executeStatement(String statement) throws SQLException {
        this.statement.executeUpdate(statement);
    }

    /**
     * Returns a Database object with CRUD actions
     * 
     * @param databaseName
     *            name
     * @return a Database object
     * @throws SQLException
     */
    public Database getDatabase(String databaseName) throws SQLException {
        if (getDatabaseNames().contains(databaseName)) {
            return new Database(this, databaseName);
        }
        return null;
    }

    /**
     * Returns the metadata (schema, tables, etc) correlating to this connection.
     * 
     * @return metadata object
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    /**
     * Closes the connection to prevent memory leaks.
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
