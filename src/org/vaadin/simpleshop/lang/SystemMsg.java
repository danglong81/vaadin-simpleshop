package org.vaadin.simpleshop.lang;

import java.util.Locale;

import org.vaadin.appfoundation.i18n.InternationalizationServlet;

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
    GENERIC_CANCEL,
    GENERIC_OK,
    GENERIC_ADMINISTRATION,
    GENERIC_LOGOUT,

    REGISTER_REGISTER_CAPTION,
    REGISTER_VERIFY_PASSWORD,
    REGISTER_REGISTER_BTN,

    LOGIN_LOGIN_FAILED,
    LOGIN_LOGIN_CAPTION,
    LOGIN_FORGOT_PASSWORD,
    LOGIN_REGISTER_BTN,
    LOGIN_LOGIN_BTN,

    APPLICATION_TITLE,
    APPLICATION_WELCOME_USER,
    APPLICATION_USER_X_LOGGED_OUT,

    CART_CHECKOUT,
    CART_PRODUCT,
    CART_QTY,
    CART_SUM,
    CART_TOTAL_SUM_CAPTION_INCL_VAT,
    CART_TOTAL_SUM_CAPTION_EXCL_VAT,

    SHOP_CAPTION,

    ACCOUNT_TOO_SHORT_PASSWORD,
    ACCOUNT_TOO_SHORT_USERNAME,
    ACCOUNT_PASSWORDS_DO_NOT_MATCH,
    ACCOUNT_USERNAME_TAKEN,
    REGISTER_REGISTRATION_COMPLETED,

    GENERAL_ERROR,

    PROFILE_CURRENT_PASSWORD,
    PROFILE_NEW_PASSWORD,
    PROFILE_VERIFY_NEW_PASSWORD,
    PROFILE_CHANGE_PWD_BTN,
    PROFILE_PASSWORD_CHANGED,
    PROFILE_WRONG_PASSWORD,
    PROFILE_CHANGE_PASSWORD,

    PROFILE_CHANGE_CONTACT_INFORMATION,
    PROFILE_NAME,
    PROFILE_UPDATE_PROFILE,
    PROFILE_UPDATED,
    PROFILE_STREET_NAME,
    PROFILE_ZIP,
    PROFILE_CITY,
    PROFILE_EMAIL,
    PROFILE_INVALID_EMAIL,

    CHECKOUT_PREVIOUS_STEP,
    CHECKOUT_NEXT_STEP,
    CHECKOUT_VERIFY_CONTENT,
    CHECKOUT_CHOOSE_DELIVERY_METHOD,
    CHECKOUT_CONTACT_INFORMATION,
    CHECKOUT_PAYMENT,
    CHECKOUT_PAYMENT_METHOD,
    CHECKOUT_DELIVERY_ADDRESS,
    CHECKOUT_INVALID_EMAIL,
    CHECKOUT_ERROR_CHOOSE_DELIVERY_METHOD,
    CHECKOUT_ERROR_CHOOSE_PAYMENT_METHOD,
    CHECKOUT_PAY_BTN,

    ORDER_NAME,
    ORDER_STREET_NAME,
    ORDER_ZIP,
    ORDER_CITY,
    ORDER_PHONE,
    ORDER_EMAIL,
    ORDER_COMMENTS,

    VAT_NAME,
    VAT_PERCENTAGE,
    VAT_VALID_FROM,
    VAT_VALID_UNTIL,

    PRICE_NAME,
    PRICE_PRICE,
    PRICE_VAT,
    PRICE_VALID_FROM,
    PRICE_VALID_UNTIL,

    DELIVERY_METHOD_NAME,
    DELIVERY_DESCRIPTION,
    DELIVERY_PRICE,
    DELIVERY_VALID_FROM,
    DELIVERY_VALID_UNTIL,

    PAYMENT_SELECT_PAYMENT_METHOD,

    CONTACT_INFO_VIEW,
    TERMS_VIEW,
    TODO;

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
