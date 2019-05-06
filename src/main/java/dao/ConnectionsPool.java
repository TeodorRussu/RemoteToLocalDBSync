package dao;

import org.springframework.stereotype.Component;
import utils.PropertiesDispatcher;

import java.util.*;

/**
 * The class will create and return connections for databases
 */
@Component
public class ConnectionsPool {

    private List<Connector> remoteConnections = new ArrayList<>();
    private Set<String> ids = new HashSet<>();

    /**
     * Constructor
     */
    public ConnectionsPool() {
        extractConnectionsIDs();
        createRemoteConnections();
    }

    /**
     * field getter
     *
     * @return List<Connector> remoteConnections
     */
    public List<Connector> getRemoteConnections() {
        return remoteConnections;
    }

    /**
     * Helper method. Will fill the set with db's ids
     */
    private void extractConnectionsIDs() {
        Properties properties = PropertiesDispatcher.getInstance().getRemoteDBProperties();
        for (Object o : properties.keySet()) {
            String key = o.toString();
            int prefIndex = key.indexOf("_");
            String id = key.substring(0, prefIndex);
            ids.add(id);
        }
    }

    /**
     * Create Connections for remote db's.
     */
    private void createRemoteConnections() {
        for (String id : ids) {
            remoteConnections.add(new RemoteConnector(id));
        }
    }

    /**
     * Create and return Connection for local db.
     *
     * @return local db Connection
     */
    public Connector getLocalConnection() {
        return new LocalConnector();
    }

}
