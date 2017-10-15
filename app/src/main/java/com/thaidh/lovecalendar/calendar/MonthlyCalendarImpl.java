package com.thaidh.lovecalendar.calendar;

import android.content.Context;

import com.thaidh.lovecalendar.calendar.helper.ContextKt;
import com.thaidh.lovecalendar.calendar.helper.DateTimeKt;
import com.thaidh.lovecalendar.calendar.helper.Formatter;
import com.thaidh.lovecalendar.calendar.model.DayMonthly;

import org.joda.time.DateTime;

import java.util.ArrayList;
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

    
    public final DateTime getMTargetDate() {
        DateTime var10000 = this.mTargetDate;

        return var10000;
    }

    public final void setMTargetDate( DateTime var1) {
        this.mTargetDate = var1;
    }

    public void updateMonthlyCalendar( DateTime targetDate, boolean filterEventTypes) {
        this.mFilterEventTypes = filterEventTypes;
        this.mTargetDate = targetDate;
        DateTime var10000 = this.mTargetDate;

        int startTS = DateTimeKt.seconds(var10000.minusMonths(1));
        var10000 = this.mTargetDate;

        int endTS = DateTimeKt.seconds(var10000.plusMonths(1));
//        DBHelper.getEvents$default(ContextKt.getDbHelper(this.mContext), startTS, endTS, 0, (Function1)(new Function1() {
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

    // $FF: synthetic method
    // $FF: bridge method
    public void updateMonthlyCalendar$default(DateTime var1, boolean var2, int var3, Object var4) {
        if((var3 & 2) != 0) {
            var2 = true;
        }

        updateMonthlyCalendar(var1, var2);
    }

    public void getMonth( DateTime targetDate) {
        updateMonthlyCalendar$default(targetDate, false, 2, (Object)null);
    }

    public final void getDays() {
        if(this.days.isEmpty()) {
            DateTime var10000 = this.mTargetDate;

            int currMonthDays = var10000.dayOfMonth().getMaximumValue();
            var10000 = this.mTargetDate;

            int firstDayIndex = var10000.withDayOfMonth(1).getDayOfWeek();
            if(!ContextKt.isSundayFirst) {
                --firstDayIndex;
            }

            var10000 = this.mTargetDate;

            int prevMonthDays = var10000.minusMonths(1).dayOfMonth().getMaximumValue();
            boolean isThisMonth = false;
            int value = prevMonthDays - firstDayIndex + 1;
            var10000 = this.mTargetDate;

            DateTime curDay = var10000;
            int i = 0;

            for(int var9 = this.DAYS_CNT; i < var9; ++i) {
                if(i < firstDayIndex) {
                    isThisMonth = false;
                    var10000 = this.mTargetDate;

                    var10000 = var10000.withDayOfMonth(1).minusMonths(1);
                    curDay = var10000;
                } else if(i == firstDayIndex) {
                    value = 1;
                    isThisMonth = true;
                    var10000 = this.mTargetDate;

                    curDay = var10000;
                } else if(value == currMonthDays + 1) {
                    value = 1;
                    isThisMonth = false;
                    var10000 = this.mTargetDate;

                    var10000 = var10000.withDayOfMonth(1).plusMonths(1);
                    curDay = var10000;
                }

                boolean var13;
                label69: {
                    if(isThisMonth) {
                        DateTime var10001 = this.mTargetDate;

                        if(this.isToday(var10001, value)) {
                            var13 = true;
                            break label69;
                        }
                    }

                    var13 = false;
                }

                boolean isToday = var13;
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
//        ContextKt.getDbHelper(this.mContext).getEventTypes((Function1)(new Function1() {
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
//            List var10001 = ContextKt.getFilteredEvents(this.mContext, (List)events);
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
