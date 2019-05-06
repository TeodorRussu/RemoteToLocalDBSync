package dao;

import utils.Console;
import utils.PropertiesDispatcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * LocalConnector class, used to initialize and provide a SQL connection
 */
public class LocalConnector implements Connector {

    private String dbPath;
    private static Connection connection;

    /**
     * Constructor
     */
    LocalConnector() {
        Properties properties = PropertiesDispatcher.getInstance().getLocalDBProperties();
        dbPath = properties.getProperty("LocalDB_Path");
        Console.log(this, "Local db initialised");
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            Console.log(this, "init local DB connection");
            try {
                // db parameters
                String url = "jdbc:sqlite:" + dbPath;
                // create a connection to the database
                connection = DriverManager.getConnection(url);
                Console.log(this, "Connection to local DB has been established.");
            } catch (SQLException e) {
                Console.error(this, e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Console.log(this, e.getMessage());
            }
        }
    }

    @Override
    public String getPrefix() {
        return null;
    }

}




