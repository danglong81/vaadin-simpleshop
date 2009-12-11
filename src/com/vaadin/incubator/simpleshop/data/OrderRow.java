package com.vaadin.incubator.simpleshop.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * An individual row in an order. The row contains information about the product
 * and the quantities the user is ordering. Some information is copied from the
 * product to this class, for example product name and product price. These
 * informations are copied, because any changes to the actual product object
 * should not affected already placed orders.
 * 
 * @author Kim
 * 
 */
@Entity
public class OrderRow extends AbstractPojo {

    @ManyToOne
    private Product product;

    private String productName;

    private double price;

    private int quantity;

    public OrderRow() {

    }

    /**
     * Sets the product being ordered
     * 
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get the product related to this item row
     * 
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the ordered product's name.
     * 
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Get the ordered product's name
     * 
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Set the price of the ordered product
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the price of the ordered product
     * 
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the ordered quantity
     * 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the ordered quantity
     * 
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * A transient method which calculates this row's sum
     * 
     * @return
     */
    public double getSum() {
        return quantity * price;
    }

}
