package com.vaadin.incubator.simpleshop.ui;

import java.lang.reflect.Field;

import com.vaadin.incubator.simpleshop.annotations.FieldTranslation;
import com.vaadin.incubator.simpleshop.util.LogUtil;
import com.vaadin.ui.DefaultFieldFactory;

/**
 * This class is meant to be extended by all field factories in the application.
 * The only feature this class provides is the fetching of translations for the
 * fields.
 * 
 * @author Kim
 * 
 */
public abstract class GenericFieldFactory extends DefaultFieldFactory {

    private static final long serialVersionUID = -7106195086736773045L;

    /**
     * Fetch the translated caption of the given field.
     * 
     * @param c
     * @param fieldName
     * @return
     */
    protected String getCaption(Class<?> c, String fieldName) {
        Field field = getField(c, fieldName);
        if (field != null && field.isAnnotationPresent(FieldTranslation.class)) {
            FieldTranslation translation = field
                    .getAnnotation(FieldTranslation.class);
            return translation.name().get();
        }

        // No translation found, use field name as the default value
        return fieldName.substring(0, 1).toUpperCase().concat(
                fieldName.substring(1));
    }

    /**
     * Recursively search for the given field.
     * 
     * @param c
     * @param fieldName
     * @return
     */
    private Field getField(Class<?> c, String fieldName) {
        // If either the field name or the class is null, then the field cannot
        // be found
        if (c == null || fieldName == null) {
            return null;
        }

        try {
            // Try to get the field
            return c.getDeclaredField(fieldName);
        } catch (SecurityException e) {
            // An exception occured, log it
            LogUtil.warning(e);
            return null;
        } catch (NoSuchFieldException e) {
            // The field didn't exist, maybe it exists in the superclass?
            return getField(c.getSuperclass(), fieldName);
        }
    }
}
