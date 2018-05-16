package com.sh3h.indemnity.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dengzhimin on 2017/3/15.
 */

public class DateUtils {

    public static final String EMPTY = "";
    public static final String FORMAT_FULL_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_NO_YEAR = "MM-dd HH:mm";
    public static final String FORMAT_SHORT_DATE = "MM-dd";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_ISDROP = "yyyy.MM.dd";
    public static final String FORMAT_DATE_YEAR = "yyyy";
    public static final String FORMAT_DATE_NO_YEAR_SLASH = "MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_SHORT_TIME = "HH:mm";
    public static final String FORMAT_DATE_MONTH = "MM";
    public static final String FORMAT_DATE_DAY = "dd";
    public static final String FORMAT_DATE_SECOND = "yyyyMMddHHmmss";

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format == null ? FORMAT_DATE_NO_SECOND : format, Locale.getDefault()).format(date);
    }

    public static String format(long millis, String format) {
        Date date = new Date(millis);
        return format(date, format);
    }

    public static long getTime(Date date) {
        return date.getTime();
    }

    public static Date getDate(long millis) {
        return new Date(millis);
    }

    public static Date parse(String date, String format) {
        if(date == null) {
            return null;
        }
        try {
            SimpleDateFormat f = new SimpleDateFormat(format == null ? FORMAT_DATE_NO_SECOND : format);
            return f.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Date parse(int year, int month, int day, String format){
        String date = new StringBuffer().append(year).append("-")
                .append(month >= 10 ? month : "0" + month).append("-")
                .append(day >= 10 ? day : "0" + day).toString();
        return parse(date, format);
    }

}
