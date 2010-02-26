package org.vaadin.simpleshop.lang;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.authentication.util.UserUtil.ProfileMsg;
import org.vaadin.appfoundation.authentication.util.UserUtil.RegistrationMsg;

/**
 * This class maps the error messages from Vaadin ApplicationFoundation to
 * SystemMsgs.
 * 
 * @author Kim
 * 
 */
public class EnumMsgMapper {

    private static Map<Enum<?>, SystemMsg> map = new HashMap<Enum<?>, SystemMsg>();

    static {
        map.put(ProfileMsg.ERROR, SystemMsg.GENERAL_ERROR);
        map
                .put(ProfileMsg.PASSWORD_CHANGED,
                        SystemMsg.PROFILE_PASSWORD_CHANGED);
        map.put(ProfileMsg.PASSWORDS_DO_NOT_MATCH,
                SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH);
        map.put(ProfileMsg.TOO_SHORT_PASSWORD,
                SystemMsg.ACCOUNT_TOO_SHORT_PASSWORD);
        map.put(ProfileMsg.WRONG_PASSWORD, SystemMsg.PROFILE_WRONG_PASSWORD);
        map.put(RegistrationMsg.ERROR, SystemMsg.GENERAL_ERROR);
        map.put(RegistrationMsg.PASSWORDS_DO_NOT_MATCH,
                SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH);
        map.put(RegistrationMsg.REGISTRATION_COMPLETED,
                SystemMsg.REGISTER_REGISTRATION_COMPLETED);
        map.put(RegistrationMsg.TOO_SHORT_PASSWORD,
                SystemMsg.ACCOUNT_TOO_SHORT_PASSWORD);
        map.put(RegistrationMsg.TOO_SHORT_USERNAME,
                SystemMsg.ACCOUNT_TOO_SHORT_USERNAME);
        map.put(RegistrationMsg.USERNAME_TAKEN,
                SystemMsg.ACCOUNT_USERNAME_TAKEN);
    }

    /**
     * Get the system message corresponding the enumeration.
     * 
     * @param enumeration
     * @return
     */
    public static SystemMsg getMsg(Enum<?> enumeration) {
        return map.get(enumeration);
    }

}
