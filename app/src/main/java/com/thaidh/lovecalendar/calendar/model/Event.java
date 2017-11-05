package com.thaidh.lovecalendar.calendar.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by thaidh on 10/15/17.
 */

@IgnoreExtraProperties
public class Event {
    public static final int TYPE_BLEEDING = 1;
    public static final int TYPE_SPOTTING = 2;
    public static final int TYPE_DRY_INFERTILE = 3;
    public static final int TYPE_INFERTILE = 4;
    public static final int TYPE_POSSIBLY_FERTILE = 5;
    public static final int TYPE_PEAK = 6;
    public static final int TYPE_PEAK_AFTER_1 = 7;
    public static final int TYPE_PEAK_AFTER_2 = 8;
    public static final int TYPE_PEAK_AFTER_3 = 9;
    public static final int TYPE_INFERCOURSE = 10;

    int type;
    long startTime;
    long endTime;

    public Event() {
    }

    public Event(int type, long start, long end) {
        this.type = type;
        this.startTime = start;
        this.endTime = end;
    }

    public int getType() {
        return type;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
