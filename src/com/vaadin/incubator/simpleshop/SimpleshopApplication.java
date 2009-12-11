package com.vaadin.incubator.simpleshop;

import com.vaadin.Application;
import com.vaadin.incubator.simpleshop.events.EventHandler;
import com.vaadin.incubator.simpleshop.ui.views.MainLayout;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Window;

public class SimpleshopApplication extends Application implements
        TransactionListener {

    private static final long serialVersionUID = 4097161573771089801L;
    private static ThreadLocal<SimpleshopApplication> currentApplication = new ThreadLocal<SimpleshopApplication>();

    private MainLayout mainLayout;

    private final EventHandler eventHandler = new EventHandler();

    @Override
    public void init() {
        setTheme("simpleshop");
        getContext().addTransactionListener(this);
        currentApplication.set(this);

        Window mainWindow = new Window("Simple Shop");
        mainLayout = new MainLayout();
        mainWindow.setContent(mainLayout);
        mainWindow.setSizeFull();

        setMainWindow(mainWindow);
    }

    @Override
    public void transactionEnd(Application application, Object transactionData) {
        if (application == SimpleshopApplication.this) {
            currentApplication.set(null);
            currentApplication.remove();
        }
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        if (application == SimpleshopApplication.this) {
            currentApplication.set(this);
        }
    }

    public static SimpleshopApplication getInstance() {
        return currentApplication.get();
    }

    public static EventHandler getEventHandler() {
        return getInstance().eventHandler;
    }

}
