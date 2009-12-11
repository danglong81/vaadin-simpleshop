package com.vaadin.incubator.simpleshop;

import com.vaadin.Application;
import com.vaadin.incubator.simpleshop.events.EventHandler;
import com.vaadin.incubator.simpleshop.ui.views.MainLayout;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Window;

/**
 * Application class for the SimpleShop.
 * 
 * @author Kim
 * 
 */
public class SimpleshopApplication extends Application implements
        TransactionListener {

    private static final long serialVersionUID = 4097161573771089801L;

    // An instance of the current application stored in a thread local variable
    private static ThreadLocal<SimpleshopApplication> currentApplication = new ThreadLocal<SimpleshopApplication>();

    // The main layout we will be using in the entire application
    private MainLayout mainLayout;

    // An instance of an EventHandler for this application. Each application
    // instance has its own EventHandler.
    private final EventHandler eventHandler = new EventHandler();

    @Override
    public void init() {
        setTheme("simpleshop");
        getContext().addTransactionListener(this);

        // Set the current application to this. This is also done in the
        // transactionStart, but in our application we need to define it in the
        // init, because the main layout will call the EventHandler (registering
        // a couple of listeners) and the getEventHandler() method requires that
        // we have the currentApplication set.
        currentApplication.set(this);

        // Create the main window we will be using in this application
        Window mainWindow = new Window("Simple Shop");

        // Create an instance of the MainLayout. Make sure currentApplication is
        // set.
        mainLayout = new MainLayout();

        // Use the MainLayout as the content of the window. Maximize its size.
        mainWindow.setContent(mainLayout);
        mainWindow.setSizeFull();

        setMainWindow(mainWindow);
    }

    @Override
    public void transactionEnd(Application application, Object transactionData) {
        // Clear the currentApplication field
        if (application == SimpleshopApplication.this) {
            currentApplication.set(null);
            currentApplication.remove();
        }
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        // Check if the application instance we got as parameter is actually
        // this application instance. If it is, then we should define the thread
        // local variable for this request.
        if (application == SimpleshopApplication.this) {
            currentApplication.set(this);
        }
    }

    /**
     * Fetch the current application instance in a static manner.
     * 
     * @return SimpleshopApplication
     */
    public static SimpleshopApplication getInstance() {
        return currentApplication.get();
    }

    /**
     * Method for fetching the EventHandler instance in a static way for this
     * application instance.
     * 
     * @return
     */
    public static EventHandler getEventHandler() {
        return getInstance().eventHandler;
    }

}
