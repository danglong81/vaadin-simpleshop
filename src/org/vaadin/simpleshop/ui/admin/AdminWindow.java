package org.vaadin.simpleshop.ui.admin;

import org.vaadin.simpleshop.data.ActionLock;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.util.PermissionsUtil;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

/**
 * The administration views are opened in a modal popup. This is class is the
 * main window for the administration views.
 * 
 * @author Kim
 * 
 */
public class AdminWindow extends Window {

    private static final long serialVersionUID = 1054451935082620494L;

    private TabSheet tabs = new TabSheet();

    public AdminWindow() {
        setCaption(SystemMsg.GENERIC_ADMINISTRATION.get());
        setModal(true);
        setWidth("90%");
        setHeight("90%");

        getContent().setSizeFull();
        initTabs();
    }

    /**
     * Check which actions the user has access to and adds the appropriate views
     * to the main TabSheet.
     */
    private void initTabs() {
        tabs.setSizeFull();
        tabs.setStyleName("minimal");

        addComponent(tabs);

        if (PermissionsUtil.hasAccess(ActionLock.MANAGE_INVENTORY)) {
            tabs.addTab(new Label("Inventory"), "Inventory", null);
        }

        if (PermissionsUtil.hasAccess(ActionLock.MANAGE_ORDERS)) {
            tabs.addTab(new Label("Orders"), "Orders", null);
        }

        if (PermissionsUtil.hasAccess(ActionLock.MANAGE_CUSTOMERS)) {
            tabs.addTab(new Label("Customers"), "Customers", null);
        }

        if (PermissionsUtil.hasAccess(ActionLock.MANAGE_ACL)) {
            tabs.addTab(new Label("Access control"), "Access control", null);
        }
    }
}
