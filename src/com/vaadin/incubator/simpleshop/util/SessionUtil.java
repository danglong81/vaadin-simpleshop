package com.vaadin.incubator.simpleshop.util;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.incubator.simpleshop.CurrentUser;
import com.vaadin.incubator.simpleshop.data.User;
import com.vaadin.incubator.simpleshop.facade.FacadeFactory;

/**
 * A utility class for handling user sessions
 * 
 * @author Kim
 * 
 */
public class SessionUtil {

    /**
     * Try to log in a user with the given user credentials
     * 
     * @param username
     *            Username of the user
     * @param password
     *            Password of the user
     * @return Returns true if login was successfull, otherwise false
     */
    public static boolean login(String username, String password) {
        // Login fails if either the username or password is null
        if (username == null || password == null) {
            return false;
        }

        // Create a query which searches the database for a user with the given
        // username
        String query = "SELECT u FROM User u WHERE u.username = :username";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("username", username);
        User user = (User) FacadeFactory.getFacade().find(query, parameters);

        // Check if the user exists
        if (user != null) {
            // Check if the user's password is correct
            if (PasswordUtil.verifyPassword(user, password)) {
                // The user's password was correct, so set the user as the
                // current user (inlogged)
                CurrentUser.setUser(user);

                return true;
            }
        }
        // Either the username didn't exist or the passwords did not match
        return false;
    }

    /**
     * Method for logging out a user
     */
    public static void logout() {
        CurrentUser.setUser(null);
    }

}
