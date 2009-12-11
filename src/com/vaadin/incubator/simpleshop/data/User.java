package com.vaadin.incubator.simpleshop.data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for users. This class keeps information about registered users.
 * 
 * @author Kim
 * 
 */
@Entity
@Table(name = "shopuser")
public class User extends AbstractPojo {

    protected String username;

    protected String password;

    private String name;

    public User() {

    }

    /**
     * Get the username of the user
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the hashed password of the user
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password of the user
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the actual name of the user
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the actual name of the user
     * 
     * @return
     */
    public String getName() {
        return name;
    }

}
