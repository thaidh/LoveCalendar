package com.thaidh.lovecalendar.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.adapter.IconPickerAdapter;

/**
 * Created by thaidh on 10/22/17.
 */

public class IconPickerBottomSheet extends BottomSheetDialog {
    View sheetView;
    IconPickerListener iconPickerListener;
    RecyclerView iconList;
    IconPickerAdapter adapter;
    LinearLayoutManager mLayoutManager;

    public IconPickerBottomSheet(@NonNull Context context) {
        super(context);
        sheetView = getLayoutInflater().inflate(R.layout.dialog_icon_picker_layout, null);
        for (int i = 0; i < 10; i++) {
            final int index = i + 1;
            View icon = findViewById(context.getResources().getIdentifier(String.format("icon_%d", (i + 1)), "id", context.getPackageName()));
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iconPickerListener != null) {
                        iconPickerListener.onIconClick(index);
                    }
                }
            });
        }

        setContentView(sheetView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        iconList = sheetView.findViewById(R.id.icon_list);
//        adapter = new IconPickerAdapter(getContext());
//        mLayoutManager = new LinearLayoutManager(getContext());
//        iconList.setLayoutManager(mLayoutManager);
//        iconList.setItemAnimator(null);
//        iconList.setAdapter(adapter);
    }

    public void setIconPickerListener(IconPickerListener iconPickerListener) {
        this.iconPickerListener = iconPickerListener;
    }

    public interface IconPickerListener {
        void onIconClick(int index);
    }


}
