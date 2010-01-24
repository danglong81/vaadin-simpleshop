package org.vaadin.simpleshop.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.PrivateOwned;
import org.vaadin.simpleshop.annotations.FieldTranslation;
import org.vaadin.simpleshop.lang.SystemMsg;

/**
 * Entity for orders places in the shop
 * 
 * @author Kim
 * 
 */
@Entity
@Table(name = "shop_order")
public class Order extends AbstractPojo {

    @FieldTranslation(name = SystemMsg.ORDER_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.ORDER_STREET_NAME)
    private String streetName;

    @FieldTranslation(name = SystemMsg.ORDER_ZIP)
    private String zip;

    @FieldTranslation(name = SystemMsg.ORDER_CITY)
    private String city;

    @FieldTranslation(name = SystemMsg.ORDER_PHONE)
    private String phone;

    @FieldTranslation(name = SystemMsg.ORDER_EMAIL)
    private String email;

    @FieldTranslation(name = SystemMsg.ORDER_COMMENTS)
    private String comments;

    @OneToMany(cascade = CascadeType.ALL)
    @PrivateOwned
    private List<OrderRow> orderedProducts = new ArrayList<OrderRow>();

    private String deliveryMethodName;

    private double deliveryMethodPrice;

    private double deliveryMethodVAT;

    public Order() {

    }

    /**
     * Sets the name of the person the delivery is addressed to.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the person the delivery is addressed to.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the street name of the delivery address.
     * 
     * @param streetName
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Returns the street name of the delivery address.
     * 
     * @return
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets the zip code for the delivery address
     * 
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns the zip code for the delivery address
     * 
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the city for the delivery address
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the city of the delivery address
     * 
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the phone number of the person making the delivery
     * 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the phone number of the person making the delivery
     * 
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the email address of the person making the delivery
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the email address of the person making the delivery
     * 
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the free comments of the person making the delivery. This field can
     * be used as a way to deliver a message to the shop keep with the actual
     * order. The field is free text where the user can for example add special
     * requests.
     * 
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Returns the free comments of the person making the delivery
     * 
     * @return
     */
    public String getComments() {
        return comments;
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
            row.setPrice(product.getPrice().getPrice());
            row.setVat(product.getPrice().getVat().getPercentage());
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
            row.setPrice(product.getPrice().getPrice());
            row.setVat(product.getPrice().getVat().getPercentage());
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
