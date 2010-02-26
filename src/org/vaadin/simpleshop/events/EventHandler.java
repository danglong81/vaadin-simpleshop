package org.vaadin.simpleshop.events;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.simpleshop.events.CartUpdatedEvent.CartUpdateListener;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;

/**
 * This class handles the receiving of system wide events and dispatching those
 * events to appropriate listeners.
 * 
 * @author Kim
 * 
 */
public class EventHandler implements TransactionListener {

    private static final long serialVersionUID = -3963471359648190718L;

    // A list of all the cart update listeners in the application
    private final List<CartUpdateListener> cartUpdateListeners = new ArrayList<CartUpdateListener>();

    private final List<UserSessionListener> userSessionListeners = new ArrayList<UserSessionListener>();

    private static ThreadLocal<EventHandler> instance = new ThreadLocal<EventHandler>();

    private final Application application;

    public EventHandler(Application application) {
        this.application = application;
        instance.set(this);
    }

    /**
     * Add a cart update listener
     * 
     * @param listener
     */
    public static void addListener(CartUpdateListener listener) {
        if (listener != null) {
            instance.get().cartUpdateListeners.add(listener);
        }
    }

    /**
     * Remove a cart update listener.
     * 
     * @param listener
     */
    public static void removeListener(CartUpdateListener listener) {
        if (listener != null) {
            instance.get().cartUpdateListeners.remove(listener);
        }
    }

    /**
     * Dispatch an cart update event to all appropriate listeners.
     * 
     * @param event
     */
    public static void dispatchEvent(CartUpdatedEvent event) {
        if (event != null) {
            for (CartUpdateListener listener : instance.get().cartUpdateListeners) {
                listener.cartUpdated(event);
            }
        }
    }

    /**
     * Add a user session listener
     * 
     * @param listener
     */
    public static void addListener(UserSessionListener listener) {
        if (listener != null) {
            instance.get().userSessionListeners.add(listener);
        }
    }

    /**
     * Remove a user session listener.
     * 
     * @param listener
     */
    public static void removeListener(UserSessionListener listener) {
        if (listener != null) {
            instance.get().userSessionListeners.remove(listener);
        }
    }

    /**
     * Dispatch a login event to all appropriate listeners.
     * 
     * @param event
     */
    public static void dispatchLoginEvent(UserSessionEvent event) {
        if (event != null) {
            for (UserSessionListener listener : instance.get().userSessionListeners) {
                listener.loginEvent(event);
            }
        }
    }

    /**
     * Dispatch a logout event to all appropriate listeners.
     * 
     * @param event
     */
    public static void dispatchLogoutEvent(UserSessionEvent event) {
        if (event != null) {
            for (UserSessionListener listener : instance.get().userSessionListeners) {
                listener.logoutEvent(event);
            }
        }
    }

    @Override
    public void transactionEnd(Application application, Object transactionData) {
        // Clear thread local instance at the end of the transaction
        if (this.application == application) {
            instance.set(null);
        }
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        // Set the thread local instance
        if (this.application == application) {
            instance.set(this);
        }
    }

}
