package com.vaadin.incubator.simpleshop.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

/**
 * Entity class for Roles used in ACL
 * 
 * @author Kim
 * 
 */
@Entity
public class Role extends AbstractPojo {

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

}
