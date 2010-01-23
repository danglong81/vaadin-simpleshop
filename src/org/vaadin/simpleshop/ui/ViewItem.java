package org.vaadin.simpleshop.ui;

import org.vaadin.simpleshop.ui.views.View;
import org.vaadin.simpleshop.util.LogUtil;

/**
 * The view item is a a wrapper class used by the ViewHandler. It contains
 * information about the views added to the ViewHandler.
 * 
 * @author Kim
 * 
 */
public class ViewItem {

    private final Object viewId;

    private View<?> view;

    private Class<? extends View<?>> viewClass = null;

    /**
     * Constructor. Takes as input the viewId. If the viewId is an instance of
     * Class<? extends View>, then the viewId is used as the default viewClass.
     * 
     * @param viewId
     */
    @SuppressWarnings("unchecked")
    public ViewItem(Object viewId) {
        if (viewId instanceof Class) {
            if (View.class.isAssignableFrom((Class<?>) viewId)) {
                viewClass = (Class<? extends View<?>>) viewId;
            }

        }
        this.viewId = viewId;
    }

    /**
     * Set the view object for this ViewItem
     * 
     * @param view
     */
    public void setView(View<?> view) {
        this.view = view;
    }

    /**
     * Get the view object for this item. If view isn't set and the viewClass is
     * defined, then an instance of the class is created and returned.
     * 
     * @return
     */
    public View<?> getView() {
        if (view == null && viewClass != null) {
            try {
                view = viewClass.newInstance();
            } catch (InstantiationException e) {
                LogUtil.warning(e);
            } catch (IllegalAccessException e) {
                LogUtil.warning(e);
            }
        }
        return view;
    }

    /**
     * Returns the view id for this ViewItem.
     * 
     * @return
     */
    public Object getViewId() {
        return viewId;
    }

    /**
     * Set the viewClass. This is only required if a view instance is not
     * provided. The viewClass is used for creating an instance of View when
     * getView() is called in case an instance of View is not available.
     * 
     * @param viewClass
     */
    public void setViewClass(Class<? extends View<?>> viewClass) {
        this.viewClass = viewClass;
    }

    /**
     * Get the current view class.
     * 
     * @return
     */
    public Class<? extends View<?>> getViewClass() {
        return viewClass;
    }

}
