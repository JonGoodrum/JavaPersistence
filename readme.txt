Package for managing and persisting objects in a MySQL database.

TO USE THIS PACKAGE:
    * Update persistence.xml with values pertaining to your database.
      Specifically, the following fields need to be updated:
        * The host/database (line 14).
        * The username (line 19) and password (line 20).

TO RUN MainExample.java
    * Update databaseName, username, password, and host (lines 20-23)
      to match the ones specified in your persistence.xml file.

TO RUN UNIT TESTS:
    * Update the properties of com.grapedrink.util.TestingCredentials.java.
    * Make sure to update IS_ADMIN to reflect whether or not USER
      has admin privileges.  Many of the tests (such as creating/dropping databases)
      automatically skip themselves if the user doesn't have those pivileges.

************************************************************************************
Package Layout:

{PACKAGE-ROOT}
    --> src
        --> com.grapedrink.persistence.dao
            --> AbstractDAO.java

        --> com.grapedrink.persistence.entities
            --> AbstractEntity.java

        --> com.grapedrink.persistence.mysql.connection
            --> Database.java
            --> MySQLConnection.java
            --> TableBuilder.java

        --> META-INF
            --> persistence.xml

       (Exists only to demonstrate functionality.  Entire package can be deleted.)
        --> com.grapedrink.persistence.example
            --> ExampleEmployeeDAO.java
            --> ExampleEmployeeEntity.java
            --> MainExample.java

************************************************************************************
File Definitions:

    AbstractDAO.java:
      * Template class containing basic CRUD operations for a DAO (Data Access Object)

    AbstractEntity.java:
      * Template class for an entity object

    persistence.xml:
      * Contains information (credentials, etc...) necessary for Java to connect to MySQL.

    MySQLConnection.java:
      * Connects to a MySQL instance.  Offers statement execution and CRUD capabalities.

    Database.java:
      * Represents a MySQL database.  Offers statement execution and CRUD capabalities.

    TableBuilder.java:
      * Utility class for programatically building "CREATE TABLE" statements.

************************************************************************************


