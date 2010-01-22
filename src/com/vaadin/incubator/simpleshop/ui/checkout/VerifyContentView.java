package com.vaadin.incubator.simpleshop.ui.checkout;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.ui.components.cart.CartContentView;

public class VerifyContentView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 9209471354300993943L;

    private CartContentView view;

    public VerifyContentView() {
        view = new CartContentView();
        view.activated(true);

        // The CartContentView itself has a panel, so there is no need for us to
        // set it inside another panel. Let's make the DOM structure a bit more
        // lightweight by removing the main panel and replacing it with the view
        // itself.
        mainLayout.replaceComponent(mainPanel, view);

        mainLayout.setExpandRatio(view, 1);

        previousStepBtn.setEnabled(false);
    }

    @Override
    protected void next() {
        SimpleshopApplication.getViewHandler().activateView(
                ContactInfoView.class);
    }

    @Override
    protected void previous() {
        // TODO Auto-generated method stub

    }

    @Override
    public void activated(Object... params) {

    }

    @Override
    public int getStep() {
        return 1;
    }

}
