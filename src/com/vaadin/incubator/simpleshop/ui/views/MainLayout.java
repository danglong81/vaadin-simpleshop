package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout {

    private static final long serialVersionUID = -6616394129999558904L;

    private final TabSheet mainTabs = new TabSheet();

    public MainLayout() {
        initLayout();
        initTabs();
        setSizeFull();
    }

    private void initLayout() {
        setMargin(true);
        mainTabs.setWidth("950px");
        mainTabs.setHeight("100%");

        addComponent(mainTabs);
        setComponentAlignment(mainTabs, Alignment.TOP_CENTER);
    }

    private void initTabs() {
        mainTabs.addComponent(new ShopView());
    }

}
