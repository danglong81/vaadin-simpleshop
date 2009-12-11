package com.vaadin.incubator.simpleshop.ui.components.cart;

import com.vaadin.incubator.simpleshop.ui.controllers.CartController;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class ShoppingCart extends VerticalLayout {

    private static final long serialVersionUID = 8401760557369059696L;

    private Button userSettings;

    private HorizontalLayout buttonLayout;

    private final CartContent cartContent;

    private final CartController controller = new CartController();

    public ShoppingCart() {
        setSizeFull();
        initButtons();

        cartContent = new CartContent(controller);
        addComponent(buttonLayout);
        addComponent(cartContent);

        setExpandRatio(cartContent, 1);
    }

    public void initButtons() {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setHeight(null);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(true);

        userSettings = new Button("User");
        buttonLayout.addComponent(userSettings);
    }

}
