package com.revature.util;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ConnectionUtil class follows the singleton pattern, ensuring that 
 * only one instance of the ConnectionUtil object exists throughout the 
 * application. This class encapsulates the connection details such as 
 * username, password, and URL required for establishing database 
 * connections. It provides methods for configuring the connection 
 * parameters and obtaining a connection to the database.
 * 
 * The constructor is private to prevent instantiation from outside 
 * the class. The static method getInstance() provides a way to access 
 * the singleton instance, while the configure() method sets up the 
 * necessary parameters for the connection. It is important to call 
 * configure() only once to ensure the singleton is properly initialized.
 */

public class ConnectionUtil {

    /**
     * The single instance of ConnectionUtil.
     */
    private static ConnectionUtil instance = null;

    /**
     * The username for the database connection.
     */
    @SuppressWarnings("unused")
    private String username;

    /**
     * The password for the database connection.
     */
    @SuppressWarnings("unused")
    private String password;

    /**
     * The URL for the database connection.
     */
    @SuppressWarnings("unused")
    private String url;

    /**
     * Private no-args constructor to prevent external instantiation.
     */
    private ConnectionUtil() {
        // Private constructor
        super();
    }

    /**
     * Provides access to the singleton instance of ConnectionUtil.
     *
     * @return the singleton instance of ConnectionUtil
     */

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return instance;
    }

    /**
     * Configures the ConnectionUtil instance with the specified connection values.
     *
     * @param username the username for the database connection
     * @param password the password for the database connection
     * @param url the URL for the database connection
     * @param driver the database driver to be used
     * @return the current instance of ConnectionUtil
     * @throws SQLException if there is an error during configuration
     */

    public ConnectionUtil configure(String username, String password, String url, Driver driver) throws SQLException {
        this.username = username;
        this.password = password;
        this.url = url;
        // Additional configuration logic here, if needed
        DriverManager.registerDriver(driver);
        return this;
    }

    /**
     * Obtains a connection to the database using the configured parameters.
     *
     * @return a Connection object to the database
     * @throws SQLException if there is an error obtaining the connection
     */
    
    public Connection getConnection() throws SQLException {
        // Logic to create and return a database connection
        // Example: return DriverManager.getConnection(url, username, password);
       // return null;
        return DriverManager.getConnection(url, username, password);
    }
}

