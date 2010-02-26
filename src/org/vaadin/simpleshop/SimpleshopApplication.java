package org.vaadin.simpleshop;

import java.util.Locale;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.events.EventHandler;
import org.vaadin.simpleshop.events.UserSessionEvent;
import org.vaadin.simpleshop.events.UserSessionListener;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.views.MainLayout;
import org.vaadin.simpleshop.util.ConfigUtil;

import com.vaadin.Application;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * Application class for the SimpleShop.
 * 
 * @author Kim
 * 
 */
public class SimpleshopApplication extends Application implements
        UserSessionListener {

    private static final long serialVersionUID = 4097161573771089801L;

    // The main layout we will be using in the entire application
    private MainLayout mainLayout;

    @Override
    public void init() {
        setTheme("simpleshop");
        getContext().addTransactionListener(new EventHandler(this));
        getContext().addTransactionListener(new ShoppingCart(this));
        getContext().addTransactionListener(new SessionHandler(this));
        getContext().addTransactionListener(new ViewHandler(this));

        EventHandler.addListener(this);

        // Get the store locale from the settings
        Locale locale = new Locale(ConfigUtil.getString("locale.language"),
                ConfigUtil.getString("locale.country"));
        // Use the settings locale
        Locale.setDefault(locale);

        // Create the main window we will be using in this application
        Window mainWindow = new Window(SystemMsg.APPLICATION_TITLE.get());

        // Create an instance of the MainLayout. Make sure currentApplication is
        // set.
        mainLayout = new MainLayout();

        // Use the MainLayout as the content of the window. Maximize its size.
        mainWindow.setContent(mainLayout);
        mainWindow.setSizeFull();

        setMainWindow(mainWindow);
    }

    @Override
    public void loginEvent(UserSessionEvent event) {
        getMainWindow().showNotification(
                SystemMsg.APPLICATION_WELCOME_USER.get(event.getUser()
                        .getName()), "", Notification.TYPE_TRAY_NOTIFICATION);
    }

    @Override
    public void logoutEvent(UserSessionEvent event) {
        getMainWindow().showNotification(
                SystemMsg.APPLICATION_USER_X_LOGGED_OUT.get(event.getUser()
                        .getName()), "", Notification.TYPE_TRAY_NOTIFICATION);
    }

}
