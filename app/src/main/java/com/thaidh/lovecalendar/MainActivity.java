package com.thaidh.lovecalendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.thaidh.lovecalendar.adapter.MyMonthPagerAdapter;
import com.thaidh.lovecalendar.calendar.helper.Formatter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager mainViewPager;
    TextView mTextMessage;
    MyMonthPagerAdapter pagerAdapter;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPager = findViewById(R.id.main_view_pager);
//        pagerAdapter = new MyMonthPagerAdapter(getSupportFragmentManager());


//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
//        main_weekly_scrollview.beGone()
//        calendar_fab.beVisible()
        ArrayList<String> codes = getMonths(targetDay);
        pagerAdapter = new MyMonthPagerAdapter(getSupportFragmentManager(), codes);
        mainViewPager.setAdapter(pagerAdapter);
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
