package com.grapedrink.persistence.mysql.connection;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {

    private String name;
    private List<Column> columns;

    /**
     * Creates a SQL Statement used to create tables
     * 
     * @param tableName
     *            name of table
     */
    public TableBuilder(String tableName) {
        this.name = tableName;
        this.columns = new ArrayList<>();
    }

    /**
     * Creates a column definition for a nullable, non-unique, non-primary-key column.
     * 
     * @param name
     * @param type
     * @param defaultValue
     *            (can be null)
     * @return this instance of the class
     */
    public TableBuilder withColumn(String name, String type, String defaultValue) {
        this.addColumn(name, type, defaultValue, false, false, false, false);
        return this;
    }

    /**
     * Creates a column definition for a nullable, non-unique, non-primary-key column.
     * 
     * @param name
     * @param type
     * @param defaultValue
     *            (can be null)
     * @return this instance of the class
     */
    public void addColumn(String name, String type, String defaultValue) {
        this.addColumn(name, type, defaultValue, false, false, false, false);
    }

    /**
     * Same as addColumn, but returns a reference to this (for concurrent programming styles)
     * 
     * @param name
     *            Column name (must be unique across this class)
     * @param type
     *            DataType of column
     * @param size
     *            size of DataType (has no affect for types where size is not user-defined, such as DATE)
     * @param defaultValue
     *            Default value of this column when a row is added. Null represents no default value. Values are interpreted as plaintext, so the string "43"
     *            represents the number 43 and the string "'Puppy!'" represents the string 'Puppy!'.
     * @param isNotNull
     *            whether or not the column is nullable
     * @param isAutoIncrement
     *            Whether or not the column increments itself when a new row is added. Only valid for one column per table, and numeric data types.
     * @param isPrimaryKey
     *            Whether or not the column is the table's primary key. Only valid for one column per table.
     * @param hasUniqueValues
     *            Whether or not the column's values are unique in this table.
     * @return this instance of the class
     */
    public TableBuilder withColumn(String name, String type, String defaultValue, boolean isNotNull, boolean isAutoIncrement, boolean isPrimaryKey,
            boolean hasUniqueValues) {
        this.addColumn(name, type, defaultValue, isNotNull, isAutoIncrement, isPrimaryKey, hasUniqueValues);
        return this;
    }

    /**
     * Adds a column to this table, with the specified parameters
     * 
     * @param name
     *            Column name (must be unique across this class)
     * @param type
     *            DataType of column
     * @param size
     *            size of DataType (has no affect for types where size is not user-defined, such as DATE)
     * @param defaultValue
     *            Default value of this column when a row is added. Null represents no default value. Values are interpreted as plaintext, so the string "43"
     *            represents the number 43 and the string "'Puppy!'" represents the string 'Puppy!'.
     * @param isNotNull
     *            whether or not the column is nullable
     * @param isAutoIncrement
     *            Whether or not the column increments itself when a new row is added. Only valid for one column per table, and numeric data types.
     * @param isPrimaryKey
     *            Whether or not the column is the table's primary key. Only valid for one column per table.
     * @param hasUniqueValues
     *            Whether or not the column's values are unique in this table.
     */
    public void addColumn(String name, String type, String defaultValue, boolean isNotNull, boolean isAutoIncrement, boolean isPrimaryKey,
            boolean hasUniqueValues) {
        columns.add(new Column(name, type, defaultValue, isNotNull, isAutoIncrement, isPrimaryKey, hasUniqueValues));
    }

    /**
     * Get the name of this table
     * 
     * @return name of this table
     */
    public String getName() {
        return this.name;
    }

    public String getCreateStatement(String parentDatabaseName) {
        return String.format("CREATE TABLE %s.%s(%s)", parentDatabaseName, name, getTableArgs());
    }

    private String getTableArgs() {
        StringBuilder args = new StringBuilder();
        String comma = "";
        for (Column column : columns) {
            args.append(comma);
            comma = ", ";
            args.append(column.toString());
        }
        return args.toString();
    }

    private class Column {
        private String name;
        private String dataType;
        private String notNull;
        private String defaultValue;
        private String primaryKey;
        private String unique;
        private String autoIncrement;

        public Column(String name, String dataType, String defaultValue, boolean isNotNull, boolean isAutoIncrement, boolean isPrimaryKey,
                boolean hasUniqueValues) {
            this.name = name;
            this.dataType = dataType;
            this.notNull = isNotNull ? "NOT NULL" : "";
            this.primaryKey = isPrimaryKey ? "PRIMARY KEY" : "";
            this.unique = hasUniqueValues ? "UNIQUE" : "";
            this.defaultValue = defaultValue == null ? "" : String.format("DEFAULT %s", defaultValue);
            this.autoIncrement = isAutoIncrement ? "AUTO_INCREMENT" : "";
        }

        @Override
        public String toString() {
            return String.format("%s %s %s %s %s %s %s", name, dataType, notNull, autoIncrement, primaryKey, unique, defaultValue).trim()
                    .replaceAll("\\s+", " ");
        }

    }
}
