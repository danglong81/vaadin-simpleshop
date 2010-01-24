package org.vaadin.simpleshop.ui.controllers;

import org.vaadin.simpleshop.data.Order;
import org.vaadin.simpleshop.data.OrderRow;
import org.vaadin.simpleshop.util.ConfigUtil;
import org.vaadin.simpleshop.util.NumberUtil;

public class CartController {

    private static final long serialVersionUID = 1510068834927523940L;

    /**
     * Calculates the total order sum and formats the sum
     * 
     * @param order
     * @return
     */
    public static String getFormattedTotalPrice(Order order, boolean includeVat) {
        double totalSum = 0;
        // Loop through all order rows
        for (OrderRow row : order.getOrderedProducts()) {
            // Calculate this row's sum and add it to the total sum
            if (includeVat) {
                totalSum += row.getSumIncludingVAT();
            } else {
                totalSum += row.getSumExcludingVAT();
            }
        }

        return formatSum(totalSum);
    }

    /**
     * Formats the given sum and adds the currency
     * 
     * @param sum
     * @return
     */
    public static String formatSum(double sum) {
        // Format the total sum, add the currency and return the value
        return NumberUtil.roundSum(sum, 2) + " "
                + ConfigUtil.getString("product.currency");
    }
}
