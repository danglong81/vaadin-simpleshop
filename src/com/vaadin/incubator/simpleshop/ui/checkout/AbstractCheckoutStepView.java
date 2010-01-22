package com.vaadin.incubator.simpleshop.ui.checkout;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.views.ShopView;
import com.vaadin.incubator.simpleshop.ui.views.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Abstract implementation of the views in the checkout process.
 * 
 * @author Kim
 * 
 */
abstract public class AbstractCheckoutStepView extends View<VerticalLayout>
        implements ClickListener {

    private static final long serialVersionUID = -7000782887824488286L;

    protected Panel mainPanel;

    // Navigation buttons
    protected Button previousStepBtn;
    protected Button cancelBtn;
    protected Button nextStepBtn;

    public AbstractCheckoutStepView() {
        super(new VerticalLayout());

        // Create the main panel
        mainPanel = new Panel();
        mainPanel.setStyleName(Panel.STYLE_LIGHT);
        mainPanel.setSizeFull();

        mainLayout.setSizeFull();
        // Add the main panel to the main layout
        mainLayout.addComponent(mainPanel);

        // Expand the main panel so that it will take all the available space
        mainLayout.setExpandRatio(mainPanel, 1);

        // Initialize the navigation buttons
        initButtons();

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

        // Add the button layout to the main layout
        mainLayout.addComponent(buttonLayout);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(cancelBtn)) {
            SimpleshopApplication.getViewHandler().activateView(ShopView.class);
        } else if (event.getButton().equals(nextStepBtn)) {
            next();
        } else if (event.getButton().equals(previousStepBtn)) {
            previous();
        }
    }

    /**
     * Method is called when the next step button is pressed.
     */
    abstract protected void next();

    /**
     * Method is called when the previous step button is pressed.
     */
    abstract protected void previous();

    /**
     * Returns the ordinal number of the view in the checkout process.
     * 
     * @return
     */
    abstract public int getStep();

}
