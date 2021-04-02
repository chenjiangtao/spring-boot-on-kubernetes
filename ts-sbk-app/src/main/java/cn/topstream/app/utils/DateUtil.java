/*
 * Copyright © 2014-2016 jia-fu.cn All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * TAOLUE Co.,Ltd. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with TAOLUE Co.,Ltd.
 *
 */

package cn.topstream.app.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtil {

    /**
     * 日期常量
     */
    enum DateConstants {
        TODAY(0), NEARLYWEEK(1), MONTH(2), NEARLYMONTH(3);
        private int value;

        DateConstants(int value) {
            this.value = value;
        }

    }

    /**
     * 显示日期的格式,yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 显示日期的格式,yyyy-MM-dd
     */
    public static final String DATE_FORMAT1 = "yyyy/MM/dd";

    /**
     * 显示日期的格式,MM/dd/yyyy
     */
    public static final String DATE_FORMAT2 = "MM/dd/yyyy";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm
     */
    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 显示日期的格式,yyyy-MM
     */
    public static final String DATE_YEAE_MONTH = "yyyy-MM";

    /**
     * 显示日期的格式,yyyy/MM/dd HH:mm:ss
     */
    public static final String TIMEMM_STR_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 显示日期的格式,yyyyMM
     */
    public static final String DATE_YEAE_MONTH_SIMPLE = "yyyyMM";

    /**
     * 显示日期的格式,yyyyMM
     */
    public static final String DATE_YEAE_SIMPLE = "yyyy";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm:ss
     */
    public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm:ss
     */
    public static final String TIMES_FORMAT = "yyyy-MM-dd HH:mm:ss S";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 显示日期的格式,yyyy年MM月dd日HH时mm分ss秒
     */
    public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 显示日期的格式,yyyy年MM月dd日HH时mm分
     */
    public static final String ZHCN_TIME_FORMAT1 = "yyyy年MM月dd日HH时mm分";

    /**
     * 显示日期的格式,yyyyMMddHHmmss
     */
    public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";

    /**
     * 显示日期的格式,yyyyMMddHHmmssSSS
     */
    public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 显示日期的格式,mmssSSS
     */
    public static final String TIMESSS_STR_TIME = "mmssSSS";
    /**
     * 显示日期的格式,mmssSSS
     */
    public static final String TIMEHHMMSS_STR_TIME = "HHmmss";
    /**
     * 显示日期的格式,mmssSSS
     */
    public static final String TIMEHHMMSSSSS_STR_TIME = "HHmmssSSS";

    /**
     * 显示日期的格式,yyyyMMdd
     */
    public static final String DATE_STR_FORMAT = "yyyyMMdd";

    /**
     * 显示日期的格式,yyMMdd
     */
    public static final String DATE_SIMPLE_SHORT_FORMAT = "yyMMdd";

    private static final int DEAFULT_10 = 10;

    /**
     * 格式转换类型: yyyyMMdd ==> yyyy/MM/dd
     */
    public static final int FORMAT_TRANS_D8_D10_S = 1;

    /**
     * 格式转换类型: yyyyMMdd ==> yyyy-MM-dd
     */
    public static final int FORMAT_TRANS_D8_D10_ML = 2;

    /**
     * 格式转换类型: yyyy?MM?dd ==> yyyyMMdd
     */
    public static final int FORMAT_TRANS_D10_D8 = 3;

    /**
     * 格式转换类型: yyyyMMddhhmmss ==> yyyyMMdd
     */
    public static final int FORMAT_TRANS_DT14_D8 = 4;

    /**
     * 格式转换类型: yyyyMMddHHmmss ==> yyyy/MM/dd
     */
    public static final int FORMAT_TRANS_DT14_D10_S = 5;

    /**
     * 格式转换类型: yyyyMMddHHmmss ==> yyyy-MM-dd
     */
    public static final int FORMAT_TRANS_DT14_D10_ML = 6;

    /**
     * 格式转换类型: yyyyMMddHHmmss ==> yyyy/MM/dd HH:mm:ss
     */
    public static final int FORMAT_TRANS_DT14_DT19_S = 7;

    /**
     * 格式转换类型: yyyyMMddHHmmss ==> yyyy-MM-dd HH:mm:ss
     */
    public static final int FORMAT_TRANS_DT14_DT19_ML = 8;

    /**
     * 格式转换类型: yyyyMMddHHmmss ==> yyyyMMdd HH:mm:ss
     */
    public static final int FORMAT_TRANS_DT14_DT17 = 9;

    /**
     * 格式转换类型: yyyyMMdd HH:mm:ss ==> yyyyMMddHHmmss
     */
    public static final int FORMAT_TRANS_DT17_DT14 = 10;

    /**
     * 格式转换类型: yyyy?MM?dd HH:mm:ss ==> yyyyMMddHHmmss
     */
    public static final int FORMAT_TRANS_DT19_DT14 = 11;

    /**
     * 格式转换类型: yyyy-MM-dd ==> yyyyMMddHHmmss
     */
    public static final int FORMAT_TRANS_D10_DT14_ML = 12;

    /**
     * DateFormat,格式:yyyy-MM-dd
     */
    private static DateFormat dateFormat;

    /**
     * DateFormat,格式:yyyy-MM-dd HH:mm:ss
     */
    private static DateFormat dateTimeFormat;

    /**
     * DateFormat,格式:yyyyMMddHHmmss
     */
    private static DateFormat dateTimeStrFormat;

    /**
     * DateFormat,格式:yyyy年MM月dd日HH时mm分ss秒
     */
    private static DateFormat zhcnDateTimeStrFormat;

    /**
     * DateFormat,格式:yyyy年MM月dd日HH时mm分ss秒
     */


    static {
        dateFormat = SimpleDateFormatFactory.getInstance(DATE_FORMAT);
        dateTimeFormat = SimpleDateFormatFactory.getInstance(TIMEF_FORMAT);
        dateTimeStrFormat = SimpleDateFormatFactory
                .getInstance(TIME_STR_FORMAT);
        zhcnDateTimeStrFormat = SimpleDateFormatFactory
                .getInstance(ZHCN_TIME_FORMAT);
    }

    /**
     * 取得当前日期时间。
     *
     * @return 当前日期时间，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return df.format(cal.getTime());
    }

    /**
     * 取得当前日期时间。
     *
     * @return 当前日期时间，格式：yyyyMMddHHmmss
     */
    public static Date getDateFromStr(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    /**
     * 取得当前日期时间。
     *
     * @return 当前日期时间，格式：HHmmss
     */
    public static String getCurrTimenopoint() {
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return df.format(cal.getTime());
    }

    /**
     * 根据指定格式获取当前日期时间
     *
     * @param format 待获取的日期时间格式
     * @return
     */
    public static String getCurrDateTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return df.format(cal.getTime());
    }

    /**
     * 获取当前时间在加减n分钟后的字符串时间
     *
     * @param n
     * @return String
     */
    public static String getTimebyMinAfterOrBefore(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        return (dateTimeFormat.format(now.getTime()));
    }

    /**
     * 获取当前时间在加减n秒后的字符串时间
     *
     * @param n
     * @return String
     */
    public static String getTimebySecAfterOrBefore(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.SECOND, n);
        return (dateTimeFormat.format(now.getTime()));
    }

    /**
     * 获取当前时间在加减n分钟后的时间(java.util.Date)
     *
     * @param n
     * @return Date
     */
    public static Date getTimebyMinAfterOrBeforeDate(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        return now.getTime();
    }

    /**
     * 获取定义的DateFormat格式
     *
     * @param formatStr
     * @return DateFormat
     */
    private static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DATE_FORMAT)) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase(TIMEF_FORMAT)) {
            return dateTimeFormat;
        } else {
            return SimpleDateFormatFactory.getInstance(formatStr);
        }
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return String
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, TIMEF_FORMAT);
    }

    /**
     * 将Date转换成指定格式的字符串
     *
     * @param date
     * @param formatStr
     * @return String
     */
    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 将Date转换成yyyy-MM-dd的字符串
     *
     * @param date
     * @return String
     */
    public static String getDateString(Date date) {
        DateFormat df = getDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     *
     * @param hours
     * @return long
     */
    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取今日的日期，格式自定
     *
     * @param pattern
     * @return String
     */
    public static String getToday(String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        DateFormat sdf = getDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到系统当前的分钟数,如当前时间是上午12:00,系统当前的分钟数就是12*60
     *
     * @return int
     */
    public static int getCurrentMinutes() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        int iMin = now.get(Calendar.HOUR_OF_DAY) * 60
                + now.get(Calendar.MINUTE);
        return iMin;
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return String
     */
    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), ZHCN_TIME_FORMAT);
    }

    /**
     * 获取当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 得到本月月份 如09
     *
     * @return String
     */
    public static String getMonth() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        String monStr = String.valueOf(month);

        // 对于10月份以下的月份,加"0"在前面

        if (month < DEAFULT_10) {
            monStr = "0" + monStr;
        }
        return monStr;
    }

    /**
     * 得到本月第几天
     *
     * @return String
     */
    public static String getDay() {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.valueOf(day);

        // 对于10月份以下的月份,加"0"在前面
        if (day < DEAFULT_10) {
            dayStr = "0" + dayStr;
        }
        return dayStr;
    }

    /**
     * 获取指定日期月份 如09
     *
     * @param date
     * @return String
     */
    public static String getMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int month = now.get(Calendar.MONTH) + 1;
        String monStr = String.valueOf(month);
        // 对于10月份以下的月份,加"0"在前面
        if (month < DEAFULT_10) {
            monStr = "0" + monStr;
        }
        return monStr;
    }

    /**
     * 获取指定日期天数
     *
     * @param date
     * @return String
     */
    public static String getDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int day = now.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.valueOf(day);
        // 对于10月份以下的月份,加"0"在前面
        if (day < DEAFULT_10) {
            dayStr = "0" + dayStr;
        }
        return dayStr;
    }

    /**
     * 根据指定时间判断当前是否失效
     *
     * @param expireTime -失效时间的字符串形式,格式要求:yyMMddHHmmss
     * @return boolean -true:失效 false:没有失效
     * @throws ParseException
     */
    public static boolean isTimeExpired(String expireTime)
            throws ParseException {
        boolean ret = false;

        // SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

        Date date = SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).parse(
                expireTime);
        Calendar expire = Calendar.getInstance();
        expire.setTime(date);
        Calendar now = Calendar.getInstance();
        // 将毫秒字段设为0,保持精度一致性

        now.set(Calendar.MILLISECOND, 0);
        if (now.after(expire)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 根据开始时间和可用时长计算出失效时间
     *
     * @param startTime     -开始时间
     * @param availableTime -有效时长（单位：秒）
     * @return String 失效时间
     * @throws ParseException
     */
    public static String getExpireTimeByCalculate(Date startTime,
                                                  int availableTime) {

        Calendar expire = Calendar.getInstance();

        // 设置为开始时间

        expire.setTime(startTime);

        // 开始时间向后有效时长秒得到失效时间
        expire.add(Calendar.SECOND, availableTime);

        // 返回失效时间字符串

        // SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).format(
                expire.getTime());

    }

    /**
     * 将Date转换为yyyyMMddHHmmss的形式
     *
     * @param date
     * @return String
     */
    public static String convertDateToString(Date date) {
        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT)
                .format(date);
    }

    /**
     * 将Date转换为指定日期时间格式的形式
     *
     * @param date
     * @param format
     * @return String
     */
    public static String convertDateToString(Date date, String format) {
        return SimpleDateFormatFactory.getInstance(format).format(date);
    }

    /**
     * 将yyyyMMddHHmmss形式的字符串转换成Date的形式
     *
     * @param date -yyyyMMddHHmmss形式的日期字符串
     * @return Date
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).parse(date);
    }

    /**
     * 将yyyy-MM-dd形式的字符串转换成Date的形式
     *
     * @param date yyyy-MM-dd形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertSimpleStringToDate(String date)
            throws ParseException {
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT).parse(date);
    }

    /**
     * 将yyyyMMddHHmmss格式的日期字符转换为yyyy年MM月dd日HH时mm分ss秒格式的字符串
     *
     * @param date -yyyyMMddHHmmss
     * @return String  -yyyy年MM月dd日HH时mm分ss秒格式的日期字符串
     * @throws ParseException
     */
    public static String convertStringToZhCN(String date) throws ParseException {
        return zhcnDateTimeStrFormat.format(dateTimeStrFormat.parse(date));
    }

    /**
     * 时间字符串转换成日期时间的形式
     *
     * @param date -yyyy-MM-dd HH:mm:ss形式的日期字符串
     * @return Date
     * @throws ParseException
     */
    public static Date convertSimpleStringToDateTime(String date)
            throws ParseException {
        return SimpleDateFormatFactory.getInstance(TIMEF_FORMAT).parse(date);
    }

    /**
     * 获取今日日期
     *
     * @return Date
     */
    public static Date getTodayDate() {
        // 获取今日的日期
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        return today;
    }

    /**
     * 获取昨日日期 返回格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String getYesterdayDateStr() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT).format(
                yesterday);
    }

    /**
     * 获取昨日日期 返回格式：
     *
     * @return String
     */
    public static String getdayDateStr(String para, int daycount) {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daycount);
        Date yesterday = cal.getTime();
        return SimpleDateFormatFactory.getInstance(para).format(
                yesterday);
    }

    /**
     * 获取昨日日期
     *
     * @return Date
     */
    public static Date getYesterdayDate() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        return yesterday;
    }

    /**
     * 获取明天日期 返回格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String getTomorrowDateStr() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT)
                .format(tomorrow);
    }

    /**
     * 获取明天日期
     *
     * @return Date
     */
    public static Date getTomorrowDate() {
        // 获取昨日的日期

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();
        return tomorrow;
    }

    /**
     * 根据Calendar对象、获得指定格式的字符串日期
     *
     * @param calendar
     * @param strDateType 1.yyyy年MM月dd日；2.MM月dd日；3.yyyy年；4.yyyy-MM-dd；5。MM-dd；6.yyyy；else。yyyy-MM-dd
     * @return String
     */
    public static String getStrDate(Calendar calendar, int strDateType) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = (calendar.get(Calendar.MONTH) + 1) < DEAFULT_10 ? "0"
                + (calendar.get(Calendar.MONTH) + 1) : String.valueOf

                ((calendar.get(Calendar.MONTH) + 1));
        String day = calendar.get(Calendar.DAY_OF_MONTH) < DEAFULT_10 ? "0"
                + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf

                (calendar.get(Calendar.DAY_OF_MONTH));
        String strDate = "";

        switch (strDateType) {
            case 1:
                strDate = year + "年" + month + "月" + day + "日";
                break;
            case 2:
                strDate = month + "月" + day + "日";
                break;
            case 3:
                strDate = year + "年";
                break;
            case 4:
                strDate = year + "-" + month + "-" + day;
                break;
            case 5:
                strDate = month + "-" + day;
                break;
            case 6:
                strDate = year;
                break;
            default:
                strDate = year + "-" + month + "-" + day;
                break;
        }

        return strDate;
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date）
     *
     * @param protoDate
     * @param monthOffset
     * @return Date
     */
    public static Date getOffsetMonthDate(Date protoDate, int monthOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        // cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);错误写法
        cal.add(Calendar.MONTH, monthOffset);
        return cal.getTime();
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     *
     * @param protoDate
     * @param dateOffset
     * @return Date
     */
    public static Date getOffsetDayDate(Date protoDate, int dateOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
                + dateOffset);
        return cal.getTime();
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 小时的时间（Date）
     *
     * @param protoDate
     * @param offsetHour
     * @return Date
     */
    public static Date getOffsetHourDate(Date protoDate, int offsetHour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)
                + offsetHour);
        return cal.getTime();
    }

    /**
     * 根据指定的日期获取指定更改后的日期(未做日期效验)
     *
     * @param currentDate
     * @param assignYear  指定年份,-1代表年不做修改
     * @param assignMonth 指定月份,从0开始,超过月最大值自动往后加一个月年,-1代表月不做修改
     * @param assignDay   指定日,从1开始,超过日最大值往后加一个月,-1代表日不做修改
     * @return Date
     */
    public static Date getAssignDate(Date currentDate, int assignYear,
                                     int assignMonth, int assignDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        if (assignYear > -1) {
            cal.set(Calendar.YEAR, assignYear);
        }
        if (assignMonth > -1) {
            cal.set(Calendar.MONTH, assignMonth);
        }
        if (assignDay > -1) {
            cal.set(Calendar.DAY_OF_MONTH, assignDay);
        }
        return cal.getTime();
    }

    /**
     * 计算与当前系统日期之间相差天数curDate-checkDate
     *
     * @param compareDate 待比较日期yyyyMMdd
     * @return 相差的天数
     */
    public static int getDayCountBetweenCurDate(String compareDate) {
        //两个日期之间的天数
        int days = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(compareDate);
        } catch (Exception e) {
            log.error("计算与当前系统日期之间相差天数异常[{}]", e);
        }
        beginCalendar.setTime(date);
        endCalendar.setTime(new Date());
        //计算天数
        while (beginCalendar.before(endCalendar)) {
            days++;
            beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param startDate 起始日期yyyyMMdd
     * @param endDate   结束日期yyyyMMdd
     * @return 相差的天数
     */
    public static int getDayCountBetweenDate(String startDate, String endDate) {
        //两个日期之间的天数
        int days = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = df.parse(startDate);
            eDate = df.parse(endDate);
        } catch (Exception e) {
            log.error("DateUtil->计算两个日期之间的天数异常[{}]", e);
        }
        beginCalendar.setTime(sDate);
        endCalendar.setTime(eDate);
        //计算天数
        while (beginCalendar.before(endCalendar)) {
            days++;
            beginCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    /**
     * 获得两个日前之间相差的天数,有时分秒的影响
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return int
     */
    public static int getDayCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.DAY_OF_MONTH,
                    startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
            i++;
        }
        return i;
    }

    /**
     * 获得两个日前之间相差的天数,有时分秒的影响
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return int
     */
    public static int getDayCountBetweenDateWithoutSecond(Date startDate, Date endDate) {
        int days = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            startDate = format.parse(format.format(startDate));
            endDate = format.parse(format.format(endDate));
            days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
        } catch (ParseException e) {
            log.error("");
        }
        return days;
    }

    /**
     * 获得两个日前之间相差的月份
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return int
     */
    public static int getMonthCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.MONTH,
                    startCalendar.get(Calendar.MONTH) + 1);
            i++;
        }
        return i;
    }

    /**
     * 获得两个日前之间相差的年
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return int
     */
    public static int getYearlyCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.YEAR,
                    startCalendar.get(Calendar.YEAR + 1));
            i++;
        }
        return i;
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     *
     * @param protoDate               原来的时间（java.util.Date）
     * @param dateOffset（向前移正数，向后移负数）
     * @param type                    指定不同的格式（1：年月日，2：年月日时，3：年月日时分）
     * @return Date
     */
    public static Date getOffsetSimpleDate(Date protoDate, int dateOffset, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
                - dateOffset);
        if (type == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
        }
        if (type == 2) {
            cal.set(Calendar.MINUTE, 0);
        }
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取yyyyMMddHHmmssSSS格式的时间字符串
     *
     * @return String
     */
    public static String getDateToString() {
        SimpleDateFormat dateFormat = SimpleDateFormatFactory
                .getInstance(TIMESSS_STR_FORMAT);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 获取yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @return String
     */
    public static String getTodayTimeString() {
        SimpleDateFormat dateFormat = SimpleDateFormatFactory
                .getInstance(TIMEF_FORMAT);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    public static void main(String[] args) {
//		SimpleDateFormat dateFormat = SimpleDateFormatFactory.getInstance(TIMEF_FORMAT);
//		System.out.printf("" + dateFormat.format(getYesterdayZeroDate()));
        int re = compareTwoDate("2016-06-21 18:57:32", "2016-06-21 17:57:32", TIMEF_FORMAT);
        System.out.println(re);
    }

    /**
     * 获取yyyy-MM-dd HH:mm:ss S格式的时间字符串
     *
     * @return String
     */
    public static String getTodayTimeSString() {
        SimpleDateFormat dateFormat = SimpleDateFormatFactory
                .getInstance(TIMES_FORMAT);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 得到指定格式(yyyy-MM-dd)日增加n天后的日期
     *
     * @param s yyyy-MM-dd
     * @param n
     * @return String yyyy-MM-dd
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = SimpleDateFormatFactory
                    .getInstance(DateUtil.DATE_FORMAT);
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            // 增加n天
            cd.add(Calendar.DATE, n);
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            log.error("得到指定格式(yyyy-MM-dd)日增加n天后的日期异常，error:", e);
            return null;
        }
    }

    /**
     * 得到指定格式(yyyy-MM-dd)日增加n天后的日期
     *
     * @param s yyyy-MM-dd
     * @param n
     * @return String yyyy-MM-dd
     */
    public static String addDay(String s, String format, int n) {
        try {
            SimpleDateFormat sdf = SimpleDateFormatFactory
                    .getInstance(format);
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            // 增加n天
            cd.add(Calendar.DATE, n);
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            log.error("得到指定格式(yyyy-MM-dd)日增加n天后的日期异常，error:", e);
            return null;
        }
    }

    /**
     * 得到date日增加n天后的日期
     *
     * @param s
     * @param n
     * @return Date
     */
    public static Date addDay(Date s, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(s);
        // 增加n天
        cd.add(Calendar.DATE, n);
        return cd.getTime();
    }

    /**
     * 得到date日增加n月后的日期
     *
     * @param m
     * @param n
     * @return Date
     */
    public static Date addMonth(Date m, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(m);
        // 增加n个月
        cd.add(Calendar.MONTH, n);
        return cd.getTime();
    }

    /**
     * 得到date日增加n月后的指定格式日期
     *
     * @param m
     * @param n
     * @param formatstring
     * @return String
     */
    public static String addMonth(Date m, int n, String formatstring) {
        try {
            SimpleDateFormat sdf = SimpleDateFormatFactory
                    .getInstance(formatstring);
            Calendar cd = Calendar.getInstance();
            cd.setTime(m);
            // 增加n个月
            cd.add(Calendar.MONTH, n);
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            log.error("得到date日增加n月后的指定格式日期异常，error:", e);
            return null;
        }
    }

    /**
     * 获取需要执行的统计的日期数组 格式['2011-01-01',2011-01-02']
     *
     * @return String[]
     */
    public static String[] getExecDay(Date lastDate) {
        String[] day = null;
        // 获取昨天日期
        Date yesdate = null;
        try {
            yesdate = DateUtil.convertDateToDate(DateUtil.getYesterdayDate(),
                    DateUtil.DATE_FORMAT);
        } catch (ParseException e) {
            log.error("获取需要执行的统计的日期数组异常，error:", e);
        }
        // 获取上次最后执行日期与昨天相隔天数
        int dayCount = DateUtil.getDayCountBetweenDate(lastDate, yesdate);
        if (dayCount <= 0) {
            return day;
        }
        if (dayCount == 1) {
            return new String[]{DateUtil.getYesterdayDateStr()};
        } else {
            day = new String[dayCount];
            for (int i = 0; i < dayCount; i++) {
                String date = SimpleDateFormatFactory.getInstance(
                        DateUtil.DATE_HOUR_FORMAT).format(
                        DateUtil.addDay(lastDate, i));
                day[i] = date;
            }
        }
        return day;
    }

    /**
     * 获取需要执行的统计的年-月数组 格式['2011-01',2011-01']
     *
     * @return String[]
     */
    public static String[] getExecYearMonth(Date lastYearMonth) {
        String[] yearMonth = null;
        // 获取上个月日期
        Date lastMonth = DateUtil.getOffsetMonthDate(new Date(), 1);
        try {
            lastMonth = DateUtil.convertDateToDate(lastMonth, DateUtil.DATE_YEAE_MONTH);
        } catch (ParseException e) {
            log.error("获取需要执行的统计的日期数组异常，error:", e);
        }
        // 获取上次最后执行日期与昨天相隔天数
        int monthCount = DateUtil.getMonthCountBetweenDate(lastYearMonth,
                lastMonth);
        if (monthCount <= 0) {
            return yearMonth;
        }
        if (monthCount == 1) {
            return yearMonth = new String[]{DateUtil.convertDateToString(
                    lastMonth, DateUtil.DATE_YEAE_MONTH)};
        } else {
            yearMonth = new String[monthCount];
            for (int i = 0; i < monthCount; i++) {
                String date = DateUtil.addMonth(lastYearMonth, i, DateUtil.DATE_YEAE_MONTH);
                yearMonth[i] = date;
            }
        }
        return yearMonth;
    }

    /**
     * 获取当前月第一天
     *
     * @return Date
     */
    public static Date getFirstDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = ca.getTime();
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        return firstDate;
    }

    /**
     * 获当前月的最后一天
     *
     * @return Date
     */
    public static Date getLastDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = ca.getTime();
        return lastDate;
    }

    /**
     * 获取当前月第一天
     *
     * @return Date
     */
    public static Date getFirstDayOfMonthTime() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 00);
        ca.set(Calendar.MINUTE, 00);
        ca.set(Calendar.SECOND, 00);
        Date firstDate = ca.getTime();
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        return firstDate;
    }

    /**
     * 获当前月的最后一天
     *
     * @return Date
     */
    public static Date getLastDayOfMonthTime() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        Date lastDate = ca.getTime();
        return lastDate;
    }

    /**
     * 获取一天的最后时间，常用于时间段查询的结束时间的处理  得到的时间为在d的23:59:59
     *
     * @param d
     * @return Date
     */
    public static Date getLastTimeOfDay(Date d) {
        if (null == d) {
            d = DateUtil.getCurrentDate();
        }
        String dateStr = SimpleDateFormatFactory.getInstance(DATE_FORMAT).format(d);
        try {
            d = DateUtil.convertStringToDate(dateStr, DATE_FORMAT);
        } catch (ParseException e) {
            log.error("获取一天的最后时间，常用于时间段查询的结束时间的处理异常，error:", e);
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MILLISECOND, -1);
        d = ca.getTime();
        return d;
    }

    /**
     * 字符串转化为日期
     *
     * @param date         -日期字符串
     * @param formatString -格式化字符串
     * @return Date
     * @throws ParseException
     */
    public static Date convertStringToDate(String date, String formatString) throws ParseException {
        return SimpleDateFormatFactory.getInstance(formatString).parse(date);
    }

    /**
     * 日期转化为格式化日期
     *
     * @param date         -日期
     * @param formatString -格式化字符串
     * @return Date
     * @throws ParseException
     */
    public static Date convertDateToDate(Date date, String formatString) throws ParseException {
        return SimpleDateFormatFactory.getInstance(formatString).parse(
                convertDateToString(date, formatString));
    }

    /**
     * 获查询日期区间 今天(0), 近一周(1), 本月(2),近一月(3)   Date[0] 开始时间 Date[1] 结束时间
     *
     * @return Date[]
     */
    public static Date[] getDateSection(int dateType) {
        Date[] dateSection = new Date[2];
        if (DateConstants.TODAY.value == dateType) {
            dateSection[0] = getTodayDate();
            dateSection[1] = dateSection[0];
        } else if (DateConstants.NEARLYWEEK.value == dateType) {
            dateSection[0] = getOffsetDayDate(getTodayDate(), 7);
            dateSection[1] = getTodayDate();
        } else if (DateConstants.NEARLYMONTH.value == dateType) {
            dateSection[0] = getOffsetMonthDate(getTodayDate(), 1);
            dateSection[1] = getTodayDate();
        } else if (DateConstants.MONTH.value == dateType) {
            dateSection[0] = getFirstDayOfMonth();
            dateSection[1] = getLastDayOfMonth();
        } else {
            dateSection = null;
        }
        return dateSection;
    }

    /**
     * 得到当前时间
     *
     * @return
     */
    public static String getCurrTime() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

    /**
     * 判断传入的日期时间是否符合yyyyMMddHHmmss的格式要求
     *
     * @param dateTime
     * @return true-符合；false-不符合
     */
    public static boolean isDateTime(String dateTime) {
        boolean is = false;
        if (StringUtils.isBlank(dateTime) || dateTime.length() != 14) {
            return is;
        }
        String date = dateTime.substring(0, 8);
        String time = dateTime.substring(8, 14);
        is = isDate(date);
        if (is) {
            is = isTime(time);
        }
        return is;
    }

    /**
     * 判断传入的日期是否为符合日期格式yyyyMMdd或yyyy-MM-dd的日期
     *
     * @param date -日期
     * @return true-符合；false-不符合
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 判断传入的时间是否为符合时间格式HHmmss的时间
     *
     * @param time -日期
     * @return true-符合；false-不符合
     */
    public static boolean isTime(String time) {
        StringBuffer reg = new StringBuffer(
                "^([0-1]\\d|2[0-3])[0-5]\\d[0-5]\\d$");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(time).matches();
    }

    /**
     * 判断两个日期是否处于同一年中的同一周
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 如果两个日期处于同一年的同一周，返回{@code true}，反之为{@code false}
     */
    public static boolean inSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        int weekOfYear1 = c1.get(Calendar.WEEK_OF_YEAR);
        int weekOfYear2 = c2.get(Calendar.WEEK_OF_YEAR);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        if (year1 == year2 && weekOfYear1 == weekOfYear2) {
            return true;
        }
        return false;
    }

    /**
     * 得到两个日期之间的所有日期
     *
     * @param startDate 起始日期YYYYMMDD
     * @param endDate   结束日期YYYYMMDD
     * @param days      相差的天数
     * @return 两个日期之间的所有日期，包含起始日期
     */
    public static List<String> getAllDate(String startDate, String endDate, int days) {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = df.parse(startDate);
            eDate = df.parse(endDate);
        } catch (Exception e) {
            log.error("得到两个日期之间的所有日期异常，error:", e);
        }
        beginCalendar.setTime(sDate);
        endCalendar.setTime(eDate);
        if (days > 1) {
            list.add(df.format(endCalendar.getTime()));
        }
        for (int i = 2; i <= days; i++) {
            endCalendar.add(Calendar.DATE, -1);
            String date = df.format(endCalendar.getTime());
            list.add(date);
        }
        list.add(df.format(beginCalendar.getTime()));
        return list;
    }

    /**
     * 得到指定日期的前一日期
     *
     * @param date 指定yyyyMMdd
     * @return 前一日期
     */
    public static String getBeforeDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date eDate = null;
        try {
            eDate = df.parse(date);
        } catch (Exception e) {
            log.error("得到指定日期的前一日期异常，error:", e);
        }
        calendar.setTime(eDate);
        calendar.add(Calendar.DATE, -1);
        return df.format(calendar.getTime());
    }

    /**
     * 得到指定日期的后一日期
     *
     * @param date 指定yyyyMMdd
     * @return 后一日期
     */
    public static String getAfterDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date eDate = null;
        try {
            eDate = df.parse(date);
        } catch (Exception e) {
            log.error("得到指定日期的后一日期异常，error:", e);
        }
        calendar.setTime(eDate);
        calendar.add(Calendar.DATE, +1);
        return df.format(calendar.getTime());
    }

    /**
     * 日期/时间格式转换
     *
     * @param orgStr              原始日期时间字符串
     * @param formatTransformType 转换方式
     * @return 转换后的字符串
     */
    public static String format(String orgStr, int formatTransformType) {
        if (null == orgStr) {
            return null;
        }

        String result = null;
        StringBuffer temp = new StringBuffer();

        switch (formatTransformType) {
            case FORMAT_TRANS_D8_D10_S: {
                //yyyyMMdd ==> yyyy/MM/dd
                if (orgStr.length() != 8) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4)).append("/").append(orgStr.substring(4, 6)).append("/").append(orgStr.substring(6, 8));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_D8_D10_ML: {
                //yyyyMMdd ==> yyyy-MM-dd
                if (orgStr.length() != 8) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4)).append("-").append(orgStr.substring(4, 6)).append("-").append(orgStr.substring(6, 8));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_D10_D8: {
                //yyyy?MM?dd ==> yyyyMMdd
                if (orgStr.length() != 10) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4)).append(orgStr.substring(5, 7)).append(orgStr.substring(8, 10));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_DT14_D8: {
                //yyyyMMddhhmmss ==> yyyyMMdd
                if (orgStr.length() != 14) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 8));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_DT14_D10_S: {
                //yyyyMMddHHmmss ==> yyyy/MM/dd
                result = format(format(orgStr, FORMAT_TRANS_DT14_D8), FORMAT_TRANS_D8_D10_S);
                break;
            }
            case FORMAT_TRANS_DT14_D10_ML: {
                //yyyyMMddHHmmss ==> yyyy-MM-dd
                result = format(format(orgStr, FORMAT_TRANS_DT14_D8), FORMAT_TRANS_D8_D10_ML);
                break;
            }
            case FORMAT_TRANS_DT14_DT19_S: {
                //yyyyMMddHHmmss ==> yyyy/MM/dd HH:mm:ss
                if (orgStr.length() != 14) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4))
                        .append("/")
                        .append(orgStr.substring(4, 6))
                        .append("/")
                        .append(orgStr.substring(6, 8))
                        .append(" ")
                        .append(orgStr.substring(8, 10))
                        .append(":")
                        .append(orgStr.substring(10, 12))
                        .append(":")
                        .append(orgStr.substring(12, 14));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_DT14_DT19_ML: {
                //yyyyMMddHHmmss ==> yyyy-MM-dd HH:mm:ss
                if (orgStr.length() != 14) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4))
                        .append("-")
                        .append(orgStr.substring(4, 6))
                        .append("-")
                        .append(orgStr.substring(6, 8))
                        .append(" ")
                        .append(orgStr.substring(8, 10))
                        .append(":")
                        .append(orgStr.substring(10, 12))
                        .append(":")
                        .append(orgStr.substring(12, 14));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_DT14_DT17: {
                //yyyyMMddHHmmss ==> yyyyMMdd HH:mm:ss
                temp.append(format(orgStr, FORMAT_TRANS_DT14_D8))
                        .append(" ")
                        .append(orgStr.substring(8, 10))
                        .append(":")
                        .append(orgStr.substring(10, 12))
                        .append(":")
                        .append(orgStr.substring(12, 14));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_DT17_DT14: {
                //yyyyMMdd HH:mm:ss ==> yyyyMMddHHmmss
                if (orgStr.length() != 17) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 8))
                        .append(orgStr.substring(9, 11))
                        .append(orgStr.substring(12, 14))
                        .append(orgStr.substring(15, 17));
                result = temp.toString();
                break;//yyyy?MM?dd HH:mm:ss ==> yyyyMMddHHmmss
            }
            case FORMAT_TRANS_DT19_DT14: {
                //yyyy?MM?dd HH:mm:ss ==> yyyyMMddHHmmss
                if (orgStr.length() != 19) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4))
                        .append(orgStr.substring(5, 7))
                        .append(orgStr.substring(8, 10))
                        .append(orgStr.substring(11, 13))
                        .append(orgStr.substring(14, 16))
                        .append(orgStr.substring(17, 19));
                result = temp.toString();
                break;
            }
            case FORMAT_TRANS_D10_DT14_ML: {
                //yyyy-MM-dd ==> yyyyMMddHHmmss
                if (orgStr.length() != 10) {
                    throw new RuntimeException("Invalid date/time string format to be format.");
                }
                temp.append(orgStr.substring(0, 4))
                        .append(orgStr.substring(5, 7))
                        .append(orgStr.substring(8, 10))
                        .append("000000");
                result = temp.toString();
                break;
            }
            default:
                throw new RuntimeException("Invalid tdicdata/time format type.");
        }

        return result;
    }

    /**
     * 将17位的时间格式[YYYYMMDD HH:MM:SS]转换成14位的时间格式[YYYYMMDDHHMMSS]
     *
     * @param timeStr
     * @return
     * @throws Exception
     */
    public static String change17TimeTo14Time(String timeStr) {
        String timeStr2 = "";

        timeStr2 = timeStr.replaceAll(" ", "").replaceAll(":", "");

        return timeStr2;
    }

    /**
     * 将19位的时间格式[YYYY-MM-DD HH:MM:SS]转换成14位的时间格式[YYYYMMDDHHMMSS]
     *
     * @param timeStr
     * @return
     * @throws Exception
     */
    public static String change19TimeTo14Time(String timeStr) {
        String timeStr2 = "";

        timeStr2 = timeStr.replaceAll(" ", "").replaceAll(":", "").replaceAll("-", "");

        return timeStr2;
    }

    /**
     * 将14位的时间格式[YYYYMMDDHHMMSS]转换成19位的时间格式[YYYY-MM-DD HH:MM:SS]
     *
     * @param timeStr
     * @return
     */
    public static String change14TimeTo19Time(String timeStr) {
        String yyyy = timeStr.substring(0, 4);
        String mm = timeStr.substring(4, 6);
        String dd = timeStr.substring(6, 8);
        String hh = timeStr.substring(8, 10);
        String mi = timeStr.substring(10, 12);
        String ss = timeStr.substring(12, 14);
        return yyyy + "-" + mm + "-" + dd + " " + hh + ":" + mi + ":" + ss;
    }

    /**
     * 将14位的时间格式[YYYYMMDDHHMMSS]转换成17位的时间格式[YYYYMMDD HH:MM:SS]
     *
     * @param timeStr
     * @return
     */
    public static String change14TimeTo17Time(String timeStr) {
        String yyyy = timeStr.substring(0, 4);
        String mm = timeStr.substring(4, 6);
        String dd = timeStr.substring(6, 8);
        String hh = timeStr.substring(8, 10);
        String mi = timeStr.substring(10, 12);
        String ss = timeStr.substring(12, 14);
        return yyyy + mm + dd + " " + hh + ":" + mi + ":" + ss;
    }

    /**
     * 将14位的时间格式[YYYYMMDDHHMMSS]转换成4位的时间格式[YYYY]
     *
     * @param timeStr
     * @return
     */
    public static String change14TimeTo4Time(String timeStr) {
        String yyyy = timeStr.substring(0, 4);
        return yyyy;
    }

    /**
     * 判断与当前日期的大小
     *
     * @param compareDate 待比较日期yyyy-MM-dd
     * @return 1或者0 compareDate>=currtCalendar  ;-1 compareDate>currtCalendar
     */
    public static int getAfterDayCountBetweenCurDate(String compareDate) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Calendar currtCalendar = Calendar.getInstance();
        Calendar compareCalendar = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(compareDate);
            compareCalendar.setTime(date);
            currtCalendar.setTime(df.parse(df.format(new Date())));
        } catch (Exception e) {
            log.error("DateUtil->判断与当前日期的大小异常[{}]", e);
        }
        if (currtCalendar.before(compareCalendar)) {
            return 1;
        } else if (currtCalendar.after(compareCalendar)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 判断两个日期的大小
     *
     * @param
     * @return 1或者0 firstDate>=lastDate  ;-1 lastDate>firstDate
     */
    public static int compareTwoDate(String firstDate, String lastDate) {
        SimpleDateFormat df = new SimpleDateFormat(TIMEMM_STR_FORMAT);
        Calendar currtCalendar = Calendar.getInstance();
        Calendar compareCalendar = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(firstDate);
            compareCalendar.setTime(date);
            currtCalendar.setTime(df.parse(lastDate));
        } catch (Exception e) {
            log.error("判断两个日期的大小异常，error:", e);
        }
        if (currtCalendar.after(compareCalendar)) {
            return 1;
        } else if (currtCalendar.before(compareCalendar)) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取前天日期
     *
     * @return Date
     */
    public static Date getYesterdayZeroDate() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        Date yesterday = cal.getTime();
        return yesterday;
    }

    public static Date getYesterdayEndDate() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        Date yesterday = cal.getTime();
        return yesterday;
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return String
     */
    public static String dateToDateString2(Date date) {
        String str = "";
        if (null == date) {
            str = "";
        } else {
            str = dateToDateString(date, TIMEF_FORMAT).substring(0, 10);
        }
        return str;
    }

    /**
     * 计算两个日期相隔的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();

        // 设日历为相应日期
        calendarStartDate.setTime(startDate);
        calendarEndDate.setTime(endDate);
        if (startDate.after(endDate)) {
            Calendar swap = calendarStartDate;
            calendarStartDate = calendarEndDate;
            calendarEndDate = swap;
        }

        int days = calendarEndDate.get(Calendar.DAY_OF_YEAR)
                - calendarStartDate.get(Calendar.DAY_OF_YEAR);
        int y2 = calendarEndDate.get(Calendar.YEAR);
        while (calendarStartDate.get(Calendar.YEAR) < y2) {
            days += calendarStartDate.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendarStartDate.add(Calendar.YEAR, 1);
        }

        return days;
    }

    /**
     * 判断两个日期的大小
     *
     * @param
     * @return 1或者0 firstDate>=lastDate  ;-1 lastDate>firstDate
     */
    public static int compareTwoDate(String firstDate, String lastDate, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Calendar currtCalendar = Calendar.getInstance();
        Calendar compareCalendar = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(firstDate);
            compareCalendar.setTime(date);
            currtCalendar.setTime(df.parse(lastDate));
        } catch (Exception e) {
            log.error("判断两个日期的大小异常，error:", e);
        }
        if (currtCalendar.after(compareCalendar)) {
            return 1;
        } else if (currtCalendar.before(compareCalendar)) {
            return -1;
        } else {
            return 0;
        }
    }

}
