package org.vaadin.simpleshop.ui.checkout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.data.PaymentMethod;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.controllers.CheckoutController;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * View for the payment process.
 * 
 * @author Kim
 * 
 */
public class PaymentMethodView extends AbstractCheckoutStepView implements
        LayoutClickListener {

    // Create a map between the PaymentMethod objects and the layout they are
    // represented in
    private final Map<PaymentMethod, Layout> pmToLayoutMap = new HashMap<PaymentMethod, Layout>();

    // What is the currently selected payment method.
    private PaymentMethod selectedPaymentMethod = null;

    private static final long serialVersionUID = 4611821802952819230L;

    // Add a label for displaying error messages
    private final Label errorMsg = new Label(
            SystemMsg.CHECKOUT_ERROR_CHOOSE_PAYMENT_METHOD.get());

    public PaymentMethodView() {
        mainPanel.addComponent(new Label(
                SystemMsg.PAYMENT_SELECT_PAYMENT_METHOD.get()));
        // Don't show the error message by default
        errorMsg.setVisible(false);

        // Set another style for the error message
        errorMsg.setStyleName("error");

        mainPanel.addComponent(errorMsg);

        // Enable spacing between the components
        ((VerticalLayout) mainPanel.getContent()).setSpacing(true);

        // Get all available payment methods
        List<PaymentMethod> methods = CheckoutController.getPaymentMethods();

        // Loop through the payment methods and add them to the layout
        for (PaymentMethod method : methods) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setMargin(true);
            layout.setSpacing(true);
            layout.setWidth("100%");
            layout.setStyleName("delivery-method");

            Label name = new Label();
            name.setCaption(method.getName());
            name.setValue(method.getDescription());

            layout.addComponent(name);
            layout.setExpandRatio(name, 1);

            // Add a click listener for the layout
            layout.addListener(this);

            // Set the delivery method to the layout's data, so that we can
            // later on get the delivery method from the layout object.
            layout.setData(method);

            pmToLayoutMap.put(method, layout);

            mainPanel.addComponent(layout);
        }

    }

    @Override
    public int getStep() {
        return 4;
    }

    @Override
    protected void next() {
        if (selectedPaymentMethod != null) {
            ViewHandler.activateView(PaymentMethodView.class);
            CheckoutController.setPaymentMethod(selectedPaymentMethod,
                    ShoppingCart.getOrder());

            // Remove error message if it is visible
            errorMsg.setVisible(false);
        } else {
            errorMsg.setVisible(true);
        }
    }

    @Override
    protected void previous() {
        ViewHandler.activateView(DeliveryMethodView.class);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void layoutClick(LayoutClickEvent event) {
        // Remove the "selected" style from the currently selected layout
        if (selectedPaymentMethod != null) {
            pmToLayoutMap.get(selectedPaymentMethod).removeStyleName(
                    "delivery-method-selected");
        }

        event.getComponent().addStyleName("delivery-method-selected");

        // Set the new selected delivery method
        selectedPaymentMethod = (PaymentMethod) ((HorizontalLayout) event
                .getComponent()).getData();

        // If the error message was shown, remove it
        errorMsg.setVisible(false);
    }

}
