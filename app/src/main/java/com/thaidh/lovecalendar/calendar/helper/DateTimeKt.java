package com.thaidh.lovecalendar.calendar.helper;

import org.joda.time.DateTime;

/**
 * Created by thaidh on 10/15/17.
 */

public class DateTimeKt {
    public static int seconds(DateTime dt) {
        return (int)(dt.getMillis() / 1000);
    }
}
