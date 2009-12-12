package com.vaadin.incubator.simpleshop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.vaadin.incubator.simpleshop.data.User;

/**
 * Utility class containing useful helper methods related to passwords.
 * 
 * @author Kim
 * 
 */
public class PasswordUtil {

    /**
     * Verify if the given password (unhashed) matches with the user's password
     * 
     * @param user
     *            User to whome's password we are comparing
     * @param password
     *            The unhashed password we are comparing
     * @return Returns true if passwords match, otherwise false
     */
    public static boolean verifyPassword(User user, String password) {
        // Return null if either the username or password is null
        if (user == null || password == null) {
            return false;
        }

        // Hash the generated password
        String hashedPassword = generateHashedPassword(password);

        // Check if the password matches with the one stored in the User object
        if (user.getPassword().equals(hashedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates a hashed password of the given string.
     * 
     * @param password
     *            String which is to be hashed
     * @return
     */
    public static String generateHashedPassword(String password) {
        StringBuffer hashedPassword = new StringBuffer();

        // Get a byte array of the password concatenated with the password salt
        // value
        byte[] defaultBytes = (password + ConfigUtil.getString("password.salt"))
                .getBytes();
        try {
            // Perform the hashing with SHA
            MessageDigest algorithm = MessageDigest.getInstance("SHA");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                hashedPassword.append(Integer
                        .toHexString(0xFF & messageDigest[i]));
            }
        } catch (NoSuchAlgorithmException nsae) {

        }

        return hashedPassword.toString();
    }
}
