package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.ui.ParentView;
import com.vaadin.incubator.simpleshop.ui.ViewHandler;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This is the main layout for the entire application.
 * 
 * @author Kim
 * 
 */
public class MainLayout extends VerticalLayout implements ParentView {

    private static final long serialVersionUID = -6616394129999558904L;

    private final TabSheet mainTabs = new TabSheet();

    private View<?> currentShopView;

    public MainLayout() {
        // Initialize layout
        initLayout();
        // initTabs();
        setSizeFull();

        ViewHandler vh = SimpleshopApplication.getViewHandler();
        currentShopView = vh.addView(ShopView.class, this).getView();
        vh.addView(CheckoutView.class, this);

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
    public void activate(View<?> view) {
        mainTabs.replaceComponent(currentShopView, view);
        currentShopView = view;
    }

}
