package com.vaadin.incubator.simpleshop.ui.checkout;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;

/**
 * View for selecting the delivery method of the order.
 * 
 * @author Kim
 * 
 */
public class DeliveryMethodView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 2709553655192478423L;

    @Override
    public int getStep() {
        return 3;
    }

    @Override
    protected void next() {
        SimpleshopApplication.getViewHandler().activateView(PaymentView.class);
    }

    @Override
    protected void previous() {
        SimpleshopApplication.getViewHandler().activateView(
                ContactInfoView.class);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

}
