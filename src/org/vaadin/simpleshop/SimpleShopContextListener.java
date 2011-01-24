package org.vaadin.simpleshop;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.vaadin.appfoundation.i18n.InternationalizationServlet;
import org.vaadin.appfoundation.i18n.TmxSourceReader;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

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
        try {
            FacadeFactory.registerFacade("default", true);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        URL url = SimpleshopApplication.class.getClassLoader().getResource(
                "translations.xml");
        File file = new File(url.getPath());

        InternationalizationServlet.loadTranslations(new TmxSourceReader(file));

        // Import initial test data
        InitialData.init();
    }

}
