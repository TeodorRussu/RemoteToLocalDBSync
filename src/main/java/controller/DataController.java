package controller;

import dao.ConnectionsPool;
import dao.Connector;
import entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.Console;
import utils.SQLStatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data controller class.
 * All the data manipulations processed between db, will start here
 */
@Component
public class DataController extends DBSyncAbstractEngine {

    private Connector localDB;
    private ConnectionsPool connectionPool;
    private DataProcessor dataProcessor;

    @Autowired
    public void setLocalDB(Connector localDB) {
        this.localDB = localDB;
    }
    @Autowired
    public void setConnectionPool(ConnectionsPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    @Autowired
    public void setDataProcessor(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public void updateLocalDB() {
        for (Connector connector : connectionPool.getRemoteConnections()) {
            uploadNewClientsFromRemoteDB(connector);
            connector.closeConnection();
        }
        Console.log(this, " All new Clients from remote DBs were added to locl DB");
        localDB.closeConnection();
    }

    /**
     * Method used to load customer details into local DB     *
     *
     * @param client Client
     */
    private void addNewCustomersToLocalDB(Client client) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = localDB.getConnection().prepareStatement(SQLStatements.SIMPLE_INSERT_TO_CLIENTI);

            // set the corresponding param
            preparedStatement.setString(1, client.getDenumire());
            preparedStatement.setString(2, client.getAdresa());
            preparedStatement.setString(3, client.getLocalitate());
            preparedStatement.setString(4, client.getJudet());
            preparedStatement.setString(5, client.getTara());

            preparedStatement.executeUpdate();
            Console.log(this, client + " imported to local DB");
        } catch (SQLException exception) {
            Console.log(this, exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void uploadNewClientsFromRemoteDB(Connector connector) {
        try {
            Statement statement;
            statement = connector.getConnection().createStatement();
            String query = String.format(SQLStatements.SELECT_CLIENTS_FROM_REMOTE, connector.getPrefix() + "order");
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String payment_firstname = resultSet.getString("payment_firstname");
                String payment_lastname = resultSet.getString("payment_lastname");
                String payment_company = resultSet.getString("payment_company");
                String payment_address_1 = resultSet.getString("payment_address_1");
                String payment_address_2 = resultSet.getString("payment_address_2");
                String payment_city = resultSet.getString("payment_city");
                String payment_postcode = resultSet.getString("payment_postcode");
                String payment_zone = resultSet.getString("payment_zone");
                String payment_country = resultSet.getString("payment_country");
                Client client = new Client(payment_firstname, payment_lastname, payment_company, payment_address_1, payment_address_2, payment_city, payment_postcode, payment_zone, payment_country);

                if (dataProcessor.checkIfIsNewAndFromEU(client)) {
                    addNewCustomersToLocalDB(client);
                }
            }
        } catch (SQLException exception) {
            Console.log(this, exception.getMessage());
            exception.printStackTrace();
        }
    }
}
