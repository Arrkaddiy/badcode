package ru.liga.intership.badcode.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private static final String APPLICATION_PROPERTIES = "src/main/resources/application.properties";

    private static PropertiesReader propertiesReader;
    private static Properties properties;

    private PropertiesReader() {
        PropertiesReader.load();
    }

    public static Properties getProperties() {
        if (propertiesReader == null) {
            propertiesReader = new PropertiesReader();
        }

        return properties;
    }

    private static void load() {
        properties = new Properties();

        try {
            properties.load(new FileInputStream(APPLICATION_PROPERTIES));

        } catch (IOException ioe) {
            ioe.getMessage();
            ioe.printStackTrace();
        }
    }
}
