package org.vaadin.simpleshop.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;
import org.vaadin.simpleshop.annotations.FieldTranslation;
import org.vaadin.simpleshop.lang.SystemMsg;

/**
 * Entity class for value added taxes.
 * 
 * @author Kim
 * 
 */
@Entity
public class Vat extends AbstractPojo {

    private static final long serialVersionUID = -4733123592750920398L;

    @FieldTranslation(name = SystemMsg.VAT_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.VAT_PERCENTAGE)
    private double percentage;

    @FieldTranslation(name = SystemMsg.VAT_VALID_FROM)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @FieldTranslation(name = SystemMsg.VAT_VALID_UNTIL)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    /**
     * Default constructor
     */
    public Vat() {
    }

    /**
     * Constructor which takes the name and the VAT percentage as parameters
     */
    public Vat(String name, double percentage) {
        setName(name);
        setPercentage(percentage);
    }

    /**
     * Sets a name for this VAT object.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of this VAT object
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the actual percentage value for this VAT
     * 
     * @param percentage
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Get this VAT's actual percentage
     * 
     * @return
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Set the start date for this VAT's validity. The VAT can only be used if
     * the current time is within the validity range.
     * 
     * @return
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Get the start date for this VAT's validity. The VAT can only be used if
     * the current time is within the validity range.
     * 
     * @return
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Set the end date for this VAT's validity. The VAT can only be used if the
     * current time is within the validity range.
     * 
     * @return
     */
    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Get the end date for this VAT's validity. The VAT can only be used if the
     * current time is within the validity range.
     * 
     * @return
     */
    public Date getValidUntil() {
        return validUntil;
    }
}
