package com.Podzilla.analytics.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MetricCalculator {
    private static final int DEFAULT_SCALE = 2;

    private MetricCalculator() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    /**
     * Calculates a success rate or percentage.
     *
     * @param numerator    The count of successful outcomes (e.g., completed
     *                     deliveries).
     * @param denominator  The total count of attempts (e.g., completed + failed
     *                     deliveries).
     * @param scale        The number of decimal places for the result.
     * @param roundingMode The rounding mode to apply.
     * @return The calculated percentage as a BigDecimal, or BigDecimal.ZERO
     *         if the denominator is zero.
     */
    public static BigDecimal calculateRate(final long numerator,
            final long denominator, final int scale,
            final RoundingMode roundingMode) {
        if (denominator == 0) {
            return BigDecimal.ZERO;
        }
        if (numerator < 0 || denominator < 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(numerator)
                .divide(BigDecimal.valueOf(denominator), scale, roundingMode);
    }

    /**
     * Calculates a success rate or percentage using default scale and rounding.
     *
     * @param numerator   The count of successful outcomes.
     * @param denominator The total count of attempts.
     * @return The calculated percentage (scale 2, HALF_UP rounding), or
     *         BigDecimal.ZERO if denominator is zero.
     */
    public static BigDecimal calculateRate(final long numerator,
            final long denominator) {
        return calculateRate(numerator, denominator, DEFAULT_SCALE,
                RoundingMode.HALF_UP);
    }
}
