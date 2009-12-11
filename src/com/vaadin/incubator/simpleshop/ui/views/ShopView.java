package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.incubator.simpleshop.ui.components.ItemBrowser;
import com.vaadin.incubator.simpleshop.ui.components.cart.ShoppingCart;
import com.vaadin.ui.HorizontalLayout;

public class ShopView extends HorizontalLayout implements View {

    private static final long serialVersionUID = 5345221919713457136L;

    private final ShoppingCart cart;

    private final ItemBrowser browser;

    public ShopView() {
        setSizeFull();
        setCaption("Shop");
        browser = new ItemBrowser();
        cart = new ShoppingCart();

        addComponent(browser);
        addComponent(cart);
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }

}
