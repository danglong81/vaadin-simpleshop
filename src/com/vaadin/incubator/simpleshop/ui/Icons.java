package com.vaadin.incubator.simpleshop.ui;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

/**
 * This enum class contains names and ThemeResources for icons used in the
 * application
 * 
 * @author Kim
 * 
 */
public enum Icons {

    USER_PROFILE(
            "profile_btn.png"),
    SHOPPING_CART(
            "cart_btn.png");

    Resource resource;

    private Icons(String path) {
        resource = new ThemeResource("icons/" + path);
    }

    public Resource getResource() {
        return resource;
    }

}
