package cn.topstream.app.utils;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 *
 * @author RenYuLiang
 */
public final class BigDecimalUtils {

    /**
     * 禁止实例化
     */
    private BigDecimalUtils() {
        throw new AssertionError(">>>>>>>>类[BigDecimalUtils]禁止实例化<<<<<<<<");
    }

    /**
     * 大于
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return boolean
     */
    public static boolean gt(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return 1 == BigDecimalUtils.compareTo(var1, var2);
    }

    /**
     * 大于等于
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return boolean
     */
    public static boolean gte(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return 0 <= BigDecimalUtils.compareTo(var1, var2);
    }

    /**
     * 小于
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return boolean
     */
    public static boolean lt(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return -1 == BigDecimalUtils.compareTo(var1, var2);
    }

    /**
     * 小于等于
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return boolean
     */
    public static boolean lte(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return 0 >= BigDecimalUtils.compareTo(var1, var2);
    }

    /**
     * 等于
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return boolean
     */
    public static boolean et(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return 0 == BigDecimalUtils.compareTo(var1, var2);
    }

    /**
     * 比较大小
     *
     * @param var1 BigDecimal
     * @param var2 BigDecimal
     * @return int
     */
    private static int compareTo(@NonNull final BigDecimal var1, @NonNull final BigDecimal var2) {
        return var1.compareTo(var2);
    }

}
