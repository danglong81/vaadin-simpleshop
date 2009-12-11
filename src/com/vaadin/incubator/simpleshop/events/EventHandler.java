package com.vaadin.incubator.simpleshop.events;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.CartUpdateListener;

/**
 * This class handles the receiving of system wide events and dispatching those
 * events to appropriate listeners.
 * 
 * @author Kim
 * 
 */
public class EventHandler {

    // A list of all the cart update listeners in the application
    private final List<CartUpdateListener> cartUpdateListeners = new ArrayList<CartUpdateListener>();

    /**
     * Add a cart update listener
     * 
     * @param listener
     */
    public void addListener(CartUpdateListener listener) {
        if (listener != null) {
            cartUpdateListeners.add(listener);
        }
    }

    /**
     * Remove a cart update listener.
     * 
     * @param listener
     */
    public void removeListener(CartUpdateListener listener) {
        if (listener != null) {
            cartUpdateListeners.remove(listener);
        }
    }

    /**
     * Dispatch an cart update event to all appropriate listeners.
     * 
     * @param event
     */
    public void dispatchEvent(CartUpdatedEvent event) {
        if (event != null) {
            for (CartUpdateListener listener : cartUpdateListeners) {
                listener.cartUpdated(event);
            }
        }
    }

}
