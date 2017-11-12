package com.thaidh.lovecalendar.calendar;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thaidh.lovecalendar.calendar.helper.DateTimeKt;
import com.thaidh.lovecalendar.calendar.helper.Formatter;
import com.thaidh.lovecalendar.calendar.helper.GlobalData;
import com.thaidh.lovecalendar.calendar.model.DayMonthly;
import com.thaidh.lovecalendar.calendar.model.Event;
import com.thaidh.lovecalendar.database.EventRepository;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thaidh on 10/15/17.
 */

public class MonthlyCalendarImpl {
    private final int DAYS_CNT;
    private final String YEAR_PATTERN;
    private final String mToday;
    private static HashMap<String, ArrayList<Event>> dayEventsMap = new HashMap();
    private ArrayList<DayMonthly> days;
    private boolean mFilterEventTypes;
    
    public DateTime mTargetDate;
    
    private final MonthlyCalendar mCallback;
    
    private final Context mContext;

    public MonthlyCalendarImpl( MonthlyCalendar mCallback,  Context mContext) {
        this.mCallback = mCallback;
        this.mContext = mContext;
        this.DAYS_CNT = 42;
        this.YEAR_PATTERN = "YYYY";
        String var10001 = new DateTime().toString(Formatter.DAYCODE_PATTERN);
        this.mToday = var10001;
        this.days = new ArrayList(this.DAYS_CNT);
        this.mFilterEventTypes = true;
    }

    public final boolean getMFilterEventTypes() {
        return this.mFilterEventTypes;
    }

    public final void setMFilterEventTypes(boolean var1) {
        this.mFilterEventTypes = var1;
    }

    public void loadEvent(DateTime targetDate, boolean filterEventTypes) {
        this.mFilterEventTypes = filterEventTypes;
        this.mTargetDate = targetDate;
        DateTime dateTime = this.mTargetDate;
        int startTS = DateTimeKt.seconds(dateTime.minusMonths(1));
        int endTS = DateTimeKt.seconds(dateTime.plusMonths(1));

        EventRepository.mEventQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                handleAddEventToMap(event);
                Log.i("AAAAAA", "onChildAdded: " + event.getType() + " " + event.getStartTime());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void handleAddEventToMap(Event event) {
        DateTime eventStart = Formatter.getDateTimeFromTS(event.getStartTime());
        DateTime eventEnd = Formatter.getDateTimeFromTS(event.getEndTime());
        String endCode = Formatter.getDayCodeFromDateTime(eventEnd);

        DateTime currDay = eventStart;
        String dayCode = Formatter.getDayCodeFromDateTime(eventStart);

        ArrayList<Event> currDayEvents = dayEventsMap.get(dayCode);
        if (currDayEvents == null) {
            currDayEvents = new ArrayList();
            dayEventsMap.put(dayCode, currDayEvents);
        } else {
            for (Event curEvent : currDayEvents) {
                if (curEvent.getType() == event.getType() && curEvent.getStartTime() == event.getStartTime() && curEvent.getEndTime() == event.getEndTime()) { // need ignore
                    return;
                }
            }
        }
        currDayEvents.add(event);

        while (!Formatter.getDayCodeFromDateTime(currDay).equals(endCode)) {
            currDay = currDay.plusDays(1);
            dayCode = Formatter.getDayCodeFromDateTime(currDay);
            currDayEvents = dayEventsMap.get(dayCode);
            if (currDayEvents == null) {
                currDayEvents = new ArrayList();
                dayEventsMap.put(dayCode, currDayEvents);
            }
            currDayEvents.add(event);

        }

        markDaysWithEvents();
    }

    public final void getDays() {
        if(this.days.isEmpty()) {
            DateTime dateTime = this.mTargetDate;

            int currMonthDays = dateTime.dayOfMonth().getMaximumValue();
            dateTime = this.mTargetDate;

            int firstDayIndex = dateTime.withDayOfMonth(1).getDayOfWeek();
            if(!GlobalData.isSundayFirst) {
                --firstDayIndex;
            }

            dateTime = this.mTargetDate;

            int prevMonthDays = dateTime.minusMonths(1).dayOfMonth().getMaximumValue();
            boolean isThisMonth = false;
            int value = prevMonthDays - firstDayIndex + 1;
            dateTime = this.mTargetDate;

            DateTime curDay = dateTime;
            for(int i = 0; i < DAYS_CNT; ++i) {
                if(i < firstDayIndex) {
                    isThisMonth = false;
                    dateTime = this.mTargetDate;

                    dateTime = dateTime.withDayOfMonth(1).minusMonths(1);
                    curDay = dateTime;
                } else if(i == firstDayIndex) {
                    value = 1;
                    isThisMonth = true;
                    dateTime = this.mTargetDate;

                    curDay = dateTime;
                } else if(value == currMonthDays + 1) {
                    value = 1;
                    isThisMonth = false;
                    dateTime = this.mTargetDate;

                    dateTime = dateTime.withDayOfMonth(1).plusMonths(1);
                    curDay = dateTime;
                }

                boolean isToday = false;
                if (isThisMonth) {
                    DateTime curDate = this.mTargetDate;

                    if (this.isToday(curDate, value)) {
                        isToday = true;
                    }
                }

                DateTime newDay = curDay.withDayOfMonth(value);
                String dayCode = Formatter.getDayCodeFromDateTime(newDay);
                DayMonthly day = new DayMonthly(value, isThisMonth, isToday, dayCode, newDay.getWeekOfWeekyear(), new ArrayList());
                this.days.add(day);
                ++value;
            }
        }

        if(dayEventsMap.isEmpty()) {
            this.mCallback.updateMonthlyCalendar(this.getMonthName(), this.days);
        } else {
            this.markDaysWithEvents();
        }

    }

    private void markDaysWithEvents() {
        Log.e("AAAAAAAA", "markDaysWithEvents: ");
        for (DayMonthly dayMonthly: days) {
            if (dayEventsMap.containsKey(dayMonthly.code)) {
                dayMonthly.setEvents(dayEventsMap.get(dayMonthly.code));
            }
        }

        MonthlyCalendarImpl.this.getMCallback().updateMonthlyCalendar(MonthlyCalendarImpl.this.getMonthName(), days);
    }

    private boolean isToday(DateTime targetDate, int curDayInMonth) {
        return curDayInMonth > targetDate.dayOfMonth().getMaximumValue() ? false :
                targetDate.withDayOfMonth(curDayInMonth).toString(Formatter.DAYCODE_PATTERN).equals(this.mToday);
    }

    private String getMonthName() {
        Context var10001 = this.mContext;
        DateTime var10002 = this.mTargetDate;

        String month = Formatter.getMonthName(var10001, var10002.getMonthOfYear());
        DateTime var3 = this.mTargetDate;

        String targetYear = var3.toString(this.YEAR_PATTERN);
        if(targetYear.equals(new DateTime().toString(this.YEAR_PATTERN))) {
            month = month + ' ' + targetYear;
        }

        return month;
    }

    public final MonthlyCalendar getMCallback() {
        return this.mCallback;
    }
}
