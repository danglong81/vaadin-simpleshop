package com.vaadin.incubator.simpleshop.facade;

import java.util.HashMap;
import java.util.Map;

public class FacadeFactory {

    private static Map<String, FacadeImpl> facades = new HashMap<String, FacadeImpl>();

    private static IFacade defaultFacade;

    public static void registerFacade(String name, boolean isDefault) {
        if (facades.containsKey(name)) {
            facades.get(name).close();
        }

        FacadeImpl facade = new FacadeImpl(name);
        facades.put(name, facade);

        if (isDefault) {
            defaultFacade = facade;
        }
    }

    public static IFacade getFacade() {
        return defaultFacade;
    }

    public static FacadeImpl getFacade(String name) {
        return facades.get(name);
    }

    public static void removeFacade(String name) {
        if (facades.containsKey(name)) {
            FacadeImpl facade = facades.get(name);
            facade.kill();
            facades.remove(name);
        }
    }

}
