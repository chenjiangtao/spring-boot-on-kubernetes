package cn.topstream.app.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date的format(), 采用Apache Common Lang3中线程安全, 性能更佳的FastDateFormat
 * 1. 常用格式的FastDateFormat定义, 常用格式直接使用这些FastDateFormat
 *
 * @author RenYuLiang
 */
public final class DateFormatUtils {

    /**
     * 日期格式
     */
    public static final String[] PARSE_PATTERNS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss",
            "yyyyMMdd", "yyyyMMdd HH:mm", "yyyyMMdd HH:mm:ss",
            "yyyy年MM月dd日", "yyyy年MM月dd日 HH:mm", "yyyy年MM月dd日 HH:mm:ss"
    };
    /**
     * 以空格分隔日期和时间，不带时区信息
     */
    public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";


    /**
     * 禁止实例化
     */
    private DateFormatUtils() {
        throw new AssertionError(">>>>>>>>类[DateFormatUtils]禁止实例化<<<<<<<<");
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 格式化日期, 仅用于pattern不固定的情况
     * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找
     *
     * @param pattern String
     * @param date    Date
     * @return Date
     */
    public static String formatDate(final String pattern, final Date date) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 转换 Date
     *
     * @param str String
     * @return Date
     */
    public static Date parseDateByDateUtils(final String str) {
        return DateFormatUtils.parseDateByDateUtils(str, PARSE_PATTERNS);
    }

    /**
     * 转换 Date
     *
     * @param str           String
     * @param parsePatterns String
     * @return Date
     */
    public static Date parseDateByDateUtils(final String str, final String... parsePatterns) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str, parsePatterns);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否是日期格式 默认 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr String
     * @return boolean
     */
    public static boolean isDateString(final String dateStr) {
        return DateFormatUtils.isDateString(dateStr, DateFormatUtils.PATTERN_DEFAULT_ON_SECOND);
    }

    /**
     * 是否是日期格式
     *
     * @param dateStr    String
     * @param dateFormat String 格式
     * @return boolean
     */
    public static boolean isDateString(final String dateStr, final String dateFormat) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        try {
            SimpleDateFormat fmt = SimpleDateFormatFactory
                    .getInstance(dateFormat);
            // 设置 lenient 为 false
            // 否则 SimpleDateFormat 会比较宽松地验证日期 比如 2007/02/29 会被接受 并转换成 2007/03/01
            fmt.setLenient(false);
            fmt.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是日期格式
     *
     * @param dateStr String
     * @return boolean
     */
    public static boolean isDate(final String dateStr) {
        return DateFormatUtils.isDate(dateStr, PARSE_PATTERNS);
    }

    /**
     * 是否是日期格式
     *
     * @param dateStr String
     * @return boolean
     */
    public static boolean isDate(final String dateStr, final String... parsePatterns) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        try {
            org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, parsePatterns);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
