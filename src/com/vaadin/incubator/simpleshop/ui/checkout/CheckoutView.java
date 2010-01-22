package com.vaadin.incubator.simpleshop.ui.checkout;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.ParentView;
import com.vaadin.incubator.simpleshop.ui.ViewHandler;
import com.vaadin.incubator.simpleshop.ui.ViewItem;
import com.vaadin.incubator.simpleshop.ui.components.cart.CartContentView;
import com.vaadin.incubator.simpleshop.ui.views.ShopView;
import com.vaadin.incubator.simpleshop.ui.views.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This view is the main view for the checkout process.
 * 
 * @author Kim
 * 
 */
public class CheckoutView extends View<VerticalLayout> implements ParentView,
        ClickListener {

    private static final long serialVersionUID = 1700835878617090430L;

    // Process steps
    private Label verifyContent;
    private Label chooseDeliveryMethod;
    private Label contactInformation;
    private Label payment;

    // Navigation buttons
    private Button previousStepBtn;
    private Button cancelBtn;
    private Button nextStepBtn;

    private View<?> currentView = null;

    public CheckoutView() {
        super(new VerticalLayout());
        mainLayout.setSizeFull();

        // Initialize the checkout process step captions
        initStepCaptions();

        //
        ViewHandler vh = SimpleshopApplication.getViewHandler();
        ViewItem item = vh.addView("content", this);
        item.setViewClass(CartContentView.class);

        // Initialize the navigation buttons
        initButtons();

        vh.activateView("content", true);
    }

    private void initStepCaptions() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setWidth("100%");

        // Add the step captions
        verifyContent = new Label("1. "
                + SystemMsg.CHECKOUT_VERIFY_CONTENT.get());
        layout.addComponent(verifyContent);

        chooseDeliveryMethod = new Label("2. "
                + SystemMsg.CHECKOUT_CHOOSE_DELIVERY_METHOD.get());
        layout.addComponent(chooseDeliveryMethod);

        contactInformation = new Label("3. "
                + SystemMsg.CHECKOUT_CONTACT_INFORMATION.get());
        layout.addComponent(contactInformation);

        payment = new Label("4. " + SystemMsg.CHECKOUT_PAYMENT.get());
        layout.addComponent(payment);

        mainLayout.addComponent(layout);
    }

    /**
     * Initialize the buttons used for navigating between checkout process steps
     */
    private void initButtons() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setWidth("100%");
        // Set a margin for the button layout so that the buttons are not packed
        // directly against the borders.
        buttonLayout.setMargin(true);

        // Create the previous step button
        previousStepBtn = new Button(SystemMsg.CHECKOUT_PREVIOUS_STEP.get(),
                this);
        // When this view is initialized, we will be in the first step of the
        // checkout process, hence there are no previous steps and this button
        // should be disabled.
        previousStepBtn.setEnabled(false);
        buttonLayout.addComponent(previousStepBtn);
        buttonLayout.setExpandRatio(previousStepBtn, 1);

        // Initialize the cancel button
        cancelBtn = new Button(SystemMsg.GENERIC_CANCEL.get(), this);
        cancelBtn.setSizeUndefined();
        buttonLayout.addComponent(cancelBtn);
        buttonLayout.setComponentAlignment(cancelBtn, Alignment.MIDDLE_RIGHT);

        nextStepBtn = new Button(SystemMsg.CHECKOUT_NEXT_STEP.get(), this);
        nextStepBtn.setSizeUndefined();
        buttonLayout.addComponent(nextStepBtn);
        buttonLayout.setComponentAlignment(nextStepBtn, Alignment.MIDDLE_RIGHT);

        // Add the button layout to the main layout and align it to the bottom
        // of the view
        mainLayout.addComponent(buttonLayout);
        mainLayout.setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate(View<?> view) {
        if (currentView == null) {
            mainLayout.addComponent(view, 1);
        } else {
            mainLayout.replaceComponent(currentView, view);
            currentView = view;
        }
        mainLayout.setExpandRatio(view, 1);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(cancelBtn)) {
            // Cancel action has been called, return to shop.
            SimpleshopApplication.getViewHandler().activateView(ShopView.class);
        } else if (event.getButton().equals(previousStepBtn)) {
            // Previous step was requested
        } else if (event.getButton().equals(nextStepBtn)) {
            // Next step was requested
        }
    }

}
