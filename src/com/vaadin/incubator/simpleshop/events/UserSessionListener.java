package com.vaadin.incubator.simpleshop.events;

/**
 * Interface for listeners for user session events.
 * 
 * Note. If I had this interface defined within the UserSessionEvent, the
 * initialization of the Entity Manager Factory in the JPAFacade would fail.
 * 
 * @author Kim
 * 
 */
public interface UserSessionListener {
    /**
     * Called when a user logs in
     * 
     * @param event
     */
    public void loginEvent(UserSessionEvent event);

    /**
     * Called when a user logs out
     * 
     * @param event
     */
    public void logoutEvent(UserSessionEvent event);
}