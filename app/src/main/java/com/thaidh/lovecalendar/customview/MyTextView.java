package com.thaidh.lovecalendar.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by thaidh on 10/15/17.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setColors(int textColor, int accentColor) {
        setTextColor(textColor);
        setLinkTextColor(accentColor);
    }
}
