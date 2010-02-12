package org.vaadin.simpleshop.ui.checkout;

import org.vaadin.appfoundation.view.ViewHandler;

/**
 * View for the payment process.
 * 
 * @author Kim
 * 
 */
public class PaymentView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 4611821802952819230L;

    @Override
    public int getStep() {
        return 4;
    }

    @Override
    protected void next() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void previous() {
        ViewHandler.activateView(DeliveryMethodView.class);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

}
