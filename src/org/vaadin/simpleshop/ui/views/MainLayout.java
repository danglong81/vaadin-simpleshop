package org.vaadin.simpleshop.ui.views;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ui.checkout.CheckoutView;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This is the main layout for the entire application.
 * 
 * @author Kim
 * 
 */
public class MainLayout extends VerticalLayout implements ViewContainer {

    private static final long serialVersionUID = -6616394129999558904L;

    private final TabSheet mainTabs = new TabSheet();

    private AbstractView<?> currentShopView;

    public MainLayout() {
        // Initialize layout
        initLayout();
        setSizeFull();

        currentShopView = ViewHandler.addView(ShopView.class, this).getView();
        ViewHandler.addView(CheckoutView.class, this);

        mainTabs.addComponent(currentShopView);
    }

    /**
     * Initializes the main layout
     */
    private void initLayout() {
        // Add a margin and specify the width and height;
        setMargin(true);
        mainTabs.setWidth("950px");
        mainTabs.setHeight("100%");

        // Add the TabSheet to the main layout
        addComponent(mainTabs);

        // Set alignment for the tabs
        setComponentAlignment(mainTabs, Alignment.TOP_CENTER);
    }

    @Override
    public void activate(AbstractView<?> view) {
        mainTabs.replaceComponent(currentShopView, view);
        currentShopView = view;
    }

}
