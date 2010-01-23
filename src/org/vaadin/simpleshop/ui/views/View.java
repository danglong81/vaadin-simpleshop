package org.vaadin.simpleshop.ui.views;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

/**
 * Interface which all main views should implement
 * 
 * @author Kim
 * 
 */
public abstract class View<A extends Layout> extends CustomComponent {

    private static final long serialVersionUID = -1420553541682132603L;

    protected A mainLayout;

    protected View(A layout) {
        setCompositionRoot(layout);
        mainLayout = layout;
        setSizeFull();
    }

    /**
     * This method is called when the view is activated.
     */
    public abstract void activated(Object... params);

}
