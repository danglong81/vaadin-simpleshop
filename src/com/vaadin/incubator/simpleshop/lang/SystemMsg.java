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

    REGISTER_REGISTER_CAPTION,
    LOGIN_LOGIN_FAILED;

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
