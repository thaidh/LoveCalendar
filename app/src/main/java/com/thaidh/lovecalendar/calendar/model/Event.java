package com.thaidh.lovecalendar.calendar.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.thaidh.lovecalendar.R;

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

    public static int getImageResource(int eventType) {
        int id;
        switch (eventType) {
            default:
            case Event.TYPE_BLEEDING:
                id = R.drawable.icon_bleeding;
                break;
            case Event.TYPE_SPOTTING:
                id = R.drawable.icon_spotting;
                break;
            case Event.TYPE_DRY_INFERTILE:
                id = R.drawable.icon_dry_infertile;
                break;
            case Event.TYPE_INFERTILE:
                id = R.drawable.icon_infertile;
                break;
            case Event.TYPE_POSSIBLY_FERTILE:
                id = R.drawable.icon_possibly_fertile;
                break;
            case Event.TYPE_PEAK:
                id = R.drawable.icon_peak;
                break;
            case Event.TYPE_PEAK_AFTER_1:
                id = R.drawable.icon_peak_after_1;
                break;
            case Event.TYPE_PEAK_AFTER_2:
                id = R.drawable.icon_peak_after_2;
                break;
            case Event.TYPE_PEAK_AFTER_3:
                id = R.drawable.icon_peak_after_3;
                break;
            case Event.TYPE_INFERCOURSE:
                id = R.drawable.icon_infercourse;
                break;
        }
        return id;
    }
}
