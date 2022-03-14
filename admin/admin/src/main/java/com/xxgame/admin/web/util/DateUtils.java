package com.xxgame.admin.web.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils
 * @author gil
 */
public class DateUtils {

    /**
     * 一秒钟的毫秒数
     */
    public static final int ONE_SECOND_MILLISECOND = 1000;

    /**
     * 一分钟的毫秒数
     */
    public static final int ONE_MINUTE_MILLISECOND = 60 * ONE_SECOND_MILLISECOND;

    /**
     * 一小时的毫秒数
     */
    public static final int ONE_HOUR_MILLISECOND = 60 * ONE_MINUTE_MILLISECOND;

    /**
     * 一天的毫秒数
     */
    public static final int ONE_DAY_MILLISECOND = 24 * ONE_HOUR_MILLISECOND;

    /**
     * 一分钟的秒数
     */
    public static final int ONE_MINUTE_SECOND = ONE_MINUTE_MILLISECOND / ONE_SECOND_MILLISECOND;

    /**
     * 1小时的秒数
     */
    public static final long ONE_HOUR_SECOND = ONE_HOUR_MILLISECOND / ONE_SECOND_MILLISECOND;

    /**
     * 1天的秒数
     */
    public static final long ONE_DAY_SECOND = ONE_DAY_MILLISECOND / ONE_SECOND_MILLISECOND;

    /**
     * HH:mm 格式
     */
    public static final String PATTERN_HH_MM = "HH:mm";

    /**
     * yyyyMM 格式
     */
    public static final  String PATTERN_YYYYMM = "yyyyMM";

    /**
     * yyyyMMdd 格式
     */
    public static final  String PATTERN_YYYYMMDD = "yyyyMMdd";

    /**
     * yyyy-MM-dd 格式
     */
    public static final  String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * yyyyMMddHHmm 格式
     */
    public static final  String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";

    /**
     * yyyyMMddHH 格式
     */
    public static final  String PATTERN_YYYYMMDDHH = "yyyyMMddHH";

    /**
     * yyyy-MM-dd HH:mm:ss 格式
     */
    public static final  String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss 格式
     */
    public static final  String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 日期转换成字符串格式
     * @param theDate 待转换的日期
     * @param datePattern 日期格式
     * @return 日期字符串
     */
    public static String date2String(Date theDate, String datePattern) {
        if (theDate == null) {
            return "";
        }

        DateFormat format = new SimpleDateFormat(datePattern);
        try {
            return format.format(theDate);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 字符串转换成日期格式
     *
     * @param  dateString 		待转换的日期字符串
     * @param  datePattern 		日期格式
     * @return {@link Date}		转换后的日期
     */
    public static Date string2Date(String dateString, String datePattern) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(datePattern);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("[%s] -> [%s]", dateString, datePattern), e);
        }
    }

    /**
     * 数值日期转换成日期格式
     *
     * @param  dateTime 		数值类型的日期时间
     * @param  datePattern 		日期格式
     * @return {@link Date}		转换后的日期
     */
    public static Date numberToDate(long dateTime, String datePattern) {
        if (dateTime <= 0) {
            return string2Date("2000-01-01", PATTERN_YYYY_MM_DD);
        }

        return string2Date(dateTime + "", datePattern);
    }

    /**
     * 获取本月第一天的0点
     * @param time
     * @return
     */
    public static Date monthAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        return calendar.getTime();
    }

    /**
     * 获取上月第一天的0点
     * @param time
     * @return
     */
    public static Date lastMonthAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);

        return calendar.getTime();
    }

    /**
     * 获取下月第一天的0点
     * @param time
     * @return
     */
    public static Date nextMonthAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

        return calendar.getTime();
    }

    /**
     * 获取当前时间的0点
     * @param time
     * @return
     */
    public static Date dayAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取前一天的0点
     * @param time
     * @return
     */
    public static Date lastDayAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取下一天的0点
     * @param time
     * @return
     */
    public static Date nextDayAm0(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 计算2个时间相差的天数,这个方法算的是2个零点时间的绝对时间(天数)
     * @param startDate 起始时间
     * @param endDate 结束时间
     */
    public static int calc2DateTDOADays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        Date startDate0AM = dayAm0(startDate);
        Date endDate0AM = dayAm0(endDate);
        long v1 = startDate0AM.getTime() - endDate0AM.getTime();

        BigDecimal bd1 = new BigDecimal(Math.abs(v1));
        BigDecimal bd2 = new BigDecimal(ONE_DAY_MILLISECOND);

        int days = (int) bd1.divide(bd2, 0, BigDecimal.ROUND_UP).doubleValue();
        return days;
    }

    /**
     * 获得获得改变后的时间
     *
     * @param 	addDay			增加的天数(减少天数, 则传负数)
     * @param   to0AM			是否取0点时间
     * @return
     */
    public static Date add(int addDay, boolean to0AM) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, addDay);
        Date time = calendar.getTime();
        return to0AM ? dayAm0(time) : time;
    }

    /**
     * 修改日期
     * @param theDate 待修改的日期
     * @param addDays 加减的天数
     * @param hour 设置的小时
     * @param minute 设置的分
     * @param second 设置的秒
     * @return 修改后的日期
     */
    public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
        if (theDate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(theDate);

        cal.add(Calendar.DAY_OF_MONTH, addDays);
        cal.set(Calendar.MILLISECOND, 0);

        if (hour >= 0 && hour <= 24) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute >= 0 && minute <= 60) {
            cal.set(Calendar.MINUTE, minute);
        }
        if (second >= 0 && second <= 60) {
            cal.set(Calendar.SECOND, second);
        }

        return cal.getTime();
    }

    /**
     * 修改日期
     * @param theDate 待修改的日期
     * @param minute 设置的分
     * @param second 设置的秒
     * @return 修改后的日期
     */
    public static Date changeDateTime(Date theDate, int minute, int second) {
        if (theDate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(theDate);
        cal.set(Calendar.MILLISECOND, 0);

        if (minute >= 0 && minute <= 60) {
            cal.set(Calendar.MINUTE, minute);
        }
        if (second >= 0 && second <= 60) {
            cal.set(Calendar.SECOND, second);
        }

        return cal.getTime();
    }

}
