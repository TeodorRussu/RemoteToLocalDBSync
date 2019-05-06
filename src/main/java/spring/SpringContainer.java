package spring;

import controller.DataController;
import controller.DataProcessor;
import dao.ConnectionsPool;
import dao.Connector;
import entity.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.Console;
import utils.SQLStatements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SpringContainer {

    @Bean
    public ConnectionsPool connectionPool(){
        return new ConnectionsPool();
    }

    @Bean
    public DataController dataController(){
        return new DataController();
    }

    @Bean
    public Connector localDB(@Value("#{connectionPool}")ConnectionsPool connectionPool){
        return connectionPool.getLocalConnection();
    }

    @Bean
    public DataProcessor dataProcessor(@Value("#{localClientsAsSet}")Set<Client> localClientsAsSet){
        return new DataProcessor(localClientsAsSet);
    }

    @Bean("localClientsAsSet")
    public Set<Client> getClientsFromLocalDB(@Value("#{localDB}") Connector localDB) {
        Set<Client> clientSet = new HashSet<>();
        try {
            Statement statement;
            statement = localDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SQLStatements.SELECT_CLIENTS_FROM_LOCAL);

            while (resultSet.next()) {
                String denumire = resultSet.getString("denumire");
                String adresa = resultSet.getString("adresa");
                String judet = resultSet.getString("judet");
                String localitate = resultSet.getString("localitate");
                String tara = resultSet.getString("tara");

                Client client = new Client(denumire, adresa, localitate, judet, tara);
                clientSet.add(client);

            }
        } catch (SQLException exception) {
            Console.log(this, exception.getMessage());
            exception.printStackTrace();
        }
        Console.log(this, "Current clients retrieved from local DB.");
        return clientSet;
    }
}
