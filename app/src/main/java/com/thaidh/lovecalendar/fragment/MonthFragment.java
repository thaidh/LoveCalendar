package com.thaidh.lovecalendar.fragment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.calendar.MonthlyCalendar;
import com.thaidh.lovecalendar.calendar.MonthlyCalendarImpl;
import com.thaidh.lovecalendar.calendar.helper.ContextKt;
import com.thaidh.lovecalendar.calendar.helper.Formatter;
import com.thaidh.lovecalendar.calendar.model.DayMonthly;
import com.thaidh.lovecalendar.customview.MyTextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthFragment extends Fragment implements MonthlyCalendar {
    int[] letterIDs = {R.string.sunday_letter, R.string.monday_letter, R.string.tuesday_letter, R.string.wednesday_letter,
            R.string.thursday_letter, R.string.friday_letter, R.string.saturday_letter};

    private static final String DAY_CODE = "day_code";
    private static final String ARG_PARAM2 = "param2";

    private String mParam2;

    RelativeLayout mHolder;
    TextView week_num;
    TextView top_value;
    View top_left_arrow;
    View top_right_arrow;



    MonthlyCalendarImpl mCalendar;

    int mTextColor;
    private int mWeakTextColor;
    private String mDayCode;
    private boolean mSundayFirst = false;
    private String mPackageName;
    private int dayLabelHeight = 0;


    public MonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dayCode Parameter 1.
     * @param param2  Parameter 2.
     * @return A new instance of fragment MonthFragment.
     */
    public static MonthFragment newInstance(String dayCode, String param2) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putString(DAY_CODE, dayCode);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDayCode = getArguments().getString(DAY_CODE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPackageName = getActivity().getPackageName();
        mHolder = (RelativeLayout) view;
        week_num = mHolder.findViewById(R.id.week_num);
        top_value = mHolder.findViewById(R.id.top_value);
        top_left_arrow = mHolder.findViewById(R.id.top_left_arrow);
        top_right_arrow = mHolder.findViewById(R.id.top_right_arrow);

        mSundayFirst = ContextKt.isSundayFirst;
        mTextColor = ContextCompat.getColor(getContext(), R.color.default_text_color);
        mWeakTextColor = ColorUtils.setAlphaComponent(mTextColor, 100);

        setupButtons();

        setupLabels();
        mCalendar = new MonthlyCalendarImpl(this, getContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextKt.isSundayFirst != mSundayFirst) {
            mSundayFirst = ContextKt.isSundayFirst;
            setupLabels();
        }

        mCalendar.mTargetDate = Formatter.getDateTimeFromCode(mDayCode);
        mCalendar.getDays();    // prefill the screen asap, even if without events
        updateCalendar();
    }

    private void updateCalendar() {
        mCalendar.updateMonthlyCalendar(Formatter.getDateTimeFromCode(mDayCode), true);
    }

    private void setupLabels() {
        for (int i = 0; i <= 6; i++) {
            int index = i;
            if (!mSundayFirst) {
                index = (i + 1) % letterIDs.length;
            }
            TextView tv = mHolder.findViewById(getResources().getIdentifier(String.format("label_%d", index), "id", mPackageName));
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.default_text_color));
            tv.setText(getString(letterIDs[index]));
        }

    }

    private void setupButtons() {
        //todo
        top_left_arrow.getBackground().mutate().setColorFilter(mTextColor, PorterDuff.Mode.SRC_ATOP);
        top_right_arrow.getBackground().mutate().setColorFilter(mTextColor, PorterDuff.Mode.SRC_ATOP);
//        top_left_arrow.setBackground(null);
//        top_right_arrow.setBackground(null);
    }

    @Override
    public void updateMonthlyCalendar(final String month, final List<DayMonthly> days) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                top_value.setText(month);
                top_value.setTextColor(mTextColor);
                updateDays(days);
            }
        });
    }

    private void updateDays(List<DayMonthly> days) {
        boolean displayWeekNumbers = ContextKt.displayWeekNumbers;
        int len = days.size();

        if (week_num == null)
            return;

        week_num.setTextColor(mTextColor);
        week_num.setVisibility(displayWeekNumbers ? View.VISIBLE : View.GONE);
        for (int i = 0; i <= 5; i++) {
            TextView tv = mHolder.findViewById(getResources().getIdentifier(String.format("week_num_%d", i), "id", mPackageName));
            int weekOfYear = days.get(i * 7 + 3).weekOfYear;
            tv.setText(String.valueOf(weekOfYear));
            tv.setTextColor(mTextColor);
            tv.setVisibility(displayWeekNumbers ? View.VISIBLE : View.GONE);
        }
        for (int i = 0; i <= len; i++) {

            LinearLayout ll = mHolder.findViewById(getResources().getIdentifier(String.format("day_%d", i), "id", mPackageName));
            if (ll != null) {

                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //todo open day
//                    openDay(day.code);
                    }
                });
                DayMonthly day = days.get(i);

                ll.removeAllViews();
                addDayNumber(day, ll);
//                addDayEvents(day, this)
            }
        }
    }

    private void addDayNumber(DayMonthly day, LinearLayout linearLayout) {
        final MyTextView tv = (MyTextView) View.inflate(getContext(), R.layout.day_monthly_item_view, null);
        tv.setTextColor(day.isThisMonth ? mTextColor : mWeakTextColor);
        tv.setText(String.valueOf(day.value));
        tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(tv, layoutParams);

        if (day.isToday) {
            tv.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            if (dayLabelHeight == 0) {
                tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (tv.getHeight() > 0) {
                            dayLabelHeight = tv.getHeight();
                            updateDayLabelHeight(tv);
                        }

                    }
                });
            } else {
                updateDayLabelHeight(tv);
            }
        }
    }

    private void updateDayLabelHeight(TextView tv) {
        final int primaryColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        BitmapDrawable baseDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.monthly_today_circle);
        Bitmap bitmap = baseDrawable.getBitmap();
        BitmapDrawable scaledDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, dayLabelHeight, dayLabelHeight, true));
        scaledDrawable.mutate().setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
        tv.setBackground(scaledDrawable);
    }

}
