package org.vaadin.simpleshop.util;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.simpleshop.data.ActionLock;
import org.vaadin.simpleshop.data.Role;
import org.vaadin.simpleshop.data.User;

/**
 * Utility class for ACL
 * 
 * @author Kim
 * 
 */
public class PermissionsUtil {

    /**
     * Checks if the current user has access to this action
     * 
     * @param lock
     * @return
     */
    public static boolean hasAccess(ActionLock lock) {
        return hasAccess(lock, (User) SessionHandler.get());
    }

    /**
     * Checks if the given user has access to this action
     * 
     * @param lock
     * @param user
     * @return
     */
    public static boolean hasAccess(ActionLock lock, User user) {
        // If the current user is null, then the user is not logged in and thus
        // does not have access to the given action
        if (user == null) {
            return false;
        }

        // Get all user's roles
        if (user.getRoles() != null) {
            // Loop through all user's roles
            for (Role role : user.getRoles()) {
                // Loop through all actions this role has access to
                for (ActionLock l : role.getLocks()) {
                    // Check if this action is what we are checking for
                    if (l.equals(lock)) {
                        // The user belongs to a role which has access to the
                        // given action
                        return true;
                    }
                }
            }
        }

        // The user does not have access to the action.
        return false;

    }

    /**
     * Checks if the user belongs to any roles that have even access to at least
     * one administrative action.
     * 
     * @param user
     * @return
     */
    public static boolean isAdmin(User user) {
        if (hasAccess(ActionLock.MANAGE_ACL, user)) {
            return true;
        } else if (hasAccess(ActionLock.MANAGE_CUSTOMERS, user)) {
            return true;
        } else if (hasAccess(ActionLock.MANAGE_INVENTORY, user)) {
            return true;
        } else if (hasAccess(ActionLock.MANAGE_ORDERS, user)) {
            return true;
        }

        return false;
    }

}
