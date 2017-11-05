package com.thaidh.lovecalendar.calendar;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thaidh.lovecalendar.calendar.helper.GlobalData;
import com.thaidh.lovecalendar.calendar.helper.DateTimeKt;
import com.thaidh.lovecalendar.calendar.helper.Formatter;
import com.thaidh.lovecalendar.calendar.model.DayMonthly;
import com.thaidh.lovecalendar.calendar.model.Event;
import com.thaidh.lovecalendar.database.EventRepository;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thaidh on 10/15/17.
 */

public class MonthlyCalendarImpl {
    private final int DAYS_CNT;
    private final String YEAR_PATTERN;
    private final String mToday;
    private ArrayList mEvents;
    private ArrayList days;
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
        this.mEvents = new ArrayList();
        this.days = new ArrayList(this.DAYS_CNT);
        this.mFilterEventTypes = true;
    }

    public final boolean getMFilterEventTypes() {
        return this.mFilterEventTypes;
    }

    public final void setMFilterEventTypes(boolean var1) {
        this.mFilterEventTypes = var1;
    }

    public void updateMonthlyCalendar( DateTime targetDate, boolean filterEventTypes) {
        this.mFilterEventTypes = filterEventTypes;
        this.mTargetDate = targetDate;
        DateTime var10000 = this.mTargetDate;

        int startTS = DateTimeKt.seconds(var10000.minusMonths(1));
        var10000 = this.mTargetDate;

        int endTS = DateTimeKt.seconds(var10000.plusMonths(1));


//        EventRepository.mEventQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
//                    Event event = eventSnapshot.getValue(Event.class);
//                    Log.i("AAAAAA", "onDataChange: " + event.getType() + " " + event.getStartTime());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        EventRepository.mEventQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    Log.i("AAAAAA", "onChildAdded: " + event.getType() + " " + event.getStartTime());
//                }
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
//        DBHelper.getEvents$default(GlobalData.getDbHelper(this.mContext), startTS, endTS, 0, (Function1)(new Function1() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public Object invoke(Object var1) {
//                this.invoke((List)var1);
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke( List it) {
//                MonthlyCalendarImpl.this.gotEvents((ArrayList)it);
//            }
//        }), 4, (Object)null);
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

        if(this.mEvents.isEmpty()) {
            this.mCallback.updateMonthlyCalendar(this.getMonthName(), (List)this.days);
        } else {
            this.markDaysWithEvents(this.days);
        }

    }

    private final void markDaysWithEvents(final ArrayList days) {
        //todo handle mark event
//        GlobalData.getDbHelper(this.mContext).getEventTypes((Function1)(new Function1() {
//            // $FF: synthetic method
//            // $FF: bridge method
//            public Object invoke(Object var1) {
//                this.invoke((ArrayList)var1);
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke( ArrayList itx) {
//                Intrinsics.checkParameterIsNotNull(itx, "it");
//                HashMap dayEvents = new HashMap();
//                Iterable $receiver$iv = (Iterable)MonthlyCalendarImpl.this.mEvents;
//                Iterator var4 = $receiver$iv.iterator();
//
//                Object element$iv;
//                while(var4.hasNext()) {
//                    element$iv = var4.next();
//                    Event itxx = (Event)element$iv;
//                    DateTime startDateTime = Formatter.INSTANCE.getDateTimeFromTS(itxx.getStartTS());
//                    DateTime endDateTime = Formatter.INSTANCE.getDateTimeFromTS(itxx.getEndTS());
//                    String endCode = Formatter.INSTANCE.getDayCodeFromDateTime(endDateTime);
//                    DateTime currDay = startDateTime;
//                    String dayCode = Formatter.INSTANCE.getDayCodeFromDateTime(startDateTime);
//                    ArrayList var10000 = (ArrayList)dayEvents.get(dayCode);
//                    if(var10000 == null) {
//                        var10000 = new ArrayList();
//                    }
//
//                    ArrayList var12 = var10000;
//                    var12.add(itxx);
//                    dayEvents.put(dayCode, var12);
//
//                    while(Intrinsics.areEqual(Formatter.INSTANCE.getDayCodeFromDateTime(currDay), endCode) ^ true) {
//                        DateTime var23 = currDay.plusDays(1);
//                        Intrinsics.checkExpressionValueIsNotNull(var23, "currDay.plusDays(1)");
//                        currDay = var23;
//                        dayCode = Formatter.INSTANCE.getDayCodeFromDateTime(currDay);
//                        var10000 = (ArrayList)dayEvents.get(dayCode);
//                        if(var10000 == null) {
//                            var10000 = new ArrayList();
//                        }
//
//                        var12 = var10000;
//                        var12.add(itxx);
//                        dayEvents.put(dayCode, var12);
//                    }
//                }
//
//                $receiver$iv = (Iterable)days;
//                Collection destination$iv$iv = (Collection)(new ArrayList());
//                Iterator var19 = $receiver$iv.iterator();
//
//                while(var19.hasNext()) {
//                    Object element$iv$iv = var19.next();
//                    DayMonthly it = (DayMonthly)element$iv$iv;
//                    if(dayEvents.keySet().contains(it.getCode())) {
//                        destination$iv$iv.add(element$iv$iv);
//                    }
//                }
//
//                $receiver$iv = (Iterable)((List)destination$iv$iv);
//
//                Object var10001;
//                DayMonthly itxxx;
//                for(var4 = $receiver$iv.iterator(); var4.hasNext(); itxxx.setDayEvents((ArrayList)var10001)) {
//                    element$iv = var4.next();
//                    itxxx = (DayMonthly)element$iv;
//                    var10001 = dayEvents.get(itxxx.getCode());
//                    if(var10001 == null) {
//                        Intrinsics.throwNpe();
//                    }
//                }
//
//                MonthlyCalendarImpl.this.getMCallback().updateMonthlyCalendar(MonthlyCalendarImpl.this.getMonthName(), (List)days);
//            }
//        }));
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

    private void gotEvents(ArrayList events) {
        ArrayList var2;
        if(this.mFilterEventTypes) {
            //todo filter event
//            List var10001 = GlobalData.getFilteredEvents(this.mContext, (List)events);
//
//            var2 = (ArrayList)var10001;
        } else {
            var2 = events;
        }

//        this.mEvents = var2;
        this.getDays();
    }

    
    public final MonthlyCalendar getMCallback() {
        return this.mCallback;
    }

    
    public final Context getMContext() {
        return this.mContext;
    }



    // $FF: synthetic method
    public static final void access$setMEvents$p(MonthlyCalendarImpl $this,  ArrayList var1) {
        $this.mEvents = var1;
    }
}
