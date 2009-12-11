package com.vaadin.incubator.simpleshop;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vaadin.incubator.simpleshop.facade.FacadeFactory;

/**
 * 
 * @author Kim
 * 
 */
public class SimpleShopContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void contextInitialized(ServletContextEvent arg0) {
        // Setup and register the facade
        FacadeFactory.registerFacade("default", true);

        // Import initial test data
        InitialData.init();
    }

}
