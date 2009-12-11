package com.vaadin.incubator.simpleshop.util;

import com.vaadin.incubator.simpleshop.CurrentUser;
import com.vaadin.incubator.simpleshop.data.ActionLock;
import com.vaadin.incubator.simpleshop.data.Role;
import com.vaadin.incubator.simpleshop.data.User;

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
        // Get the current user
        User user = CurrentUser.get();
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

}
