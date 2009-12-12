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

    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                URL configFile = ConfigUtil.class.getClassLoader().getResource(
                        "shop.properties.xml");
                properties.load(new FileInputStream(configFile.getFile()));
            } catch (FileNotFoundException e) {
                LogUtil.error(e);
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }

        return properties;
    }

    public static String getString(String key) {
        return getProperties().getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.valueOf(getProperties().getProperty(key));
    }

}
