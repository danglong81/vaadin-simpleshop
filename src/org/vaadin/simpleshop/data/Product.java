package org.vaadin.simpleshop.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * Entity class for the products in this application.
 * 
 * @author Kim
 * 
 */
@Entity
public class Product extends AbstractPojo {

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @PrivateOwned
    private Price price;

    private String description;

    public Product() {

    }

    /**
     * Set the name of the product. This is used as the title when for products.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the product name.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the price for this product.
     * 
     * @param price
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * Returns the price for this product.
     * 
     * @return
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Set the description of the product. This is the longer introduction of
     * the product which is shown after the user has clicked for more
     * information.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the description of the product.
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Transient method for getting the price without taxes
     */
    public double getPriceExcludingTaxes() {
        return getPrice().getPrice();
    }

    /**
     * Transient method for getting the price with taxes
     */
    public double getPriceIncludingTaxes() {
        return getPrice().getPrice()
                * ((getPrice().getVat().getPercentage() / 100) + 1);
    }

}
