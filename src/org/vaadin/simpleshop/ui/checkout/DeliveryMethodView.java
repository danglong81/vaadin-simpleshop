package org.vaadin.simpleshop.ui.checkout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.data.DeliveryMethod;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.controllers.CheckoutController;
import org.vaadin.simpleshop.util.ConfigUtil;
import org.vaadin.simpleshop.util.NumberUtil;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * View for selecting the delivery method of the order.
 * 
 * @author Kim
 * 
 */
public class DeliveryMethodView extends AbstractCheckoutStepView implements
        LayoutClickListener {

    private static final long serialVersionUID = 2709553655192478423L;

    // Create a map between the DeliveryMethod objects and the layout they are
    // represented in
    private final Map<DeliveryMethod, Layout> dmToLayoutMap = new HashMap<DeliveryMethod, Layout>();

    // What is the currently selected delivery method.
    private DeliveryMethod selectedDeliveryMethod = null;

    // Add a label for displaying error messages
    private final Label errorMsg = new Label(
            SystemMsg.CHECKOUT_ERROR_CHOOSE_DELIVERY_METHOD.get());

    public DeliveryMethodView() {
        // Don't show the error message by default
        errorMsg.setVisible(false);

        // Set another style for the error message
        errorMsg.setStyleName("error");

        mainPanel.addComponent(errorMsg);

        // Enable spacing between the components
        ((VerticalLayout) mainPanel.getContent()).setSpacing(true);

        // Get all available delivery methods
        List<DeliveryMethod> methods = CheckoutController.getDeliveryMethods();

        // Loop through the delivery methods and add them to the layout
        for (DeliveryMethod method : methods) {
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

            Label price = new Label();
            // Check if we should show the price including or excluding the VAT.
            if (ConfigUtil.getBoolean("product.showPriceIncludingTakes")) {
                price.setValue(NumberUtil.roundSum(method
                        .getPriceIncludingVAT(), 2)
                        + " " + ConfigUtil.getString("product.currency"));
            } else {
                price.setValue(NumberUtil.roundSum(method
                        .getPriceExcludingVAT(), 2)
                        + " " + ConfigUtil.getString("product.currency"));
            }
            price.setWidth(null);
            layout.addComponent(price);
            layout.setComponentAlignment(price, Alignment.MIDDLE_RIGHT);

            // Add a click listener for the layout
            layout.addListener(this);

            // Set the delivery method to the layout's data, so that we can
            // later on get the delivery method from the layout object.
            layout.setData(method);

            dmToLayoutMap.put(method, layout);

            mainPanel.addComponent(layout);
        }
    }

    @Override
    public int getStep() {
        return 3;
    }

    @Override
    protected void next() {
        if (selectedDeliveryMethod != null) {
            ViewHandler.activateView(PaymentView.class);
            CheckoutController.setDeliveryMethod(selectedDeliveryMethod,
                    ShoppingCart.getOrder());

            // Remove error message if it is visible
            errorMsg.setVisible(false);
        } else {
            errorMsg.setVisible(true);
        }
    }

    @Override
    protected void previous() {
        ViewHandler.activateView(ContactInfoView.class);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void layoutClick(LayoutClickEvent event) {
        // Remove the "selected" style from the currently selected layout
        if (selectedDeliveryMethod != null) {
            dmToLayoutMap.get(selectedDeliveryMethod).removeStyleName(
                    "delivery-method-selected");
        }

        event.getComponent().addStyleName("delivery-method-selected");

        // Set the new selected delivery method
        selectedDeliveryMethod = (DeliveryMethod) ((HorizontalLayout) event
                .getComponent()).getData();

        // If the error message was shown, remove it
        errorMsg.setVisible(false);
    }

}
