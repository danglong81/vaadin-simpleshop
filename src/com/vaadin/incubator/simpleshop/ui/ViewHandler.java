package com.vaadin.incubator.simpleshop.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewHandler {

    private final Map<Object, ViewItem> viewMap = new HashMap<Object, ViewItem>();

    private final Map<Object, ParentView> parentMap = new HashMap<Object, ParentView>();

    /**
     * Add a new View to the ViewHandler. Takes as input a viewId. The user is
     * responsible of setting the view class. If the viewId is already in use,
     * then null is returned.
     * 
     * @param viewId
     * @return
     */
    public ViewItem addView(Object viewId) {
        if (viewId == null) {
            throw new IllegalArgumentException();
        }

        // Check if the viewId is already in use. If it is, then return null.
        if (viewMap.containsKey(viewId)) {
            return null;
        }

        // Create a new ViewItem and add it to the map.
        ViewItem item = new ViewItem(viewId);
        viewMap.put(viewId, item);
        return item;
    }

    /**
     * Add a new view to the view handler.
     * 
     * @param viewId
     *            The view's id
     * @param parent
     *            Parent view for the given view
     * @return
     */
    public ViewItem addView(Object viewId, ParentView parent) {
        ViewItem item = addView(viewId);
        setParent(viewId, parent);
        return item;
    }

    /**
     * Add a new view. Returns the viewId. Make sure to set either the view
     * instance or the view class for the ViewItem.
     * 
     * @return
     */
    public Object addView() {
        Object viewId = UUID.randomUUID();
        ViewItem item = new ViewItem(viewId);
        viewMap.put(viewId, item);
        return viewId;
    }

    /**
     * Fetch the ViewItem for the given viewId. If the viewId is not found, then
     * null is returned.
     * 
     * @param viewId
     * @return
     */
    public ViewItem getViewItem(Object viewId) {
        // Check if the viewId exists in the map
        if (viewId != null && viewMap.containsKey(viewId)) {
            return viewMap.get(viewId);
        }

        return null;
    }

    /**
     * Removes the ViewItem from the handler for the given viewId.
     * 
     * @param viewId
     * @return Returns true if the viewId existed, otherwise false.
     */
    public boolean removeView(Object viewId) {
        if (viewId != null && viewMap.containsKey(viewId)) {
            viewMap.remove(viewId);
            return true;
        }

        return false;
    }

    /**
     * Activate the view with the given viewId. You can specify any given amount
     * of parameters for the activation request. Each parameter is forwarded to
     * the View's activated() method.
     * 
     * @param viewId
     * @param params
     */
    public void activateView(Object viewId, Object... params) {
        if (viewId != null && viewMap.containsKey(viewId)
                && parentMap.containsKey(viewId)) {
            // Get the ViewItem and parent for this viewId
            ViewItem item = viewMap.get(viewId);
            ParentView parent = parentMap.get(viewId);

            // Tell the parent tp activate the given view
            parent.activate(item.getView());

            // Tell the view that it has been activated
            item.getView().activated(params);
        }
    }

    /**
     * Set the parent view for the given viewId.
     * 
     * @param viewId
     * @param parent
     */
    public void setParent(Object viewId, ParentView parent) {
        if (viewId != null && parent != null && viewMap.containsKey(viewId)) {
            parentMap.put(viewId, parent);
        }
    }

}
