package org.vaadin.simpleshop.ui.controllers;

import java.util.List;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.simpleshop.data.DeliveryMethod;
import org.vaadin.simpleshop.data.Order;
import org.vaadin.simpleshop.data.PaymentMethod;

/**
 * Controller class for the checkout process.
 * 
 * @author Kim
 * 
 */
public class CheckoutController {

    public static List<DeliveryMethod> getDeliveryMethods() {
        return FacadeFactory.getFacade().list(DeliveryMethod.class);
    }

    public static List<PaymentMethod> getPaymentMethods() {
        return FacadeFactory.getFacade().list(PaymentMethod.class);
    }

    public static void setDeliveryMethod(DeliveryMethod method, Order order) {
        if (method != null && order != null) {
            order.setDeliveryMethodName(method.getName());
            order.setDeliveryMethodPrice(method.getPrice().getPrice());
            order.setDeliveryMethodVAT(method.getPrice().getVat()
                    .getPercentage());
        }
    }

    public static void setPaymentMethod(PaymentMethod method, Order order) {
        if (method != null && order != null) {
            // TODO
        }
    }
}
