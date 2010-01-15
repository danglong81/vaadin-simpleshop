package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.ParentView;
import com.vaadin.incubator.simpleshop.ui.ViewHandler;
import com.vaadin.incubator.simpleshop.ui.ViewItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
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

    public CheckoutView() {
        super(new VerticalLayout());

        // Initialize the checkout process step captions
        initStepCaptions();

        //
        ViewHandler vh = SimpleshopApplication.getViewHandler();
        ViewItem item = vh.addView("content", this);
        // item.setViewClass(CartItems.class);
        //
        // mainLayout.addComponent(item.getView());

        // Initialize the navigation buttons
        initButtons();
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

        // Create the previous step button
        previousStepBtn = new Button(SystemMsg.CHECKOUT_PREVIOUS_STEP.get(),
                this);
        // When this view is initialized, we will be in the first step of the
        // checkout process, hence there are no previous steps and this button
        // should be disabled.
        previousStepBtn.setEnabled(false);
        buttonLayout.addComponent(previousStepBtn);

        // Create a spacer
        CssLayout spacer = new CssLayout();
        spacer.setWidth("100%");
        buttonLayout.addComponent(spacer);

        // Initialize the cancel button
        cancelBtn = new Button(SystemMsg.GENERIC_CANCEL.get(), this);
        cancelBtn.setSizeUndefined();
        buttonLayout.addComponent(cancelBtn);

        nextStepBtn = new Button(SystemMsg.CHECKOUT_NEXT_STEP.get(), this);
        nextStepBtn.setSizeUndefined();
        buttonLayout.addComponent(nextStepBtn);

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
        // TODO Auto-generated method stub

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
