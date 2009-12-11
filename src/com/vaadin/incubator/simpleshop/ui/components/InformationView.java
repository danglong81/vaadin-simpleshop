package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.incubator.simpleshop.ui.components.cart.CartContentView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * This view is placed on the right side of the item browser. This view consist
 * of navigation buttons and the subviews (cart content, user information, ...)
 * 
 * @author Kim
 * 
 */
public class InformationView extends VerticalLayout {

    private static final long serialVersionUID = 8401760557369059696L;

    // Navigation buttons
    private Button userSettings;

    // Layout for navigation buttons
    private HorizontalLayout buttonLayout;

    // The cart content view
    private final CartContentView cartContent;

    private final Component currentView;

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
        // navigation button's should only reserv as much space as they need.
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
        buttonLayout.setMargin(true);

        // Create and add buttons to layout
        userSettings = new Button("User");
        buttonLayout.addComponent(userSettings);
    }

}
