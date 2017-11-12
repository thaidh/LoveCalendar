package com.thaidh.lovecalendar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.adapter.MyMonthPagerAdapter;
import com.thaidh.lovecalendar.calendar.helper.Formatter;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager mainViewPager;
    MyMonthPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPager = findViewById(R.id.main_view_pager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventComposeActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        openMonthlyToday();
    }

    private void openMonthlyToday() {
        DateTime targetDay = new DateTime();
        fillMonthlyViewPager(targetDay.toString(Formatter.DAYCODE_PATTERN));
    }

    private void fillMonthlyViewPager(String targetDay) {
        ArrayList<String> codes = getMonths(targetDay);
        pagerAdapter = new MyMonthPagerAdapter(getSupportFragmentManager(), codes);
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.setOffscreenPageLimit(1);
        int mDefaultMonthlyPage = codes.size() / 2;
        mainViewPager.setCurrentItem(mDefaultMonthlyPage);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
//                    if (config.storedView == YEARLY_VIEW) {
//                        val dateTime = Formatter.getDateTimeFromCode(codes[position])
//                        title = "${getString(R.string.app_launcher_name)} - ${Formatter.getYear(dateTime)}"
//                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        calendar_event_list_holder.beGone()
    }

    public static int PREFILLED_MONTHS = 97;
    private ArrayList<String> getMonths(String codes) {
        ArrayList<String> months = new ArrayList<String>(PREFILLED_MONTHS);
        DateTime today = Formatter.getDateTimeFromCode(codes);
        for (int i = -PREFILLED_MONTHS / 2; i <= PREFILLED_MONTHS / 2; i++) {
            months.add(Formatter.getDayCodeFromDateTime(today.plusMonths(i)));

        }
        return months;
    }

}
