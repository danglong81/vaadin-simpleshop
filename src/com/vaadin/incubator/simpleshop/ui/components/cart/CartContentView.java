package com.vaadin.incubator.simpleshop.ui.components.cart;

import com.vaadin.incubator.simpleshop.ShoppingCart;
import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.CartUpdateListener;
import com.vaadin.incubator.simpleshop.ui.controllers.CartController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class defines the layout for the cart content. The cart content consists
 * of two parts, a list of items in the cart and a summary containing the total
 * sum of the cart content including a checkout button.
 * 
 * @author Kim
 * 
 */
public class CartContentView extends VerticalLayout implements
        CartUpdateListener {

    private static final long serialVersionUID = 8401760557369059696L;

    private HorizontalLayout summaryLayout;

    private Label totalSumLabel;

    private Button paymentBtn;

    private CartItems content;

    /**
     * Constructor
     */
    public CartContentView() {
        // Take as much space as there is available
        setSizeFull();

        // Initialize the content
        initContent();

        // Initialize the summary
        initSummary();

        SimpleshopApplication.getEventHandler().addListener(this);
    }

    /**
     * Initializes the cart items listing panel
     */
    private void initContent() {
        // Create the cart items panel and add it to the main layout
        content = new CartItems();
        addComponent(content);

        // The items panel should take all the available space
        setExpandRatio(content, 1);
    }

    /**
     * Initializes the cart content summary layout
     */
    private void initSummary() {
        // Create a horizontal layout for the sum and buttons
        summaryLayout = new HorizontalLayout();
        summaryLayout.setWidth("100%");
        summaryLayout.setMargin(true);
        summaryLayout.setSpacing(true);

        // Create the sum label
        totalSumLabel = new Label(CartController
                .getFormattedTotalPrice(ShoppingCart.getOrder()));

        // Initialize the checkout button
        paymentBtn = new Button("Proceed");

        // Add the label and the button to the layout
        summaryLayout.addComponent(totalSumLabel);
        summaryLayout.addComponent(paymentBtn);

        // The sum label should be aligned to the left and the checkout button
        // to the right
        summaryLayout.setComponentAlignment(totalSumLabel,
                Alignment.MIDDLE_LEFT);
        summaryLayout.setComponentAlignment(paymentBtn, Alignment.MIDDLE_RIGHT);

        // Add summary layout to the main layout
        addComponent(summaryLayout);
    }

    @Override
    public void cartUpdated(CartUpdatedEvent event) {
        totalSumLabel.setValue(CartController
                .getFormattedTotalPrice(ShoppingCart.getOrder()));
        content.refresh();
    }

}
