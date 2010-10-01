package org.vaadin.simpleshop.ui.checkout;

import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.controllers.CartController;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class PaymentView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 3322970250532959214L;

    private GridLayout summaryGrid = new GridLayout(2, 7);

    public PaymentView() {
        nextStepBtn.setCaption(SystemMsg.CHECKOUT_PAY_BTN.get());
    }

    private void refreshSummary() {
        summaryGrid.setWidth("100%");

        Label purchasesInTotal = new Label(SystemMsg.PAYMENT_PURCHASE_TOTAL
                .get());
        purchasesInTotal.setStyleName("summary-header");
        summaryGrid.addComponent(purchasesInTotal, 0, 0, 1, 0);

        Label purchasesInTotalSum = new Label(CartController
                .formatSum(ShoppingCart.getCartTotalIncludingVAT()));
        purchasesInTotalSum.setWidth(null);
        summaryGrid.addComponent(purchasesInTotalSum, 0, 1, 1, 1);
        summaryGrid.setComponentAlignment(purchasesInTotalSum,
                Alignment.TOP_RIGHT);

        Label deliveryMethodHeader = new Label(
                SystemMsg.PAYMENT_DELIVERY_METHOD.get());
        deliveryMethodHeader.setStyleName("summary-header");
        summaryGrid.addComponent(deliveryMethodHeader, 0, 2, 1, 2);

        Label deliveryMethodName = new Label(ShoppingCart.getOrder()
                .getDeliveryMethodName());
        summaryGrid.addComponent(deliveryMethodName);

        Label deliveryMethodPrice = new Label(CartController
                .formatSum(ShoppingCart.getOrder()
                        .getDeliveryMethodPriceIncludingVAT()));
        summaryGrid.addComponent(deliveryMethodPrice);
        deliveryMethodPrice.setWidth(null);
        summaryGrid.setComponentAlignment(deliveryMethodPrice,
                Alignment.TOP_RIGHT);

        Label paymentMethodHeader = new Label(SystemMsg.PAYMENT_PAYMENT_METHOD
                .get());
        paymentMethodHeader.setStyleName("summary-header");
        summaryGrid.addComponent(paymentMethodHeader, 0, 4, 1, 4);

        Label paymentMethodName = new Label(ShoppingCart.getOrder()
                .getPaymentMethodName());
        summaryGrid.addComponent(paymentMethodName);

        Label paymentMethodPrice = new Label(CartController
                .formatSum(ShoppingCart.getOrder()
                        .getPaymentMethodPriceIncludingVAT()));
        summaryGrid.addComponent(paymentMethodPrice);
        paymentMethodPrice.setWidth(null);
        summaryGrid.setComponentAlignment(paymentMethodPrice,
                Alignment.TOP_RIGHT);

        Label total = new Label(CartController.formatSum(ShoppingCart
                .getOrderTotalIncludingVAT()));
        total.setCaption(SystemMsg.CART_TOTAL_SUM_CAPTION_INCL_VAT.get());
        total.setStyleName("summary-total");
        summaryGrid.addComponent(total, 0, 6, 1, 6);
        total.setWidth(null);
        summaryGrid.setComponentAlignment(total, Alignment.TOP_RIGHT);

        mainPanel.addComponent(summaryGrid);
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
        summaryGrid.removeAllComponents();
        refreshSummary();
    }

}
