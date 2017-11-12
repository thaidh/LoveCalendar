package com.thaidh.lovecalendar.calendar.helper;

import android.content.Context;

import com.thaidh.lovecalendar.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

public class Formatter {
    
    public final static String DAYCODE_PATTERN = "YYYYMMdd";
    
    public final static String YEAR_PATTERN = "YYYY";
    
    public final static String TIME_PATTERN = "HHmmss";
    public final static String DAY_PATTERN = "d";
    public final static String DAY_OF_WEEK_PATTERN = "EEE";
    public final static String PATTERN_TIME_12 = "hh:mm a";
    public final static String PATTERN_TIME_24 = "HH:mm";
    public final static String PATTERN_HOURS_12 = "h a";
    public final static String PATTERN_HOURS_24 = "HH";

    public static String getDateFromCode( Context context,  String dayCode, boolean shortMonth) {
        DateTime dateTime = getDateTimeFromCode(dayCode);
        String day = dateTime.toString(DAY_PATTERN);
        String year = dateTime.toString(YEAR_PATTERN);
        byte var9 = 4;
        byte var10 = 6;
        String var10000 = dayCode.substring(var9, var10);
        Integer var13 = Integer.valueOf(var10000);

        int monthIndex = var13.intValue();
        String month = getMonthName(context, monthIndex);
        if(shortMonth) {
            var10 = 0;
            int var11 = Math.min(month.length(), 3);

            var10000 = month.substring(var10, var11);
            month = var10000;
        }

        String date = "" + month + ' ' + day;
        if(year.equals(new DateTime().toString(YEAR_PATTERN))) {
            date = date + ' ' + year;
        }

        return date;
    }

    // $FF: synthetic method
    // $FF: bridge method
    
    public static String getDateFromCodeDefault(Context var1, String var2, boolean var3, int var4, Object var5) {
        if((var4 & 4) != 0) {
            var3 = false;
        }

        return getDateFromCode(var1, var2, var3);
    }

    
    public static String getDayTitle( Context context,  String dayCode, boolean addDayOfWeek) {
        String date = getDateFromCodeDefault(context, dayCode, false, 4, (Object)null);
        DateTime dateTime = getDateTimeFromCode(dayCode);
        String day = dateTime.toString(DAY_OF_WEEK_PATTERN);
        return addDayOfWeek?"" + date + " (" + day + ')':date;
    }

    // $FF: synthetic method
    // $FF: bridge method
    
    public static String getDayTitleDefault(Context var1, String var2, boolean var3, int var4, Object var5) {
        if((var4 & 4) != 0) {
            var3 = true;
        }

        return getDayTitle(var1, var2, var3);
    }

    
    public static String getDate( Context context,  DateTime dateTime, boolean addDayOfWeek) {
        String var10002 = getDayCodeFromDateTime(dateTime);
        return getDayTitle(context, var10002, addDayOfWeek);
    }

    // $FF: synthetic method
    // $FF: bridge method
    
    public static String getDateDefault(Formatter var0, Context var1, DateTime var2, boolean var3, int var4, Object var5) {
        if((var4 & 4) != 0) {
            var3 = true;
        }

        return var0.getDate(var1, var2, var3);
    }

    
    public static String getFullDate( Context context,  DateTime dateTime) {
        String day = dateTime.toString(DAY_PATTERN);
        String year = dateTime.toString(YEAR_PATTERN);
        int monthIndex = dateTime.getMonthOfYear();
        String month = getMonthName(context, monthIndex);
        return "" + month + ' ' + day + ' ' + year;
    }

    public static String getHours( Context context,  DateTime dateTime) {
        return dateTime.toString(getHourPattern(context));
    }

    public static String getTime( Context context,  DateTime dateTime) {
        return dateTime.toString(getTimePattern(context));
    }

    public static DateTime getDateTimeFromCode( String dayCode) {
        return DateTimeFormat.forPattern(DAYCODE_PATTERN).withZone(DateTimeZone.UTC).parseDateTime(dayCode);
    }

    public static DateTime getLocalDateTimeFromCode( String dayCode) {
        return DateTimeFormat.forPattern(DAYCODE_PATTERN).withZone(DateTimeZone.getDefault()).parseDateTime(dayCode);
    }

    public static String getTimeFromTS( Context context, int ts) {
        return getTime(context, getDateTimeFromTS(ts));
    }

    public static int getDayStartTS( String dayCode) {
        DateTime dateTime = getLocalDateTimeFromCode(dayCode);
        return DateTimeKt.seconds(dateTime);
    }

    public static int getDayEndTS( String dayCode) {
        DateTime dateTime = getLocalDateTimeFromCode(dayCode).plusDays(1).minusMinutes(1);
        return DateTimeKt.seconds(dateTime);
    }

    public static String getDayCodeFromDateTime( DateTime dateTime) {
        return dateTime.toString(DAYCODE_PATTERN);
    }

    
    public static DateTime getDateTimeFromTS(int ts) {
        return new DateTime((long)ts * 1000L, DateTimeZone.getDefault());
    }

    public static DateTime getDateTimeFromTS(long ts) {
        return new DateTime(ts, DateTimeZone.getDefault());
    }

    public static String getMonthName( Context context, int id) {
        return context.getResources().getStringArray(R.array.months)[id - 1];
    }

    public static String getYear( DateTime dateTime) {
        return dateTime.toString(YEAR_PATTERN);
    }

    
    public static String getHourPattern( Context context) {
        return true?PATTERN_HOURS_24:PATTERN_HOURS_12;
    }

    
    public static String getTimePattern( Context context) {
        return true ?PATTERN_TIME_24:PATTERN_TIME_12;
    }

    
    public static String getExportedTime(long ts) {
        DateTime dateTime = new DateTime(ts, DateTimeZone.UTC);
        return "" + dateTime.toString(DAYCODE_PATTERN) + 'T' + dateTime.toString(TIME_PATTERN) + 'Z';
    }

    
    public static String getDayCodeFromTS(int ts) {
        String daycode = getDateTimeFromTS(ts).toString(DAYCODE_PATTERN);
        CharSequence var3 = (CharSequence)daycode;
        String var10000;
        if(var3.length() > 0) {
            var10000 = daycode;
        } else {
            var10000 = "0";
        }

        return var10000;
    }

}
