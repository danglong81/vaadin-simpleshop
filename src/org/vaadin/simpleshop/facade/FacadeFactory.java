package org.vaadin.simpleshop.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating and managing facades in the application.
 * 
 * @author Kim
 * 
 */
public class FacadeFactory {

    private static Map<String, JPAFacade> facades = new HashMap<String, JPAFacade>();

    private static IFacade defaultFacade;

    /**
     * Register a new facade to the application.
     * 
     * @param name
     *            The persistence-unit name in the persistence.xml file.
     * @param isDefault
     *            Should this facade be the default facade to be used in the
     *            application.
     */
    public static void registerFacade(String name, boolean isDefault) {
        // Check if there already exists a facade with this name.
        if (facades.containsKey(name)) {
            // If it exists, then close the facade.
            facades.get(name).close();
        }

        // Create a new instance of the facade and put it in our map
        JPAFacade facade = new JPAFacade(name);
        facades.put(name, facade);

        // Should this facade instance be used as the default facade in the
        // application?
        if (isDefault) {
            defaultFacade = facade;
        }
    }

    /**
     * Returns the default facade of the application.
     * 
     * @return
     */
    public static IFacade getFacade() {
        return defaultFacade;
    }

    /**
     * Get the facade for a specific configuration
     * 
     * @param name
     *            Persistence-unit name (defined in the persistence.xml)
     * @return
     */
    public static JPAFacade getFacade(String name) {
        return facades.get(name);
    }

    /**
     * Removes a facade from the factory
     * 
     * @param name
     *            Persistence-unit name (defined in the persistence.xml)
     */
    public static void removeFacade(String name) {
        if (facades.containsKey(name)) {
            JPAFacade facade = facades.get(name);
            facade.kill();
            facades.remove(name);
        }
    }

}
