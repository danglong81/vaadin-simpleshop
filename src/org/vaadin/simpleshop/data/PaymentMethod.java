package org.vaadin.simpleshop.data;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.PrivateOwned;
import org.vaadin.appfoundation.i18n.FieldTranslation;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class PaymentMethod extends AbstractPojo {

    private static final long serialVersionUID = -606144615145954832L;

    @FieldTranslation(tuid = "PAYMENT_METHOD_NAME")
    private String name;

    @FieldTranslation(tuid = "PAYMENT_DESCRIPTION")
    private String description;

    @FieldTranslation(tuid = "PAYMENT_PRICE")
    @OneToOne(cascade = CascadeType.ALL)
    @PrivateOwned
    private Price price;

    @FieldTranslation(tuid = "PAYMENT_VALID_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @FieldTranslation(tuid = "PAYMENT_VALID_UNTIL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    /**
     * Gets the name for this payment method.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name for this payment method.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of this payment method.
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this payment method.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the price for this payment method. Price is returned as an instance
     * of the Price object.
     * 
     * @return
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Set the price object for this payment method.
     * 
     * @param price
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * Get the start date for this payment method's validity. The payment method
     * can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the start date for this payment method's validity. The payment
     * method can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Get the end date for this payment method's validity. The payment method
     * can only be used if the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidUntil() {
        return validUntil;
    }

    /**
     * Sets the end date for this payment method's validity. The payment method
     * can only be used if the current time is within the validity range.
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

    public void setType(PaymentMethodType type) {
        this.type = type;
    }

    public PaymentMethodType getType() {
        return type;
    }

}
