package org.vaadin.simpleshop.data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * This class is an abstract superclass for all Entity classes in the
 * application. This class defines variables which are common for all entity
 * classes.
 * 
 * @author Kim
 * 
 */
@MappedSuperclass
abstract public class AbstractPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    @Version
    protected Long consistencyVersion;

    public AbstractPojo() {

    }

    /**
     * Get the primary key for this entity.
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the primary key for this entity. Usually, this method should never be
     * called.
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the concurrency version number for this entity. The concurrency
     * version is a number which is used for optimistic locking in the database.
     * 
     * @return
     */
    public Long getConsistencyVersion() {
        return consistencyVersion;
    }

    /**
     * Set the concurrency version number for this entity. Usually, this method
     * should never be called.
     * 
     * @param consistencyVersion
     */
    public void setConsistencyVersion(Long consistencyVersion) {
        this.consistencyVersion = consistencyVersion;
    }

}
