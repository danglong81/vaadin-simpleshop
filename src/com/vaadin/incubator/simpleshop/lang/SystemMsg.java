package com.vaadin.incubator.simpleshop.lang;

import java.util.Locale;

import com.vaadin.incubator.simpleshop.InternationalizationServlet;

/**
 * Translation messages in the system
 * 
 * @author Kim
 * 
 */
public enum SystemMsg {

    GENERIC_USERNAME,
    GENERIC_PASSWORD,
    GENERIC_USER_PROFILE,
    GENERIC_SHOPPING_CART,
    GENERIC_ADD,

    REGISTER_REGISTER_CAPTION,

    LOGIN_LOGIN_FAILED,
    LOGIN_LOGIN_CAPTION,
    LOGIN_FORGOT_PASSWORD,
    LOGIN_REGISTER_BTN,
    LOGIN_LOGIN_BTN,

    APPLICATION_TITLE,

    CART_CHECKOUT,
    CART_PRODUCT,
    CART_QTY,
    CART_SUM,

    SHOP_CAPTION,

    ACCOUNT_TOO_SHORT_PASSWORD,
    ACCOUNT_TOO_SHORT_USERNAME,
    ACCOUNT_PASSWORDS_DO_NOT_MATCH,
    ACCOUNT_USERNAME_TAKEN,
    REGISTER_REGISTRATION_COMPLETED,

    GENERAL_ERROR;

    /**
     * Get the translation for this message
     * 
     * @return
     */
    public String get() {
        return InternationalizationServlet.getMessage(Locale.getDefault()
                .getLanguage(), name());
    }

    /**
     * Get the translation for this message
     * 
     * @param params
     *            Any parameters required by the translation message
     * @return
     */
    public String get(Object... params) {
        String msg = InternationalizationServlet.getMessage(Locale.getDefault()
                .getLanguage(), name());

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                String value = String.valueOf(params[i]);
                msg = msg.replace("{" + i + "}", value);
            }
        }

        return msg;
    }

}
