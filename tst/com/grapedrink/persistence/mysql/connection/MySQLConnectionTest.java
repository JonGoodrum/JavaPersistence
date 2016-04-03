package com.grapedrink.persistence.mysql.connection;

import static org.junit.Assert.fail;
import static com.grapedrink.util.TestingCredentials.HOST;
import static com.grapedrink.util.TestingCredentials.USER;
import static com.grapedrink.util.TestingCredentials.PASS;
import static com.grapedrink.util.TestingCredentials.IS_ADMIN;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.grapedrink.persistence.mysql.connection.MySQLConnection;

public class MySQLConnectionTest {

    private static final String INFORMATION_SCHEMA = "information_schema";

    private MySQLConnection connection;

    @Before
    public void getConnectionTest() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
        this.connection = new MySQLConnection(HOST, USER, PASS);
    }

    @After
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void createAndDeleteValidDatabaseTest() throws SQLException {
        Assume.assumeTrue(IS_ADMIN);
        String randomDatabaseName = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        this.connection.createDatabase(randomDatabaseName);
        this.connection.dropDatabaseIfExists(randomDatabaseName);
    }

    @Test
    public void getDatabaseTest() throws SQLException {
        this.connection.getDatabase(INFORMATION_SCHEMA);
    }

    @Test
    public void getDatabasesTest() throws SQLException {
        if (!this.connection.getDatabaseNames().contains(INFORMATION_SCHEMA)) {
            fail(String.format("getDatabasesTest failed (could not find %s)", INFORMATION_SCHEMA));
        }
    }
}
