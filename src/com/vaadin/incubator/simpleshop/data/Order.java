package com.vaadin.incubator.simpleshop.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * Entity for orders places in the shop
 * 
 * @author Kim
 * 
 */
@Entity
@Table(name = "shop_order")
public class Order extends AbstractPojo {

    @OneToMany(cascade = CascadeType.ALL)
    @PrivateOwned
    private List<OrderRow> orderedProducts = new ArrayList<OrderRow>();

    public Order() {

    }

    /**
     * Set the list of items related to this order
     * 
     * @param orderedProducts
     */
    public void setOrderedProducts(List<OrderRow> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    /**
     * Get all items related to this order
     * 
     * @return
     */
    public List<OrderRow> getOrderedProducts() {
        return orderedProducts;
    }

    /**
     * A transient method for adding a product to the order. If product already
     * exists in the order, then increment the quantity by one.
     * 
     * @param product
     */
    public void addProduct(Product product) {
        OrderRow row = getOrderRowForProduct(product);
        if (row != null) {
            row.setQuantity(row.getQuantity() + 1);
        } else {
            row = new OrderRow();
            row.setProduct(product);
            row.setProductName(product.getName());
            row.setPrice(product.getPrice());
            row.setQuantity(1);
            getOrderedProducts().add(row);
        }
    }

    /**
     * Sets the quantity for the given product. If the product doesn't exist in
     * the orders, then it is added with the given quantity. If the quantity is
     * zero or less than zero, then the product is removed from the order.
     * 
     * @param product
     * @param quantity
     */
    public void setQuantity(Product product, int quantity) {
        // Get the order row
        OrderRow row = getOrderRowForProduct(product);
        if (row != null) {
            // The row existed, now check that the quantity is a positive
            // integer
            if (quantity <= 0) {
                // The quantity wasn't a positive integer, hence we should
                // remove this order row from the order
                getOrderedProducts().remove(row);
            } else {
                // Row existed and quantity was valid, modify quantity
                row.setQuantity(quantity);
            }
        } else if (quantity > 0) {
            // Quantity is valid
            // Row didn't exist, so let's create a new one
            row = new OrderRow();
            row.setProduct(product);
            row.setProductName(product.getName());
            row.setPrice(product.getPrice());
            row.setQuantity(quantity);
            getOrderedProducts().add(row);
        }
    }

    /**
     * Removes the given product from the order
     * 
     * @param product
     */
    public void removeProduct(Product product) {
        // Get the order row
        OrderRow row = getOrderRowForProduct(product);

        // Check that the row isn't null, if it isn't then remove it from the
        // list
        if (row != null) {
            getOrderedProducts().remove(row);
        }
    }

    /**
     * Fetch the order row for the given product
     * 
     * @param product
     * @return Returns the OrderRow if product already exists in the orders,
     *         otherwise returns null
     */
    private OrderRow getOrderRowForProduct(Product product) {
        // Loop through all order rows
        for (OrderRow row : getOrderedProducts()) {
            if (row.getProduct().equals(product)) {
                return row;
            }
        }

        return null;
    }

}
