package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.incubator.simpleshop.CurrentUser;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.Icons;
import com.vaadin.incubator.simpleshop.ui.components.cart.CartContentView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This view is placed on the right side of the item browser. This view consist
 * of navigation buttons and the subviews (cart content, user information, ...)
 * 
 * @author Kim
 * 
 */
public class InformationView extends VerticalLayout implements ClickListener {

    private static final long serialVersionUID = 8401760557369059696L;

    // Navigation buttons
    private Button profileBtn;
    private Button shoppingCartBtn;

    // Layout for navigation buttons
    private HorizontalLayout buttonLayout;

    // The cart content view
    private final CartContentView cartContent;

    private Component currentView;

    public InformationView() {
        // Take all the space available
        setSizeFull();

        // Initialize navigation buttons
        initButtons();

        // Initialize the cart content view
        cartContent = new CartContentView();

        // Set the cart content view as the current view
        currentView = cartContent;

        // Add buttons to layout
        addComponent(buttonLayout);

        // Add current view to layout
        addComponent(currentView);

        // The sub view should take as much space as there is available and
        // navigation button's should only reserve as much space as they need.
        setExpandRatio(currentView, 1);
    }

    /**
     * Initialize navigation buttons
     */
    private void initButtons() {
        // Create the button layout
        buttonLayout = new HorizontalLayout();
        buttonLayout.setHeight(null);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(false, true, false, true);

        // Create and add buttons to layout
        profileBtn = new Button(null, this);
        profileBtn.setStyleName(Button.STYLE_LINK);
        profileBtn.setWidth("64px");
        profileBtn.setHeight("64px");
        profileBtn.setIcon(Icons.USER_PROFILE.getResource());
        profileBtn.setDescription(SystemMsg.GENERIC_USER_PROFILE.get());
        buttonLayout.addComponent(profileBtn);

        shoppingCartBtn = new Button(null, this);
        shoppingCartBtn.setStyleName(Button.STYLE_LINK);
        shoppingCartBtn.setWidth("64px");
        shoppingCartBtn.setHeight("64px");
        shoppingCartBtn.setIcon(Icons.SHOPPING_CART.getResource());
        shoppingCartBtn.setDescription(SystemMsg.GENERIC_SHOPPING_CART.get());
        buttonLayout.addComponent(shoppingCartBtn);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(profileBtn) && CurrentUser.get() == null) {
            LoginView loginView = new LoginView();
            replaceComponent(currentView, loginView);
            currentView = loginView;
        } else if (event.getButton().equals(shoppingCartBtn)) {
            replaceComponent(currentView, cartContent);
            currentView = cartContent;
        }

        setExpandRatio(currentView, 1);
    }

}
