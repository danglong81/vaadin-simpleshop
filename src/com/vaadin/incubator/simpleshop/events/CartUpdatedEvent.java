package com.vaadin.incubator.simpleshop.events;

import com.vaadin.incubator.simpleshop.data.Product;

public class CartUpdatedEvent {

    public enum EventType {
        PRODUCT_ADDED, PRODUCT_REMOVED;
    };

    private final EventType type;

    private final Product product;

    public CartUpdatedEvent(EventType type, Product product) {
        this.type = type;
        this.product = product;
    }

    public EventType getType() {
        return type;
    }

    public Product getProduct() {
        return product;
    }

    public interface CartUpdateListener {
        public void cartUpdated(CartUpdatedEvent event);
    }

}
