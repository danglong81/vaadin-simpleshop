package org.vaadin.simpleshop.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Utility class containing methods for handling number.
 * 
 * @author Kim
 * 
 */
public class NumberUtil {

    /**
     * Round the sum to the given precision.
     * 
     * @param sum
     * @return
     */
    public static String roundSum(double sum, int precision) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(precision);
        nf.setMinimumFractionDigits(precision);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(sum);
    }

}
