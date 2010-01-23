package org.vaadin.simpleshop;

import org.vaadin.simpleshop.data.User;

/**
 * A static class which holds the User instance of the inlogged user
 * 
 * @author Kim
 * 
 */
public class CurrentUser {

    private static ThreadLocal<User> currentUser = new ThreadLocal<User>();

    public static void setUser(User user) {
        currentUser.set(user);
    }

    public static User get() {
        return currentUser.get();
    }

}
