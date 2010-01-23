package org.vaadin.simpleshop;

import java.util.Locale;

import org.vaadin.simpleshop.data.Order;
import org.vaadin.simpleshop.data.User;
import org.vaadin.simpleshop.events.EventHandler;
import org.vaadin.simpleshop.events.UserSessionEvent;
import org.vaadin.simpleshop.events.UserSessionListener;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.ViewHandler;
import org.vaadin.simpleshop.ui.views.MainLayout;
import org.vaadin.simpleshop.util.ConfigUtil;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * Application class for the SimpleShop.
 * 
 * @author Kim
 * 
 */
public class SimpleshopApplication extends Application implements
        TransactionListener, UserSessionListener {

    private static final long serialVersionUID = 4097161573771089801L;

    // An instance of the current application stored in a thread local variable
    private static ThreadLocal<SimpleshopApplication> currentApplication = new ThreadLocal<SimpleshopApplication>();

    // The main layout we will be using in the entire application
    private MainLayout mainLayout;

    // An instance of an EventHandler for this application. Each application
    // instance has its own EventHandler.
    private final EventHandler eventHandler = new EventHandler();

    private final ViewHandler viewHandler = new ViewHandler();

    // This application instance's cart content
    private Order cartContent = new Order();

    private User user = null;

    @Override
    public void init() {
        setTheme("simpleshop");
        getContext().addTransactionListener(this);
        eventHandler.addListener(this);

        // Get the store locale from the settings
        Locale locale = new Locale(ConfigUtil.getString("locale.language"),
                ConfigUtil.getString("locale.country"));
        // Use the settings locale
        Locale.setDefault(locale);

        // Set the current application to this. This is also done in the
        // transactionStart, but in our application we need to define it in the
        // init, because the main layout will call the EventHandler (registering
        // a couple of listeners) and the getEventHandler() method requires that
        // we have the currentApplication set.
        currentApplication.set(this);

        // Set the order for the shopping cart for the same reason as above
        ShoppingCart.setOrder(cartContent);

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
    public void transactionEnd(Application application, Object transactionData) {
        // Clear the currentApplication field
        if (application == SimpleshopApplication.this) {
            currentApplication.set(null);
            currentApplication.remove();

            // Get the cart content
            cartContent = ShoppingCart.getOrder();

            // Get the current user
            user = CurrentUser.get();
        }
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        // Check if the application instance we got as parameter is actually
        // this application instance. If it is, then we should define the thread
        // local variable for this request.
        if (application == SimpleshopApplication.this) {
            currentApplication.set(this);

            // Set the cart content for the ShoppingCart's thread local variable
            ShoppingCart.setOrder(cartContent);

            // Set the current user
            CurrentUser.setUser(user);
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

    /**
     * Method for fetching the ViewHandler instance in a static way for this
     * application instance.
     * 
     * @return
     */
    public static ViewHandler getViewHandler() {
        return getInstance().viewHandler;
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
