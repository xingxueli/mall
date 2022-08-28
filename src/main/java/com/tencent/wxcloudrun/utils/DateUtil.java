package com.tencent.wxcloudrun.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>DateUtil class.</p>
 *
 * @author mike
 * @version $Id: $Id
 */
public class DateUtil extends DateUtils {
    /** Constant <code>DATE_FORMAT="yyyy-MM-dd"</code> */
    public static String DATE_FORMAT = "yyyy-MM-dd";
    /** Constant <code>DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss"</code> */
    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** Constant <code>DATE_FORMAT_CHINESE="yyyy年M月d日"</code> */
    public static String DATE_FORMAT_CHINESE = "yyyy年M月d日";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "dd-MM-yyyy"};

    /**
     * <p>getDate.</p>
     *
     * @return a {@link String} object.
     */
    public static String getDate() {
        return getDate(DateUtil.DATE_FORMAT);
    }

    /**
     * <p>getDate.</p>
     *
     * @param index a int.
     * @return a {@link String} object.
     */
    public static String getDate(int index) {
        return getDate(parsePatterns[index]);
    }

    /**
     * <p>getDate.</p>
     *
     * @param pattern a {@link String} object.
     * @return a {@link String} object.
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * <p>formatDate.</p>
     *
     * @param date a {@link Date} object.
     * @return a {@link String} object.
     */
    public static String formatDate(Date date) {
        return formatDate(date, "");
    }

    /**
     * <p>formatDate.</p>
     *
     * @param date a {@link Date} object.
     * @param pattern a {@link String} object.
     * @return a {@link String} object.
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        } else if (StringUtils.isEmpty(pattern)) {
            pattern = DateUtil.DATE_FORMAT;
        }
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * <p>formatDateTime.</p>
     *
     * @param date a {@link Date} object.
     * @return a {@link String} object.
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DateUtil.DATE_TIME_FORMAT);
    }

    /**
     * <p>getTime.</p>
     *
     * @return a {@link String} object.
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * <p>getDateTime.</p>
     *
     * @return a {@link String} object.
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * <p>getYear.</p>
     *
     * @return a {@link String} object.
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * <p>getMonth.</p>
     *
     * @return a {@link String} object.
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * <p>getDay.</p>
     *
     * @return a {@link String} object.
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * <p>getWeek.</p>
     *
     * @return a {@link String} object.
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    //将日期字符串转换成Date格式
    /**
     * <p>parseDate.</p>
     *
     * @param str a {@link Object} object.
     * @return a {@link Date} object.
     */
    public static Date parseDate(Object str) {
        if (str == null)
            return null;
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
        }
        return null;
    }
	/*public static Date parseDate(Object str, int parsePatternsIndex){
	    if (str == null)
	      return null;
	    try
	    {
	      return parseDate(str.toString(), parsePatterns[parsePatternsIndex]); } catch (ParseException e) {
	    }
	    return null;
	}*/

    /**
     * 与当前日期相差几天
     *
     * @param date a {@link Date} object.
     * @return a long.
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 86400000L;
    }

    /**
     * 当前时间相差几天
     * 传入时间戳,精确到秒
     * @param date
     * @return
     */
    public static long pastDays(Long date) {
        long t = System.currentTimeMillis()/1000 - date;
        return t / 86400L;
    }

    /**
     * 与当前日期相差几小时
     *
     * @param date a {@link Date} object.
     * @return a long.
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 3600000L;
    }

    /**
     * 与当前日期相差几分钟
     *
     * @param date a {@link Date} object.
     * @return a long.
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / 60000L;
    }

    /**
     * <p>formatDateTime.</p>
     *
     * @param timeMillis a long.
     * @return a {@link String} object.
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / 86400000L;
        long hour = timeMillis / 3600000L - day * 24L;
        long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
        long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
        //return (day > 0L ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
        return (day > 0L ? day + "," : "") + hour + "′" + min + "″" + s + "″";
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before a {@link Date} object.
     * @param after a {@link Date} object.
     * @return a double.
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 86400000L;
    }

    /**
     * 获取两个时间之间的分钟数
     *
     * @param before a {@link Date} object.
     * @param after a {@link Date} object.
     * @return a double.
     */
    public static double getMinuteOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 1000 / 60;
    }

    /**
     * <p>addYear.</p>
     *
     * @param date a {@link Date} object.
     * @param i a int.
     * @param pattern a {@link String} object.
     * @return a {@link String} object.
     */
    public static String addYear(Date date, int i, String pattern) {
        return formatDate(i, 3, date, pattern);
    }

    /**
     * <p>addMonth.</p>
     *
     * @param date a {@link Date} object.
     * @param i a int.
     * @param pattern a {@link String} object.
     * @return a {@link String} object.
     */
    public static String addMonth(Date date, int i, String pattern) {
        return formatDate(i, 2, date, pattern);
    }

    /**
     * <p>addDay.</p>
     *
     * @param date a {@link Date} object.
     * @param i a int.
     * @param pattern a {@link String} object.
     * @return a {@link String} object.
     */
    public static String addDay(Date date, int i, String pattern) {
        return formatDate(i, 1, date, pattern);
    }

    /**
     * 在当前日期上+number(年、月、日)
     *
     * @param number 日期增加number(年、月、日)
     * @param type   控制类型(年type=3、月type=2、日type=1)
     * @return
     */
    private static String formatDate(int number, int type, Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        cal.setTime(date);
        if (1 == type) {//操作日期(日)
            cal.add(Calendar.DATE, number);
        } else if (2 == type) {//操作日期(月)
            cal.add(Calendar.MONTH, number);
        } else if (3 == type) {//操作日期(年)
            cal.add(Calendar.YEAR, number);
        }
        return formatDate(cal.getTime(), pattern);
    }

    /**
     * <p>getQuarter.</p>
     *
     * @param month a {@link String} object.
     * @return a {@link String} object.
     */
    public static String getQuarter(String month) {
        String quarter = "";
        int m = Integer.parseInt(month);
        if (m >= 1 && m == 3) {
            quarter = "1";
        }
        if (m >= 4 && m <= 6) {
            quarter = "2";
        }
        if (m >= 7 && m <= 9) {
            quarter = "3";
        }
        if (m >= 10 && m <= 12) {
            quarter = "4";
        }
        return quarter;
    }

    /**
     * 获取天数
     * @param end
     * @param start
     * @return
     */
    public static long getDaysBetween(Date start, Date end) {
        long difference =  (end.getTime()-start.getTime())/86400000;
        return Math.abs(difference);
    }

    /**
     * 获取天数
     * @param end
     * @param start
     * @return
     */
    public static long getDaysBetween(LocalDate start, LocalDate end) {
        long day = start.toEpochDay() - end.toEpochDay();
        return day;
    }

    /**
     * 根据所有的月份计算出相差的年份,四舍五入
     *
     * @param months
     * @return
     */
    private static int handleMonth(int months) {

        BigDecimal month = BigDecimal.valueOf(months);
        BigDecimal total = BigDecimal.valueOf(12);
        int result = month.divide(total, 0, RoundingMode.HALF_UP).intValue();
        return result;
    }
    public static final java.time.format.DateTimeFormatter dateTimeFormatter= java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final LocalTime startOfDay=LocalTime.of(0,0,0);
    public static final LocalTime endOfDay=LocalTime.of(23,59,59);
    /**
     * 指定国家的时间格式化成印度时间
     * @param localDate
     * @param regionId
     * @return 印度时间String
     */
    public static String formatDate(LocalDate localDate, LocalTime localTime, Integer regionId){
        return formatDate(LocalDateTime.of(localDate,localTime),regionId);
    }
    public static String formatDate(String dateTime, Integer regionId){
        return formatDate(LocalDateTime.parse(dateTime,dateTimeFormatter),regionId);
    }
    public static String formatDate(LocalDateTime localDateTime, Integer regionId){
        if(regionId==1){
            ZonedDateTime zonedDateTime=ZonedDateTime.of(localDateTime, ZoneOffset.ofHoursMinutes(-8,0));
            return zonedDateTime.withZoneSameInstant(ZoneOffset.ofHoursMinutes(5,30)).format(dateTimeFormatter);
        }else {
            ZonedDateTime zonedDateTime=ZonedDateTime.of(localDateTime,ZoneOffset.ofHoursMinutes(5,30));
            return zonedDateTime.format(dateTimeFormatter);
        }
    }

    public static Long toEpochMilli(String dateTime,Integer regionId){
        return toEpochMilli(LocalDateTime.parse(dateTime,dateTimeFormatter),regionId);
    }
    public static Long toEpochMilli(LocalDate localDate,LocalTime localTime,Integer regionId){
        return toEpochMilli(LocalDateTime.of(localDate,localTime),regionId);
    }
    public static Long toEpochMilli(LocalDateTime localDateTime,Integer regionId){
        if(regionId==1){
            ZonedDateTime zonedDateTime=ZonedDateTime.of(localDateTime,ZoneOffset.ofHoursMinutes(-8,0));
            return zonedDateTime.withZoneSameInstant(ZoneOffset.ofHoursMinutes(5,30)).toInstant().toEpochMilli();
        }else {
            ZonedDateTime zonedDateTime=ZonedDateTime.of(localDateTime,ZoneOffset.ofHoursMinutes(5,30));
            return zonedDateTime.toInstant().toEpochMilli();
        }
    }
}
