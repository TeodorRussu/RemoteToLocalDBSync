package dao;

import java.sql.*;
import java.util.Properties;

import utils.*;

public class RemoteConnector implements Connector {
    private String hostname;
    private String username;
    private String password;
    private String database;
    private String port;
    private String prefix;
    private Connection connection;

    /**
     * Constructor
     */
    RemoteConnector(String id) {
        Properties properties = PropertiesDispatcher.getInstance().getRemoteDBProperties();

        this.hostname = properties.getProperty(id + "_DB_HOSTNAME");
        this.username = properties.getProperty(id + "_DB_USERNAME");
        this.password = properties.getProperty(id + "_DB_PASSWORD");
        this.database = properties.getProperty(id + "_DB_DATABASE");
        this.port = properties.getProperty(id + "_DB_PORT");
        this.prefix = properties.getProperty(id + "_DB_PREFIX");
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public Connection getConnection() {
        Console.log(this, "Init connection to " + hostname);

        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Console.error(this, e.getMessage());
            }

            String dbUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
            try {
                connection = DriverManager.getConnection(dbUrl, username, password);

            } catch (SQLException e) {
                Console.error(this, e.getMessage());
            }
        }

        Console.log(this, "Connection to " + hostname + " DB has been established.");
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
}
