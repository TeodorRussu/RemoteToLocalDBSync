package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class used to laod properties from the configurations file
 */
public class PropertiesDispatcher {

    private static PropertiesDispatcher instance;
    private Properties remoteDBProperties;
    private Properties localDBProperties;

    /**
     * singleton getter
     * @return PropertiesDispatcher instance
     */
    public static PropertiesDispatcher getInstance(){
        if (instance == null) {
            instance = new PropertiesDispatcher();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private PropertiesDispatcher(){

        remoteDBProperties = loadPropertiesFromfile("remoteDBConfig.properties");
        localDBProperties = loadPropertiesFromfile("localDBConfig.properties");
    }

    private Properties loadPropertiesFromfile(String filename) {
        Properties properties = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(filename);
            properties.load(input);
            Console.log(this, filename + " properties loaded");
        } catch (IOException exception) {
            Console.log(this, exception.getMessage());
        }
        return properties;
    }

    /**
     * @return the remote db loaded properties
     */
    public Properties getRemoteDBProperties() {
        return remoteDBProperties;
    }

    /**
     * @return the local db loaded properties
     */
    public Properties getLocalDBProperties() {
        return localDBProperties;
    }
}
