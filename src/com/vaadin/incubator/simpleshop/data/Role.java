package com.vaadin.incubator.simpleshop.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.eclipse.persistence.annotations.BasicCollection;

/**
 * Entity class for Roles used in ACL
 * 
 * @author Kim
 * 
 */
@Entity
public class Role extends AbstractPojo {

    private String name;

    @BasicCollection
    private Set<ActionLock> locks = new HashSet<ActionLock>();

    public Role() {

    }

    /**
     * Define all the actions this role has access to
     * 
     * @param locks
     */
    public void setLocks(Set<ActionLock> locks) {
        this.locks = locks;
    }

    /**
     * Returns all the actions this role has access to
     * 
     * @return
     */
    public Set<ActionLock> getLocks() {
        return locks;
    }

    /**
     * Transient method for adding a lock to the existing set
     * 
     * @param lock
     */
    public void addLock(ActionLock lock) {
        getLocks().add(lock);
    }

    /**
     * Transient method for removing a lock from the set
     * 
     * @param lock
     */
    public void removeLock(ActionLock lock) {
        getLocks().remove(lock);
    }

    /**
     * Set the name for this role
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of this role
     * 
     * @return
     */
    public String getName() {
        return name;
    }

}
