package org.vaadin.simpleshop.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.vaadin.appfoundation.i18n.FieldTranslation;

/**
 * Entity class for users. This class keeps information about registered users.
 * 
 * @author Kim
 * 
 */
@Entity
@Table(name = "shopuser")
public class User extends org.vaadin.appfoundation.authentication.data.User {

    private static final long serialVersionUID = 1012189918605244977L;

    protected String username;

    protected String password;

    // Provide the translated name for this field
    @FieldTranslation(tuid = "PROFILE_NAME")
    private String name;

    @FieldTranslation(tuid = "PROFILE_STREET_NAME")
    private String streetName;

    @FieldTranslation(tuid = "PROFILE_ZIP")
    private String zip;

    @FieldTranslation(tuid = "PROFILE_CITY")
    private String city;

    @FieldTranslation(tuid = "PROFILE_EMAIL")
    private String email;

    private Set<Role> roles = new HashSet<Role>();

    public User() {

    }

    /**
     * Get the username of the user
     * 
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for the user
     * 
     * @param username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the hashed password of the user
     * 
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password of the user
     * 
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the actual name of the user
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the actual name of the user
     * 
     * @return
     */
    @Override
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

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
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
