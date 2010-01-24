package org.vaadin.simpleshop.ui.controllers;

import java.util.List;

import org.vaadin.simpleshop.data.DeliveryMethod;
import org.vaadin.simpleshop.facade.FacadeFactory;

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
}
