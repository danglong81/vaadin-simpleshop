package org.vaadin.simpleshop.events;

import org.vaadin.simpleshop.data.User;

/**
 * Class for user session events (login/logout)
 * 
 * @author Kim
 * 
 */
public class UserSessionEvent {

    private User user = null;

    /**
     * Default constructor. Remember to set the target user.
     */
    public UserSessionEvent() {

    }

    /**
     * Alternative constructor which takes as parameter the target user.
     * 
     * @param user
     */
    public UserSessionEvent(User user) {
        this.setUser(user);
    }

    /**
     * Sets the user who is the target of this event
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the target user of this event
     * 
     * @return
     */
    public User getUser() {
        return user;
    }
}
