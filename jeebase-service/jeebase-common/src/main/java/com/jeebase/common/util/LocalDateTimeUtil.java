package com.jeebase.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * java8日期工具.
 *
 * @author 
 * @since 2018-08-07 02:02
 */
public final class LocalDateTimeUtil {
    
    // 获取当前时间的LocalDateTime对象
    // LocalDateTime.now()

    // 根据年月日构建
    // LocalDateTime.of()

    // 比较日期先后
    // LocalDateTime.now().isBefore()
    // LocalDateTime.now().isAfter()
    
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    
    /**
     * 英文简写（默认）如：2010/12/01
     */
    public static String FORMAT_SLASH_SHORT = "yyyy/MM/dd";
    
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 英文全称无分隔符 如：20101201231506
     */
    public static String FORMAT_LONG_NO = "yyyyMMddHHmmss";
    
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    
    /**
     * 毫秒转换为LocalDateTime.
     *
     * @param milliTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime convertMilliSecondToLDT(Long milliTime) {
        Instant instant = Instant.ofEpochMilli(milliTime);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime datetime = LocalDateTime.ofInstant(instant, zone);
        return datetime;
    }
    
    /**
     * 秒转换为LocalDateTime.
     *
     * @param secondTime
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime convertSecondToLDT(Long secondTime) {
        Instant instant = Instant.ofEpochSecond(secondTime);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime datetime = LocalDateTime.ofInstant(instant, zone);
        return datetime;
    }

    /**
     * Date转换为LocalDateTime.
     *
     * @param date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date.
     *
     * @param time
     * @return java.util.Date
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取指定日期的毫秒.
     *
     * @param time
     * @return java.lang.Long
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒.
     *
     * @param time
     * @return java.lang.Long
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式.
     *
     * @param time
     * @param pattern
     * @return java.lang.String
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式.
     *
     * @param pattern
     * @return java.lang.String
     */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*.
     *
     * @param time
     * @param number
     * @param field
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*.
     *
     * @param time
     * @param number
     * @param field
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*.
     *
     * @param startTime
     * @param endTime
     * @param field
     * @return long
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间，2017,7,22 00:00.
     *
     * @param time
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间，2017,7,22 23:59:59.999999999.
     *
     * @param time
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    /**
     * 获取某月第一天的00:00:00
     * @return
     */
    public static LocalDateTime getFirstDayOfMonth(LocalDateTime localDateTime){
        LocalDateTime firstOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        return firstOfDay;
    }
    /**
     * 获取某月最后一天的23:59:59
     * @return
     */
    public static LocalDateTime getLastDayOfMonth(LocalDateTime localDateTime){
        LocalDateTime lastOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        return lastOfDay;
    }
    /**
     * @Title: getFirstDayOFYear
     * @Description:  获取年的第一天
     * @param localDateTime
     * @return LicalDateTime    返回类型
     */
    public static LocalDateTime getFirstDayOfYear(LocalDateTime localDateTime) {
    	 LocalDateTime firstOfYear = localDateTime.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
         return firstOfYear;
    }

    /**
     * @Title: getLocalDateTimeByLocalDate
     * @Description: 把localDate转化为localDateTime,时分秒都设置成00
     * @param localDate
     * @return LocalDateTime    返回类型
     */
    public static LocalDateTime getLocalDateTimeByLocalDate(LocalDate localDate) {
    	if (localDate == null) {
    		return LocalDateTime.now();
    	}
    	LocalTime zeroTime = LocalTime.parse("00:00:00");
    	LocalDateTime localDateTime = LocalDateTime.of(localDate,zeroTime);
    	return localDateTime;
    }
}
