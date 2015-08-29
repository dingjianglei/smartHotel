package com.changhewl.hotel.util;
 
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @Discription 日期工具类
 * @Author he.zhiqiao
 */
public class DateUtil {

    /** 获取当前年月：yyyyMM */
    public static final String monthFormatStr1 = "yyyyMM";
    /** 获取当前日期：yyyyMMdd */
    public static final String dateFormatStr1 = "yyyyMMdd";
    /** 获取当前日期时间：yyyyMMddHHmmss */
    public static final String dateFormatStr3 = "yyyyMMddHHmmss";

    /** 获取当前年月：yyyyMM */
    public static final DateTimeFormatter monthFormat1 = DateTimeFormat.forPattern(monthFormatStr1);
    /** 获取当前日期：yyyyMMdd */
    public static final DateTimeFormatter dateFormat1 = DateTimeFormat.forPattern(dateFormatStr1);
    /** 获取当前日期时间：yyyyMMddHHmmss */
    public static final DateTimeFormatter dateFormat3 = DateTimeFormat.forPattern(dateFormatStr3);

    /**
     * 取得当前日期时间
	 * @param pattern 日期格式字符串
	 * @return String
     */
    public static String getCurrentDateTime(DateTimeFormatter pattern) {
        return new DateTime().toString(pattern);
    }

    /**
     * 取得当前日期时间
	 * @return Date 日期
     */
    public static Date getCurrentDateTime() {
        return new DateTime().toDate();
    }

    /**
     * 将日期型转换为字符串
     * @param date 日期
     * @return String
     */
    public static String format(Date date) {
        return new DateTime(date).toString(dateFormat1);
    }

    /**
     * 将日期型转换为字符串
     * @param date 日期
	 * @param formatter 日期格式字符串
     * @return String
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        return new DateTime(date).toString(formatter);
    }

    /**
     * 将String转换为Date
     * @param date 日期
     * @return Date 日期
     */
    public static Date parse(String date) { 
        return dateFormat1.parseDateTime(StringUtils.substring(date, 0, 8)).toDate();
    }

    /**
     * 将String转换为Date
     * @param date 日期
	 * @param formatter 日期格式字符串
     * @return Date 日期
     */
    public static Date parse(String date, DateTimeFormatter formatter) {
        return formatter.parseDateTime(date).toDate();
    }

    /**
     * 获取月末日期
     * @param date 日期
     * @return Date 日期
     */
    public static Date getLastDayOfMonth(Date date) {
        DateTime datetime = new DateTime(date);

        int lastDay = datetime.dayOfMonth().getMaximumValue();
        return datetime.withDayOfMonth(lastDay).toDate();
    }

    /**
     * 获取月初日期
     * @param date 日期
     * @return Date 日期
     */
    public static Date getFirstDayOfMonth(Date date) {
        DateTime datetime = new DateTime(date);

        return datetime.withDayOfMonth(1).toDate();
    }

    /**
     * 获取年初日期
     * @param date 日期
     * @return Date 日期
     */
    public static Date getFirstDayOfYear(Date date) {
        DateTime datetime = new DateTime(date);
        return datetime.withMonthOfYear(1).withDayOfMonth(1).toDate();
    }

    /**
     * 获取年末日期
     * @param date 日期
     * @return Date 日期
     */
    public static Date getLastDayOfYear(Date date) {
        DateTime datetime = new DateTime(date);
        return datetime.withMonthOfYear(12).withDayOfMonth(31).toDate();
    }

    /**
     * 计算日期前后step天
     * @param date 日期
     * @param step 天数
     * @return Date 日期
     */
    public static Date getDateByStep(Date date, int step) {
        DateTime datetime = new DateTime(date);
        return datetime.plusDays(step).toDate();
    }

    /**
     * 根据清算日期获取自然月的起止日期
     * @param date 日期
     * @return Date[]
     */
    public static Date[] getDayOfMonthInterval(Date date) {
        return new Date[] { getFirstDayOfMonth(date), getLastDayOfMonth(date) };
    }
}
