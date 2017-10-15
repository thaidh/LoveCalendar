package com.thaidh.lovecalendar.calendar;

import com.thaidh.lovecalendar.calendar.model.DayMonthly;

import java.util.List;

/**
 * Created by thaidh on 10/15/17.
 */

public interface MonthlyCalendar {
    void updateMonthlyCalendar(String month, List<DayMonthly> days);
}
