package com.vaadin.incubator.simpleshop.ui.components.cart;

import com.vaadin.incubator.simpleshop.ShoppingCart;
import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.CartUpdateListener;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.checkout.CheckoutView;
import com.vaadin.incubator.simpleshop.ui.controllers.CartController;
import com.vaadin.incubator.simpleshop.ui.views.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class defines the layout for the cart content. The cart content consists
 * of two parts, a list of items in the cart and a summary containing the total
 * sum of the cart content including a checkout button.
 * 
 * @author Kim
 * 
 */
public class CartContentView extends View<VerticalLayout> implements
        CartUpdateListener, ClickListener {

    private static final long serialVersionUID = 8401760557369059696L;

    private HorizontalLayout summaryLayout;

    private Label totalSumLabel;

    private Button checkoutBtn;

    private CartItems content;

    /**
     * Constructor
     */
    public CartContentView() {
        super(new VerticalLayout());
        // Take as much space as there is available
        mainLayout.setSizeFull();

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
        mainLayout.addComponent(content);

        // The items panel should take all the available space
        mainLayout.setExpandRatio(content, 1);
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

        // A label is by default 100% wide, hence aligning a label won't work
        // properly unless you set its width to null (undefined).
        totalSumLabel.setWidth(null);

        // Initialize the checkout button
        checkoutBtn = new Button(SystemMsg.CART_CHECKOUT.get(), this);

        // Button should be disabled when there are no items in the cart and
        // since the cart will be empty when this view is initialized, this
        // button should then be disabled.
        checkoutBtn.setEnabled(false);

        // Add the label and the button to the layout
        summaryLayout.addComponent(totalSumLabel);
        summaryLayout.addComponent(checkoutBtn);

        // The sum label should be aligned to the left and the checkout button
        // to the right
        summaryLayout.setComponentAlignment(totalSumLabel,
                Alignment.MIDDLE_LEFT);
        summaryLayout
                .setComponentAlignment(checkoutBtn, Alignment.MIDDLE_RIGHT);

        // Add summary layout to the main layout
        mainLayout.addComponent(summaryLayout);
    }

    @Override
    public void cartUpdated(CartUpdatedEvent event) {
        totalSumLabel.setValue(CartController
                .getFormattedTotalPrice(ShoppingCart.getOrder()));
        content.refresh();

        // Check if the checkout button exists, as it is removed in some cases.
        if (checkoutBtn != null) {
            // Disable the checkout button if there aren't any items in the cart
            if (checkoutBtn.isEnabled()
                    && ShoppingCart.getOrder().getOrderedProducts().size() <= 0) {
                checkoutBtn.setEnabled(false);
            } else if (!checkoutBtn.isEnabled()
                    && ShoppingCart.getOrder().getOrderedProducts().size() > 0) {
                // Enable button if cart contains items
                checkoutBtn.setEnabled(true);
            }
        }
    }

    @Override
    public void activated(Object... params) {
        // Check if we have any parameters
        if (params != null && params.length > 0) {
            // If there are any parameters, then we expect the first parameter
            // to be a boolean
            if (params[0] instanceof Boolean) {
                // The parameter boolean decides the content of the summary
                // layout. If the boolean is true, then the checkout button
                // should be removed and the total sum label should be aligned
                // to the right.
                boolean hideCheckoutBtn = (Boolean) params[0];
                if (hideCheckoutBtn && checkoutBtn != null) {
                    summaryLayout.removeComponent(checkoutBtn);
                    summaryLayout.setComponentAlignment(totalSumLabel,
                            Alignment.MIDDLE_RIGHT);

                    // Clear the instance of the checkout button, as we won't be
                    // needing it in this layout any longer.
                    checkoutBtn = null;
                }
            }
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // We want to proceed to the checkout process. Activate the checkout
        // view.
        SimpleshopApplication.getViewHandler().activateView(CheckoutView.class);
    }

}
