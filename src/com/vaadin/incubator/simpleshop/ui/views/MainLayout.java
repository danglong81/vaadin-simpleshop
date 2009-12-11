package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * This is the main layout for the entire application.
 * 
 * @author Kim
 * 
 */
public class MainLayout extends VerticalLayout {

    private static final long serialVersionUID = -6616394129999558904L;

    private final TabSheet mainTabs = new TabSheet();

    public MainLayout() {
        // Initialize layout
        initLayout();
        initTabs();
        setSizeFull();
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

    /**
     * Initialize the TabSheet
     */
    private void initTabs() {
        // Add the shop view to the TabSheet
        mainTabs.addComponent(new ShopView());
    }

}
