package com.vaadin.incubator.simpleshop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.incubator.simpleshop.lang.SystemMsg;

/**
 * Use this annotation to define a translation for a field name. This
 * translation is used for example by field factories to set the caption for the
 * fields.
 * 
 * @author Kim
 * 
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldTranslation {
    public SystemMsg name();
}
