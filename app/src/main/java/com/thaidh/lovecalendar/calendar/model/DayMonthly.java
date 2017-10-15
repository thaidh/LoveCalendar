package com.thaidh.lovecalendar.calendar.model;

import java.util.ArrayList;

/**
 * Created by thaidh on 10/15/17.
 */

public class DayMonthly {
    public int value;
    public boolean isThisMonth;
    public boolean isToday;
    public String code;
    public int weekOfYear;
    ArrayList<Event> eventList;

    public DayMonthly(int value, boolean isThisMonth, boolean isToday, String code, int weekOfYear, ArrayList<Event> eventList) {
        this.value = value;
        this.isThisMonth = isThisMonth;
        this.isToday = isToday;
        this.code = code;
        this.weekOfYear = weekOfYear;
        this.eventList = eventList;
    }

    public boolean hasEvent() {
        //todo implment event
        return false;
    }
}

