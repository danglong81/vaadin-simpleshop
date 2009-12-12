package com.vaadin.incubator.simpleshop.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Configurations utility class
 * 
 * @author Kim
 * 
 */
public class ConfigUtil {

    private static Properties properties = null;

    /**
     * Get the properties class
     * 
     * @return
     */
    private static Properties getProperties() {
        // Check if the properties have been initalized
        if (properties == null) {
            // Create the properties class
            properties = new Properties();
            try {
                // Read the config file
                URL configFile = ConfigUtil.class.getClassLoader().getResource(
                        "shop.properties");
                // Load the config file to the properties
                FileInputStream stream = new FileInputStream(configFile
                        .getFile());
                properties.load(stream);
                stream.close();
            } catch (FileNotFoundException e) {
                LogUtil.error(e);
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }

        // Return the properties instance
        return properties;
    }

    /**
     * Get the properties string for the given key
     * 
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (getProperties().containsKey(key)) {
            return getProperties().getProperty(key);
        } else {
            return null;
        }
    }

    /**
     * Get the property of type int for the given key
     * 
     * @param key
     * @return
     */
    public static int getInt(String key) {
        if (getProperties().containsKey(key)) {
            return Integer.valueOf(getProperties().getProperty(key));
        } else {
            return -1;
        }
    }

}
