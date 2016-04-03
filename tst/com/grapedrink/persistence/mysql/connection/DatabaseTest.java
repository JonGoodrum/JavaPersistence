package com.grapedrink.persistence.mysql.connection;

import static com.grapedrink.util.TestingCredentials.HOST;
import static com.grapedrink.util.TestingCredentials.PASS;
import static com.grapedrink.util.TestingCredentials.USER;
import static com.grapedrink.util.TestingCredentials.IS_ADMIN;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

    private MySQLConnection connection;
    private Database testDatabase;
    private String testDatabaseName;

    @Before
    public void getConnectionTest() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        this.connection = new MySQLConnection(HOST, USER, PASS);
        this.testDatabaseName = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        this.testDatabase = connection.createDatabase(testDatabaseName);
    }

    @After
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void createAndDeleteTableTest() throws SQLException {
        Assume.assumeTrue(IS_ADMIN);
        String testTableName = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        TableBuilder tableBuilder = new TableBuilder(testTableName).withColumn("ID", "INT(11)", null);
        testDatabase.createTable(tableBuilder);
        testDatabase.dropTable(testTableName);
    }
}
