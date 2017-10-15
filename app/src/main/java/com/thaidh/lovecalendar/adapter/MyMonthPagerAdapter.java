package com.thaidh.lovecalendar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thaidh.lovecalendar.fragment.MonthFragment;

import java.util.ArrayList;

/**
 * Created by thaidh on 10/15/17.
 */

public class MyMonthPagerAdapter extends FragmentPagerAdapter {
    int size = 3;

    ArrayList<String> mCodes;

    public MyMonthPagerAdapter(FragmentManager fm, ArrayList<String> codes) {
        super(fm);
        this.mCodes = codes;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = MonthFragment.newInstance(mCodes.get(position), "param" + position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mCodes.size();
    }
}
