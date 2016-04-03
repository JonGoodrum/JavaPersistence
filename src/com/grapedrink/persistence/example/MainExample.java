package com.grapedrink.persistence.example;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.grapedrink.persistence.mysql.connection.Database;
import com.grapedrink.persistence.mysql.connection.MySQLConnection;
import com.grapedrink.persistence.mysql.connection.TableBuilder;

public class MainExample {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {

        // The database (line 14) and credentials (lines 19/20) specified in persistence.xml.
        String databaseName = "sandbox";
        String username = "sandbox";
        String password = "sandbox";
        String host = "localhost";

        // A table which will be created and deleted during the course of this example.
        // This is the @Table annotation in ExampleEmployeeEntity.java (line 13).
        String temporaryTableName = "EMPLOYEE_TABLE_78Ggd782gKd3L82dHh7";

        // Create a MySQL connection.
        MySQLConnection connection = new MySQLConnection(host, username, password);

        /*
         * Create a table for storing entities (aka table rows). Note: if the following property is set in your persistence.xml file: (currently set at ln. 23)
         * 
         * <property name="hibernate.hbm2ddl.auto" value="update"/>
         * 
         * ...then hibernate will automatically generate a table capable of storing the entities.
         */
        example_createTable(connection, databaseName, temporaryTableName);

        // Create an ExampleEmployeeDAO for accessing the Employee table.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExampleEmployeeDatabase");
        EntityManager em = emf.createEntityManager();
        ExampleEmployeeDAO employeeDao = new ExampleEmployeeDAO(em);

        // Create some employees
        ExampleEmployeeEntity sarah = new ExampleEmployeeEntity("Sarah Johnson", 250000);
        ExampleEmployeeEntity boots = new ExampleEmployeeEntity("Boots O'Neal", 44000);

        // Persist these employees in the Employee table
        example_persistEntity(employeeDao, sarah);
        example_persistEntity(employeeDao, boots);

        // Show the contents of the table
        example_printAllEntities(employeeDao);

        // Delete an entity from the table
        example_deleteEntity(employeeDao, boots);

        // Show the contents of the table
        example_printAllEntities(employeeDao);

        // Close resources (to prevent memory leaks and deadlock)
        employeeDao.closeEntityManager();
        emf.close();

        // Delete the table
        example_deleteTable(connection, databaseName, temporaryTableName);

        // Close resources (to prevent memory leaks and deadlock)
        connection.close();

        System.out.println("***DONE***");
    }

    private static void example_createTable(MySQLConnection connection, String databaseName, String temporaryTableName) throws SQLException {

        // Get the database
        Database database = connection.getDatabase(databaseName);

        // Create a TableBuilder object. TableBuilder is simply a helper object
        // for creating the "CREATE TABLE tableName(with-these-properties)" statement.
        TableBuilder employeeTable = new TableBuilder(temporaryTableName).withColumn("EMPLOYEE_ID", "INT(11)", null, true, true, true, true)
                .withColumn("EMPLOYEE_NAME", "VARCHAR(256)", null).withColumn("SALARY", "INT(11)", null);

        // Create the table, from the TableBuilder object.
        database.createTable(employeeTable);
        System.out.println(String.format("Created table %s", temporaryTableName));

    }

    private static void example_deleteTable(MySQLConnection connection, String databaseName, String temporaryTableName) throws SQLException {

        // Get the database
        Database database = connection.getDatabase(databaseName);

        // drop the table
        database.dropTable(temporaryTableName);
        System.out.println(String.format("\nDeleted table %s", temporaryTableName));

    }

    private static void example_persistEntity(ExampleEmployeeDAO employeeDao, ExampleEmployeeEntity entity) {
        // Persist (save) employees into the database
        employeeDao.beginTransaction();
        employeeDao.createEmployee(entity);
        employeeDao.commitTransaction();
        System.out.println(String.format("Created entity \"%s\"", entity.getEmployeeName()));
    }

    private static void example_printAllEntities(ExampleEmployeeDAO employeeDao) {
        System.out.println("\nAll employees in table:");
        for (ExampleEmployeeEntity name : employeeDao.getEverything()) {
            System.out.println(String.format("    %s", name.toString()));
        }
    }

    private static void example_deleteEntity(ExampleEmployeeDAO employeeDao, ExampleEmployeeEntity entity) {
        // Delete employee from the database
        employeeDao.beginTransaction();
        employeeDao.deleteEmployee(entity);
        employeeDao.commitTransaction();
        System.out.println(String.format("\nDeleted entity \"%s\"", entity.getEmployeeName()));
    }
}