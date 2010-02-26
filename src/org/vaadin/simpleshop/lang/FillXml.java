package org.vaadin.simpleshop.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class for maintaining the translations.xml file. This file
 * will look for any translations defined in the SystemMsg-file but which have
 * not yet been added to the translations.xml. This class will then create stubs
 * for those missing translations.
 * 
 * @author Kim
 * 
 */
public class FillXml {

    public static void main(String[] args) {
        File file = new File("src/translations.xml");
        List<String> identifiers = new ArrayList<String>();

        for (SystemMsg msg : SystemMsg.values()) {
            identifiers.add(msg.name());
        }

        try {
            org.vaadin.appfoundation.i18n.FillXml.updateTranslations(file,
                    new String[] { "en", "fi", "sv" }, identifiers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
