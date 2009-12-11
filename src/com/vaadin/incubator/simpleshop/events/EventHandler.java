package com.vaadin.incubator.simpleshop.events;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.CartUpdateListener;

public class EventHandler {

    private final List<CartUpdateListener> cartUpdateListeners = new ArrayList<CartUpdateListener>();

    public void addListener(CartUpdateListener listener) {
        if (listener != null) {
            cartUpdateListeners.add(listener);
        }
    }

    public void removeListener(CartUpdateListener listener) {
        if (listener != null) {
            cartUpdateListeners.remove(listener);
        }
    }

    public void dispatchEvent(CartUpdatedEvent event) {
        if (event != null) {
            for (CartUpdateListener listener : cartUpdateListeners) {
                listener.cartUpdated(event);
            }
        }
    }

}
