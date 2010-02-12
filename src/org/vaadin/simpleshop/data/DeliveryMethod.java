package org.vaadin.simpleshop.data;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.PrivateOwned;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;
import org.vaadin.simpleshop.annotations.FieldTranslation;
import org.vaadin.simpleshop.lang.SystemMsg;

/**
 * Entity class for defining different delivery methods.
 * 
 * @author Kim
 * 
 */
@Entity
public class DeliveryMethod extends AbstractPojo {

    private static final long serialVersionUID = 530854220431776974L;

    @FieldTranslation(name = SystemMsg.DELIVERY_METHOD_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.DELIVERY_DESCRIPTION)
    private String description;

    @FieldTranslation(name = SystemMsg.DELIVERY_PRICE)
    @OneToOne(cascade = CascadeType.ALL)
    @PrivateOwned
    private Price price;

    @FieldTranslation(name = SystemMsg.DELIVERY_VALID_FROM)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @FieldTranslation(name = SystemMsg.DELIVERY_VALID_UNTIL)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    /**
     * Gets the name for this delivery method.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name for this delivery method.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of this delivery method.
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this delivery method.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the price for this delivery method. Price is returned as an instance
     * of the Price object.
     * 
     * @return
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Set the price object for this delivery method.
     * 
     * @param price
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * Get the start date for this delivery method's validity. The delivery
     * method can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the start date for this delivery method's validity. The delivery
     * method can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Get the end date for this delivery method's validity. The delivery method
     * can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidUntil() {
        return validUntil;
    }

    /**
     * Sets the end date for this delivery method's validity. The delivery
     * method can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Transient method for getting the price including the VAT
     * 
     * @return
     */
    public double getPriceIncludingVAT() {
        return getPrice().getPrice()
                * ((getPrice().getVat().getPercentage() / 100) + 1);
    }

    /**
     * Transient method for getting the price excluding the VAT
     * 
     * @return
     */
    public double getPriceExcludingVAT() {
        return getPrice().getPrice();
    }
}
