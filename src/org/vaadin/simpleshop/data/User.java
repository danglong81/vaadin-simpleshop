package org.vaadin.simpleshop.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.vaadin.simpleshop.annotations.FieldTranslation;
import org.vaadin.simpleshop.lang.SystemMsg;


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

    // Provide the translated name for this field
    @FieldTranslation(name = SystemMsg.PROFILE_NAME)
    private String name;

    @FieldTranslation(name = SystemMsg.PROFILE_STREET_NAME)
    private String streetName;

    @FieldTranslation(name = SystemMsg.PROFILE_ZIP)
    private String zip;

    @FieldTranslation(name = SystemMsg.PROFILE_CITY)
    private String city;

    @FieldTranslation(name = SystemMsg.PROFILE_EMAIL)
    private String email;

    private Set<Role> roles = new HashSet<Role>();

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

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Set the roles this user belongs to
     * 
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Get all the roles of this user
     * 
     * @return
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Transient method for adding a role for the user
     * 
     * @param role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * Transient method for removing a role from the user
     * 
     * @param role
     */
    public void removeRole(Role role) {
        getRoles().remove(role);
    }

}
