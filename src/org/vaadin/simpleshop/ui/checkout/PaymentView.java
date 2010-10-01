package org.vaadin.simpleshop.ui.checkout;

import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.lang.SystemMsg;

public class PaymentView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 3322970250532959214L;

    public PaymentView() {
        nextStepBtn.setCaption(SystemMsg.CHECKOUT_PAY_BTN.get());
    }

    @Override
    public int getStep() {
        return 5;
    }

    @Override
    protected void next() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void previous() {
        ViewHandler.activateView(PaymentMethodView.class);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

}
