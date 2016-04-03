package com.grapedrink.persistence.mysql.connection;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableBuilderTest {

    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static final String COLUMN_1 = "COLUMN_1";
    private static final String COLUMN_2 = "COLUMN_2";
    private static final String INT_11 = "INT(11)";
    private static final String VARCHAR_256 = "VARCHAR(256)";

    @Test
    public void testCreateTable() {
        TableBuilder builder = new TableBuilder(TABLE_NAME);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME()";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameType() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11))";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameTypeDefaultValue() {
        TableBuilder builder1 = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, "69");

        TableBuilder builder2 = new TableBuilder(TABLE_NAME).withColumn(COLUMN_2, VARCHAR_256, "'PuPPY!!1!'");

        String createTable1Syntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) DEFAULT 69)";
        String createTable2Syntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_2 VARCHAR(256) DEFAULT 'PuPPY!!1!')";

        assertEquals(createTable1Syntax, builder1.getCreateStatement(DATABASE_NAME));
        assertEquals(createTable2Syntax, builder2.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameTypeNotNull() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null, true, false, false, false);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) NOT NULL)";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameTypeUnique() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null, false, false, false, true);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) UNIQUE)";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameTypePrimaryKey() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null, false, false, true, false);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) PRIMARY KEY)";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void testColumnNameTypeAutoIncrement() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null, false, true, false, false);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) AUTO_INCREMENT)";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }

    @Test
    public void createTwoColumns() {
        TableBuilder builder = new TableBuilder(TABLE_NAME).withColumn(COLUMN_1, INT_11, null, true, true, true, true).withColumn(COLUMN_2, VARCHAR_256, null);

        String createTableSyntax = "CREATE TABLE DATABASE_NAME.TABLE_NAME(COLUMN_1 INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE, "
                + "COLUMN_2 VARCHAR(256))";
        assertEquals(createTableSyntax, builder.getCreateStatement(DATABASE_NAME));
    }
}
