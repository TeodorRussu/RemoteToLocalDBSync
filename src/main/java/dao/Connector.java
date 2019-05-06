package dao;

import java.sql.Connection;

public interface Connector {

    Connection getConnection();

    void closeConnection();

    String getPrefix();
}
