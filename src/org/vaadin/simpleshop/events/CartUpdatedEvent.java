package org.vaadin.simpleshop.events;

import org.vaadin.simpleshop.data.Product;

/**
 * This class defines the events for cart updates and the interface for the cart
 * update event listeners.
 * 
 * @author Kim
 * 
 */
public class CartUpdatedEvent {

    /** Cart update event types. Defines what happened in the cart **/
    public enum EventType {
        PRODUCT_ADDED, PRODUCT_REMOVED, PRODUCT_QUANTITY_CHANGED;
    };

    private final EventType type;

    private final Product product;

    public CartUpdatedEvent(EventType type, Product product) {
        this.type = type;
        this.product = product;
    }

    /**
     * Get the event type which defines which action took place in the cart.
     * 
     * @return
     */
    public EventType getType() {
        return type;
    }

    /**
     * Returns the product which was the subject of the event.
     * 
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Cart update listener
     * 
     * @author Kim
     * 
     */
    public interface CartUpdateListener {
        /**
         * The shopping cart has been updated.
         * 
         * @param event
         */
        public void cartUpdated(CartUpdatedEvent event);
    }

}
