package com.vaadin.incubator.simpleshop.ui.controllers;

import java.math.RoundingMode;
import java.text.NumberFormat;

import com.vaadin.incubator.simpleshop.data.Order;
import com.vaadin.incubator.simpleshop.data.OrderRow;

public class CartController {

    private static final long serialVersionUID = 1510068834927523940L;

    /**
     * Calculates the total order sum and formats the sum
     * 
     * @param order
     * @return
     */
    public static String getFormattedTotalPrice(Order order) {
        double totalSum = 0;
        // Loop through all order rows
        for (OrderRow row : order.getOrderedProducts()) {
            // Calculate this row's sum and add it to the total sum
            totalSum += row.getPrice() * row.getQuantity();
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
        // Initialize the number formatter
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        // Format the total sum, add the currency and return the value
        return nf.format(sum) + " EUR";
    }
}
