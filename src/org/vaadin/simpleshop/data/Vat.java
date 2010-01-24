package org.vaadin.simpleshop.data;

import java.util.Date;

import javax.persistence.Entity;

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

    @FieldTranslation(name = SystemMsg.VAT_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.VAT_PERCENTAGE)
    private double percentage;

    @FieldTranslation(name = SystemMsg.VAT_VALID_FROM)
    private Date validFrom;

    @FieldTranslation(name = SystemMsg.VAT_VALID_UNTIL)
    private Date validUntil;

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
