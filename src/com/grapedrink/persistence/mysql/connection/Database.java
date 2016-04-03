package com.grapedrink.persistence.mysql.connection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String databaseName;
    private MySQLConnection connection;
    private static final String ALL_TABLES = "%";

    private class Statements {
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s.%s";
    }

    protected Database(MySQLConnection databaseConnection, String databaseName) {
        this.databaseName = databaseName;
        this.connection = databaseConnection;
    }

    public List<String> getTableNames() throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet res = meta.getTables(databaseName, null, ALL_TABLES, null);
        while (res.next()) {
            tables.add(res.getString("TABLE_NAME"));
        }
        res.close();
        return tables;
    }

    public void createTable(TableBuilder table) throws SQLException {
        connection.executeStatement(table.getCreateStatement(this.databaseName));
    }

    public void dropTable(String tableName) throws SQLException {
        connection.executeStatement(String.format(Statements.DROP_TABLE, databaseName, tableName));
    }

    /**
     * Executes an arbitrary SQL statement. Not specific to this database.
     * 
     * @param statement
     *            statement to execute
     * @throws SQLException
     */
    public void executeStatement(String statement) throws SQLException {
        connection.executeStatement(statement);
    }
}
