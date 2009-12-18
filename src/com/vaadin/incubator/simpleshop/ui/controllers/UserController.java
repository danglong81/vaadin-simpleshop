package com.vaadin.incubator.simpleshop.ui.controllers;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.incubator.simpleshop.data.User;
import com.vaadin.incubator.simpleshop.facade.FacadeFactory;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.util.ConfigUtil;
import com.vaadin.incubator.simpleshop.util.PasswordUtil;

/**
 * Controller for managing user accounts
 * 
 * @author Kim
 * 
 */
public class UserController {

    /**
     * List of all possible error that can occur during registration
     * 
     * @author Kim
     * 
     */
    public static enum RegistrationError {
        TOO_SHORT_PASSWORD(
                SystemMsg.ACCOUNT_TOO_SHORT_PASSWORD.get(ConfigUtil
                        .getInt("password.length.min"))),
        TOO_SHORT_USERNAME(
                SystemMsg.ACCOUNT_TOO_SHORT_USERNAME.get(ConfigUtil
                        .getInt("username.length.min"))),
        PASSWORDS_DO_NOT_MATCH(
                SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH.get()),
        USERNAME_TAKEN(
                SystemMsg.ACCOUNT_USERNAME_TAKEN.get()),
        REGISTRATION_COMPLETED(
                SystemMsg.REGISTER_REGISTRATION_COMPLETED.get()),
        ERROR(
                SystemMsg.GENERAL_ERROR.get());

        String msg;

        private RegistrationError(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    };

    public static enum ProfileError {
        TOO_SHORT_PASSWORD(
                SystemMsg.ACCOUNT_TOO_SHORT_PASSWORD.get(ConfigUtil
                        .getInt("password.length.min"))),
        PASSWORDS_DO_NOT_MATCH(
                SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH.get()),
        PASSWORD_CHANGED(
                SystemMsg.PROFILE_PASSWORD_CHANGED.get()),
        WRONG_PASSWORD(
                SystemMsg.PROFILE_WRONG_PASSWORD.get()),
        ERROR(
                SystemMsg.GENERAL_ERROR.get());

        String msg;

        private ProfileError(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }

    /**
     * Get the user object with the given primary key
     * 
     * @param id
     * @return
     */
    public static User getUser(Long id) {
        return FacadeFactory.getFacade().find(User.class, id);
    }

    /**
     * This method tries to register a new user
     * 
     * @param username
     *            Desired username
     * @param password
     *            Desired password
     * @param verifyPassword
     *            Verification of the desired password
     * @return
     */
    public static RegistrationError registerUser(String username,
            String password, String verifyPassword) {
        // Make sure that the username and password fulfill the minimum size
        // requirements.
        if (username == null
                || username.length() < ConfigUtil.getInt("username.length.min")) {
            return RegistrationError.TOO_SHORT_USERNAME;
        } else if (password == null
                || password.length() < ConfigUtil.getInt("password.length.min")) {
            return RegistrationError.TOO_SHORT_PASSWORD;
        }
        // Make sure that the password is verified correctly
        else if (!password.equals(verifyPassword)) {
            return RegistrationError.PASSWORDS_DO_NOT_MATCH;
        }
        // Make sure the username is available
        else if (!checkUsernameAvailability(username)) {
            return RegistrationError.USERNAME_TAKEN;
        }

        // Everything is ok, create the user
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.generateHashedPassword(password));

        FacadeFactory.getFacade().store(user);

        return RegistrationError.REGISTRATION_COMPLETED;
    }

    /**
     * Checks if the given username is available.
     * 
     * @param username
     *            Desired username
     * @return Returns true if the username doesn't exist, false if it exists
     */
    private static boolean checkUsernameAvailability(String username) {
        String query = "SELECT u FROM User u WHERE u.username = :username";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("username", username);

        User user = FacadeFactory.getFacade().find(query, parameters);

        return user != null ? false : true;
    }

    /**
     * Change the password of the given user. Verifies that the new password
     * meets all the set conditions.
     * 
     * @param user
     * @param currentPassword
     * @param newPassword
     * @param verifiedNewPassword
     * @return
     */
    public static ProfileError changePassword(User user,
            String currentPassword, String newPassword,
            String verifiedNewPassword) {

        // Verify that the current password is correct
        if (!PasswordUtil.verifyPassword(user, currentPassword)) {
            return ProfileError.WRONG_PASSWORD;
        }

        // Check the new password's constraints
        if (newPassword == null
                || newPassword.length() < ConfigUtil
                        .getInt("password.length.min")) {
            return ProfileError.TOO_SHORT_PASSWORD;
        } else if (!newPassword.equals(verifiedNewPassword)) {
            return ProfileError.PASSWORDS_DO_NOT_MATCH;
        }

        // Password is ok, hash it and change it
        user.setPassword(PasswordUtil.generateHashedPassword(newPassword));
        // Store the user
        FacadeFactory.getFacade().store(user);

        return ProfileError.PASSWORD_CHANGED;
    }

    /**
     * Stores to the database any changes made to the user object
     * 
     * @param user
     */
    public static void storeUser(User user) {
        FacadeFactory.getFacade().store(user);
    }
}
