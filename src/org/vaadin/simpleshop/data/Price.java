package org.vaadin.simpleshop.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.vaadin.simpleshop.annotations.FieldTranslation;
import org.vaadin.simpleshop.lang.SystemMsg;

/**
 * Entity class for prices. Prices can be used in various location, such as
 * products and delivery methods.
 * 
 * @author Kim
 * 
 */
@Entity
public class Price extends AbstractPojo {

    @FieldTranslation(name = SystemMsg.PRICE_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.PRICE_PRICE)
    private double price;

    @FieldTranslation(name = SystemMsg.PRICE_VAT)
    @ManyToOne
    private Vat vat;

    @FieldTranslation(name = SystemMsg.PRICE_VALID_FROM)
    private Date validFrom;

    @FieldTranslation(name = SystemMsg.PRICE_VALID_UNTIL)
    private Date validUntil;

    /**
     * Get a name for the price.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set a name for the price. The name can be used as a unique identifier in
     * the GUI. Only visible for shop administrators.
     * 
     * @return
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the actual price sum for this object.
     * 
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price sum for this object.
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get this price's VAT
     * 
     * @return
     */
    public Vat getVat() {
        return vat;
    }

    /**
     * Set the VAT for this price
     * 
     * @param vat
     */
    public void setVat(Vat vat) {
        this.vat = vat;
    }

    /**
     * Get the start date for this price's validity. The price can only be used
     * if the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Set the start date for this price's validity. The price can only be used
     * if the current time is within the validity range.
     * 
     * @return
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Get the end date for this price's validity. The price can only be used if
     * the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidUntil() {
        return validUntil;
    }

    /**
     * Set the start date for this price's validity. The price can only be used
     * if the current time is within the validity range.
     * 
     * @return
     */
    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

}
